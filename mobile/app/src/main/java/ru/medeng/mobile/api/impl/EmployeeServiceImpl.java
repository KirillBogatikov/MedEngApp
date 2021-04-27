package ru.medeng.mobile.api.impl;

import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;
import ru.medeng.mobile.api.rest.CustomerService;
import ru.medeng.mobile.api.rest.EmployeeService;
import ru.medeng.models.user.Employee;
import ru.medeng.tools.NetworkThread;

public class EmployeeServiceImpl {
    private static final String TAG = EmployeeService.class.getSimpleName();
    private AuthServiceImpl auth;
    private EmployeeService employees;
    private NetworkThread thread;

    public EmployeeServiceImpl(AuthServiceImpl auth, EmployeeService employees, NetworkThread thread) {
        this.auth = auth;
        this.employees = employees;
        this.thread = thread;
    }

    public List<Employee> list() {
        return thread.await(() -> {
           try {
               Call<List<Employee>> call = employees.list(auth.getToken());
               Response<List<Employee>> resp = call.execute();
               if (resp.code() == 200) {
                   return resp.body();
               }

               Log.d(TAG, "Unexpected status " + resp.code());
           } catch(Exception e) {
               Log.d(TAG, "Failed to list employees", e);
           }

           return Collections.emptyList();
        });
    }

    public boolean delete(UUID id) {
        return thread.await(() -> {
            try {
                Call<Void> call = employees.delete(auth.getToken(), id);
                Response<Void> resp = call.execute();
                if (resp.code() == 200) {
                    return true;
                }

                Log.d(TAG, "Unexpected status: " + resp.code());
            } catch(Exception e) {
                Log.d(TAG, "Failed to delete employee", e);
            }

            return false;
        });
    }
}
