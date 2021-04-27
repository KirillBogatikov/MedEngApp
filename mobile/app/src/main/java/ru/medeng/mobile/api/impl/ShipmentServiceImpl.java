package ru.medeng.mobile.api.impl;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.medeng.mobile.api.NetworkThread;
import ru.medeng.mobile.api.rest.ProductService;
import ru.medeng.mobile.api.rest.ShipmentService;
import ru.medeng.models.Rest;
import ru.medeng.models.order.Operation;

public class ShipmentServiceImpl {
    private static final String TAG = ShipmentService.class.getSimpleName();
    private AuthServiceImpl auth;
    private ShipmentService shipments;
    private NetworkThread thread;

    public ShipmentServiceImpl(AuthServiceImpl auth, ShipmentService shipments, NetworkThread thread) {
        this.auth = auth;
        this.shipments = shipments;
        this.thread = thread;
    }

    public List<Rest> getRest() {
        return thread.await(() -> {
            try {
                Call<List<Rest>> call = shipments.listRest(auth.getToken());
                Response<List<Rest>> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body();
                }

                Log.d(TAG, "Unexpected status " + resp.code());
            } catch(Exception e) {
                Log.d(TAG, "Failed to list rests", e);
            }

            return Collections.emptyList();
        });
    }

    public boolean add(Operation o) {
        return thread.await(() -> {
           try {
               Call<Void> call = shipments.add(auth.getToken(), o);
               Response<Void> resp = call.execute();
               if (resp.code() == 200) {
                   return true;
               }

               Log.d(TAG, "Unexpected status "+ resp.code());
           } catch(Exception e) {
               Log.d(TAG, "Failed to save shipment", e);
           }

           return false;
        });
    }
}
