package ru.medeng.mobile.api.impl;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.medeng.tools.NetworkThread;
import ru.medeng.mobile.api.rest.ProductService;
import ru.medeng.models.Product;

public class ProductServiceImpl {
    private static final String TAG = ProductService.class.getSimpleName();
    private AuthServiceImpl auth;
    private ProductService products;
    private NetworkThread thread;

    public ProductServiceImpl(AuthServiceImpl auth, ProductService products, NetworkThread thread) {
        this.auth = auth;
        this.products = products;
        this.thread = thread;
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

    public boolean add(Product p) {
        return thread.await(() -> {
           try {
               Call<Void> call = products.save(auth.getToken(), p);
               Response<Void> resp = call.execute();
               if (resp.code() == 201) {
                   return true;
               }

               Log.d(TAG, "Unexpected status " + resp.code());
           } catch (Exception e) {
               Log.d(TAG, "Failed to save product", e);
           }

           return false;
        });
    }
}
