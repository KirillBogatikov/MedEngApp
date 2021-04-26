package ru.medeng.mobile.api;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.medeng.models.user.Customer;

public interface CustomerService {
    @POST("api/customer")
    Call<UUID> signup(@Body Customer customer);

    @GET("api/customer")
    Call<Customer> get(@Header("Authorization") String token);
}
