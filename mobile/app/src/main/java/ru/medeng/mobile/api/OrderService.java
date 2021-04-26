package ru.medeng.mobile.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import ru.medeng.models.order.Order;

public interface OrderService {
    @GET("api/order")
    Call<List<Order>> list(@Header("Authorization") String token);

}
