package ru.medeng.mobile.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import ru.medeng.models.Product;

public interface ProductService {
    @GET("/api/product")
    Call<List<Product>> list();
}
