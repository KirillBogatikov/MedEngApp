package ru.medeng.mobile.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.medeng.mobile.MainActivity;
import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;

public class LoginFragment extends Fragment {
    private TextView login, password;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        login = root.findViewById(R.id.login);
        password = root.findViewById(R.id.password);
        root.findViewById(R.id.signin).setOnClickListener(v -> {
            boolean ok = Api.getInstance().getAuth().login(login.getText(), password.getText());
            if (ok) {
                Toast.makeText(login.getContext(), R.string.login_success, Toast.LENGTH_LONG).show();
                MainActivity.navigationUpdateListener.invalidateNavigationBar();
            } else {
                Toast.makeText(login.getContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}
