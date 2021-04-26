package ru.medeng.mobile.api;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.medeng.models.LevelHolder;
import ru.medeng.models.Product;
import ru.medeng.models.TokenHolder;
import ru.medeng.models.order.Item;
import ru.medeng.models.order.Operation;
import ru.medeng.models.order.Order;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Customer;

public class Api {
    private static Api instance;

    public static Api getInstance() {
        return instance;
    }

    public static Api newInstance() {
        return instance = new Api();
    }

    private static String TAG = Api.class.getSimpleName();

    private NetworkThread thread;

    private String token;
    private AccessLevel level;
    private Map<Product, Integer> currentOrderItems;

    private Retrofit retrofit;
    private CustomerService customers;
    private AuthService auth;
    private ProductService products;
    private OrderService orders;

    public Api() {
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").registerTypeAdapter(CharSequence.class,
                new TypeAdapter<CharSequence>() {

                    @Override
                    public void write(JsonWriter out, CharSequence value) throws IOException {
                        if (value == null) {
                            out.nullValue();
                        } else {
                            out.value(value.toString());
                        }
                    }

                    @Override
                    public CharSequence read(JsonReader in) throws IOException {
                        try {
                            return in.nextString();
                        } catch(Throwable t) {
                            in.nextNull();
                            return null;
                        }
                    }
            })
            .create();

        retrofit = new Retrofit.Builder()
            .baseUrl("https://medeng.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

        customers = retrofit.create(CustomerService.class);
        auth = retrofit.create(AuthService.class);
        products = retrofit.create(ProductService.class);
        orders = retrofit.create(OrderService.class);

        thread = new NetworkThread();
    }

    public boolean ping() {
        return thread.await(() -> {
            try {
                URLConnection conn = new URL("https://medeng.herokuapp.com/swagger-ui.html").openConnection();
                conn.connect();
                return conn.getContentLength() > 0;
            } catch(Exception e) {
                Log.d(TAG, "API is not available", e);
                return false;
            }
        });
    }

    public boolean login(CharSequence login, CharSequence password) {
        try {
            token = thread.await(() -> {
                Call<TokenHolder> call = auth.login(login, password);
                Response<TokenHolder> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body().getToken();
                }

                return null;
            });

            return token != null;
        } catch(Exception e) {
            Log.e(TAG, "Failed to login", e);
            return false;
        }
    }

    public void logout() {
        token = null;
        level = null;
        currentOrderItems = null;
    }

    public int signup(Customer customer) {
        return thread.await(() -> {
            try {
                Call<UUID> call = customers.signup(customer);
                Response<UUID> resp = call.execute();
                return resp.code() == 200 ? 200 : 400;
            } catch(Exception e) {
                Log.d(TAG, "Failed signup", e);
                return 500;
            }
        });
    }

    public Customer getCustomer() {
        return thread.await(() -> {
            try {
                Call<Customer> call = customers.get(token);
                Response<Customer> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body();
                }

                return null;
            } catch(Exception e) {
                Log.d(TAG, "Failed to get customer info", e);
                return null;
            }
        });
    }

    public int saveCustomer(Customer customer) {
        return thread.await(() -> {
            try {
                Call<Void> call = customers.save(token, customer);
                Response<Void> resp = call.execute();
                return resp.code();
            } catch (Exception e) {
                return 500;
            }
        });
    }

    public List<Product> listProducts() {
        return thread.await(() -> {
            try {
                Call<List<Product>> call = products.list();
                Response<List<Product>> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body();
                }

                Log.d(TAG, "Unexpected status: " + resp.code());
            } catch (Exception e) {
                Log.d(TAG, "listProducts", e);
            }

            return Collections.emptyList();
        });
    }

    public List<Order> listOrders() {
        return thread.await(() -> {
            try {
                Call<List<Order>> call = orders.list(token);
                Response<List<Order>> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body();
                }

                Log.d(TAG, "Unexpected status: " + resp.code());
            } catch (Exception e) {
                Log.d(TAG, "Failed to list orders", e);
            }

            return Collections.emptyList();
        });
    }

    public void read(SharedPreferences prefs) {
        token = prefs.getString("token", null);
    }

    public void save(SharedPreferences prefs) {
        prefs.edit().putString("token", token).apply();
    }

    public AccessLevel getAccessLevel() {
        AccessLevel level = thread.await(() -> {
            try {
                Call<LevelHolder> call = auth.getAccessLevel(token);
                Response<LevelHolder> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body().getLevel();
                }

                return AccessLevel.Guest;
            } catch(Exception e) {
                Log.d(TAG, "Failed to get access level", e);
                return AccessLevel.Guest;
            }
        });

        this.level = level;
        return level;
    }

    public AccessLevel getCachedAccessLevel() {
        return level;
    }

    public void setOrderMap(Map<Product, Integer> currentOrderItems) {
        this.currentOrderItems = currentOrderItems;
    }

    public Map<Product, Integer> getOrderMap() {
        return currentOrderItems;
    }

    public List<Item> getOrderItems() {
        List<Item> items = new ArrayList<>();

        for (Map.Entry<Product, Integer> e : currentOrderItems.entrySet()) {
            Item item = new Item();
            Operation o = new Operation();
            o.setProduct(e.getKey());
            o.setCount(e.getValue());
            item.setBooking(o);
            items.add(item);
        }

        return items;
    }

    public int createOrder() {
        return thread.await(() -> {
           try {
               Order order = new Order();

               List<Item> items = new ArrayList<>();
               for (Item oldItem : getOrderItems()) {
                   Operation oldBooking = oldItem.getBooking();
                   Product oldProduct = oldBooking.getProduct();

                   Item newItem = new Item();
                   Operation newBooking = new Operation();
                   Product newProduct = new Product();

                   newProduct.setId(oldProduct.getId());
                   newBooking.setProduct(newProduct);
                   newBooking.setCount(oldBooking.getCount());
                   newItem.setBooking(newBooking);
                   items.add(newItem);
               }
               order.setItems(items);

               Call<Void> call = orders.create(token, order);
               Response<Void> resp = call.execute();
               return resp.code();
           } catch(Exception e) {
               Log.d(TAG, "Failed to create order", e);
               return 500;
           }
        });
    }
}
