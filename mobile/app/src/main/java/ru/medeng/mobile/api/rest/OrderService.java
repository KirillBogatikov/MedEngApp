package ru.medeng.mobile.api.rest;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;

public interface OrderService {
    @GET("api/order")
    Call<List<Order>> list(@Header("Authorization") String token);

    @POST("api/order")
    Call<Void> create(@Header("Authorization") String token, @Body Order order);

    @GET("api/order/customer/{id}")
    Call<List<Order>> listCustomer(@Header("Authorization") String token, @Path("id") UUID id);

    @PATCH("api/order/{id}")
    Call<Void> saveStatus(@Header("Authorization") String token, @Path("id") UUID orderId, @Query("statusText") Status value);
}
