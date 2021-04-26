package ru.medeng.mobile.api;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.medeng.models.order.Order;

public interface OrderService {
    @GET("api/order")
    Call<List<Order>> list(@Header("Authorization") String token);

    @POST("api/order")
    Call<Void> create(@Header("Authorization") String token, @Body Order order);
}
