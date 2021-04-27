package ru.medeng.mobile.api.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import ru.medeng.models.Rest;
import ru.medeng.models.order.Operation;

public interface ShipmentService {
    @GET("/api/shipment/rest")
    Call<List<Rest>> listRest(@Header("Authorization") String token);

    @POST("/api/shipment")
    Call<Void> add(@Header("Authorization") String token, @Body Operation o);
}
