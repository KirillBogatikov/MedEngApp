package ru.medeng.mobile.ui.guest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.models.user.Auth;
import ru.medeng.models.user.Customer;

public class SignupFragment extends Fragment {
    private TextView firstName, lastName, patronymic, phone, email, login, password;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        firstName = root.findViewById(R.id.first_name);
        lastName = root.findViewById(R.id.last_name);
        patronymic = root.findViewById(R.id.patronymic);
        phone = root.findViewById(R.id.phone);
        email = root.findViewById(R.id.email);
        login = root.findViewById(R.id.login);
        password = root.findViewById(R.id.password);

        root.findViewById(R.id.signup).setOnClickListener(v -> {
            Customer customer = new Customer();
            customer.setFirstName(firstName.getText());
            customer.setLastName(lastName.getText());
            customer.setPatronymic(patronymic.getText());
            customer.setPhone(phone.getText());
            customer.setEmail(email.getText());

            Auth auth = new Auth();
            auth.setLogin(login.getText());
            auth.setPassword(password.getText());

            int status = Api.getInstance().getCustomers().signup(customer);
            switch (status) {
                case 200: Toast.makeText(login.getContext(), "Пользователь не найден!", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}
