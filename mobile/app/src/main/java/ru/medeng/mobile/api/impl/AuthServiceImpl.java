package ru.medeng.mobile.api.impl;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;
import ru.medeng.tools.NetworkThread;
import ru.medeng.mobile.api.rest.AuthService;
import ru.medeng.models.LevelHolder;
import ru.medeng.models.TokenHolder;
import ru.medeng.models.user.AccessLevel;

public class AuthServiceImpl {
    private static final String TAG = AuthService.class.getSimpleName();
    private AuthService auth;
    private NetworkThread thread;
    private String token;
    private AccessLevel level;

    public AuthServiceImpl(AuthService auth, NetworkThread thread) {
        this.auth = auth;
        this.thread = thread;
    }

    public boolean login(CharSequence login, CharSequence password) {
        token = thread.await(() -> {
            try {
                Call<TokenHolder> call = auth.login(login, password);
                Response<TokenHolder> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body().getToken();
                }

                return null;
            } catch(Exception e) {
                Log.e(TAG, "Failed to login", e);
                return null;
            }
        });

        return token != null;
    }

    public void logout() {
        this.token = null;
        this.level = AccessLevel.Guest;
    }

    public AccessLevel getAccessLevel() {
        AccessLevel level = thread.await(() -> {
            try {
                Call<LevelHolder> call = auth.getAccessLevel(token);
                Response<LevelHolder> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body().getLevel();
                }

                return AccessLevel.Guest;
            } catch(Exception e) {
                return AccessLevel.Guest;
            }
        });

        this.level = level;
        return level;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccessLevel getCachedAccessLevel() {
        return level;
    }

}
