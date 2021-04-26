package ru.medeng.mobile.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import ru.medeng.models.LevelHolder;
import ru.medeng.models.TokenHolder;

public interface AuthService {
    @GET("api/login")
    Call<TokenHolder> login(@Query("login") CharSequence login, @Query("password") CharSequence password);

    @GET("api/user/level")
    Call<LevelHolder> getAccessLevel(@Header("Authorization") String token);
}
