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
import ru.medeng.mobile.api.impl.AuthServiceImpl;
import ru.medeng.mobile.api.impl.CustomerServiceImpl;
import ru.medeng.mobile.api.impl.OrderServiceImpl;
import ru.medeng.mobile.api.impl.ProductServiceImpl;
import ru.medeng.mobile.api.impl.ShipmentServiceImpl;
import ru.medeng.mobile.api.rest.AuthService;
import ru.medeng.mobile.api.rest.CustomerService;
import ru.medeng.mobile.api.rest.OrderService;
import ru.medeng.mobile.api.rest.ProductService;
import ru.medeng.mobile.api.rest.ShipmentService;
import ru.medeng.models.LevelHolder;
import ru.medeng.models.Product;
import ru.medeng.models.Rest;
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

    private Retrofit retrofit;
    private AuthService auth;
    private AuthServiceImpl authImpl;

    private CustomerService customers;
    private CustomerServiceImpl customersImpl;

    private ProductService products;
    private ProductServiceImpl productsImpl;

    private OrderService orders;
    private OrderServiceImpl ordersImpl;

    private ShipmentService shipments;
    private ShipmentServiceImpl shipmentsImpl;

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

        auth = retrofit.create(AuthService.class);
        customers = retrofit.create(CustomerService.class);
        products = retrofit.create(ProductService.class);
        orders = retrofit.create(OrderService.class);
        shipments = retrofit.create(ShipmentService.class);

        thread = new NetworkThread();

        authImpl = new AuthServiceImpl(auth, thread);
        customersImpl = new CustomerServiceImpl(authImpl, customers, thread);
        ordersImpl = new OrderServiceImpl(authImpl, orders, thread);
        productsImpl = new ProductServiceImpl(authImpl, products, thread);
        shipmentsImpl = new ShipmentServiceImpl(authImpl, shipments, thread);
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

    public void logout() {
        authImpl.logout();
        ordersImpl.setOrderMap(null);
    }

    public void read(SharedPreferences prefs) {
        String token = prefs.getString("token", null);
        authImpl.setToken(token);
        authImpl.getAccessLevel();
    }

    public void save(SharedPreferences prefs) {
        prefs.edit().putString("token", authImpl.getToken()).apply();
    }

    public AuthServiceImpl getAuth() {
        return authImpl;
    }

    public CustomerServiceImpl getCustomers() {
        return customersImpl;
    }

    public OrderServiceImpl getOrders() {
        return ordersImpl;
    }

    public ProductServiceImpl getProducts() {
        return productsImpl;
    }

    public ShipmentServiceImpl getShipments() {
        return shipmentsImpl;
    }
}
