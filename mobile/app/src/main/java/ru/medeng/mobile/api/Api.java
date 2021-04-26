package ru.medeng.mobile.api;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.medeng.models.LevelHolder;
import ru.medeng.models.Product;
import ru.medeng.models.TokenHolder;
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

    private Retrofit retrofit;
    private CustomerService customers;
    private AuthService auth;
    private ProductService products;

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

    public CustomerService getCustomers() {
        return customers;
    }

    public ProductService getProducts() {
        return products;
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
}
