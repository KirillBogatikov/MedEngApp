package ru.medeng.mobile.ui.user;

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
import ru.medeng.models.user.Auth;
import ru.medeng.models.user.Customer;

public class AccountFragment extends Fragment {
    private TextView firstName, lastName, patronymic, phone, email, login, password;
    private Customer customer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        firstName = root.findViewById(R.id.first_name);
        lastName = root.findViewById(R.id.last_name);
        patronymic = root.findViewById(R.id.patronymic);
        phone = root.findViewById(R.id.phone);
        email = root.findViewById(R.id.email);
        login = root.findViewById(R.id.login);
        password = root.findViewById(R.id.password);

        root.findViewById(R.id.save).setOnClickListener(v -> {
            customer.setFirstName(firstName.getText());
            customer.setLastName(lastName.getText());
            customer.setPatronymic(patronymic.getText());
            customer.setPhone(phone.getText());
            customer.setEmail(email.getText());

            Auth auth = customer.getAuth();
            auth.setLogin(login.getText());
            auth.setPassword(password.getText());

            int status = Api.getInstance().getCustomers().saveCustomer(customer);
            switch (status) {
                case 200: Toast.makeText(login.getContext(), "Данные сохранены", Toast.LENGTH_LONG).show(); break;
                default: Toast.makeText(login.getContext(), "Произошла непредвиденная ошибка. Попробуйте позже", Toast.LENGTH_LONG).show(); break;
            }
        });

        root.findViewById(R.id.logout).setOnClickListener(v -> {
            Api.getInstance().logout();
            MainActivity.navigationUpdateListener.invalidateNavigationBar();
        });

        customer = Api.getInstance().getCustomers().getCustomer();
        if (customer != null) {
            firstName.setText(customer.getFirstName());
            lastName.setText(customer.getLastName());
            patronymic.setText(customer.getPatronymic());
            phone.setText(customer.getPhone());
            email.setText(customer.getEmail());
            login.setText(customer.getAuth().getLogin());
        }

        return root;
    }
}