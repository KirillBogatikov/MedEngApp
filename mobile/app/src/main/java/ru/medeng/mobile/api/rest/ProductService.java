package ru.medeng.mobile.api.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import ru.medeng.models.Product;

public interface ProductService {
    @GET("/api/product")
    Call<List<Product>> list();

    @PUT("/api/product")
    Call<Void> save(@Header("Authorization") String token, @Body Product p);
}
