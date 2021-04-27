package ru.medeng.mobile.api.rest;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Part;
import retrofit2.http.Path;
import ru.medeng.models.user.Employee;

public interface EmployeeService {
    @GET("/api/employee")
    Call<List<Employee>> list(@Header("Authorization") String token);

    @DELETE("/api/employee/{id}")
    Call<Void> delete(@Header("Authorization") String token, @Path("id") UUID id);
}
