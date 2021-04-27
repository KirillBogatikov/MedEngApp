package ru.medeng.mobile.api.impl;

import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;
import ru.medeng.mobile.api.NetworkThread;
import ru.medeng.mobile.api.rest.CustomerService;
import ru.medeng.models.user.Customer;

public class CustomerServiceImpl {
    private static final String TAG = CustomerService.class.getSimpleName();
    private AuthServiceImpl auth;
    private CustomerService customers;
    private NetworkThread thread;

    public CustomerServiceImpl(AuthServiceImpl auth, CustomerService customers, NetworkThread thread) {
        this.auth = auth;
        this.customers = customers;
        this.thread = thread;
    }

    public int signup(Customer customer) {
        return thread.await(() -> {
            try {
                Call<UUID> call = customers.signup(customer);
                Response<UUID> resp = call.execute();
                return resp.code() == 200 ? 200 : 400;
            } catch(Exception e) {
                Log.d(TAG, "Failed signup", e);
                return 500;
            }
        });
    }

    public Customer getCustomer() {
        return thread.await(() -> {
            try {
                Call<Customer> call = customers.get(auth.getToken());
                Response<Customer> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body();
                }

                return null;
            } catch(Exception e) {
                Log.d(TAG, "Failed to get customer info", e);
                return null;
            }
        });
    }

    public int saveCustomer(Customer customer) {
        return thread.await(() -> {
            try {
                Call<Void> call = customers.save(auth.getToken(), customer);
                Response<Void> resp = call.execute();
                return resp.code();
            } catch (Exception e) {
                return 500;
            }
        });
    }

    public List<Customer> list() {
        return thread.await(() -> {
           try {
                Call<List<Customer>> call = customers.list(auth.getToken());
                Response<List<Customer>> response = call.execute();
                if (response.code() == 200) {
                    return response.body();
                }

                Log.d(TAG, "Unexpected status " + response.code());
           } catch (Exception e) {
               Log.d(TAG, "Failed to list customers", e);
           }

           return Collections.emptyList();
        });
    }

    private Customer selected;
    public void select(Customer c) {
        this.selected = c;
    }

    public Customer getSelected() {
        return selected;
    }
}
