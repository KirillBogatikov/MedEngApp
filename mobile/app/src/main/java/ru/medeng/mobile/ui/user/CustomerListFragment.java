package ru.medeng.mobile.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.medeng.mobile.MainActivity;
import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.ui.product.ProductAdapter;

public class CustomerListFragment extends Fragment {
    private View root;
    private ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_customers, container, false);

        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        Api api = Api.getInstance();
        list.setAdapter(new CustomerAdapter(api.getCustomers().list(), c -> {
            api.getCustomers().select(c);
            Toast.makeText(getContext(), "Клиент выбран. Перейдите на вкладку Заказы для просмотра списка его заказов", Toast.LENGTH_LONG).show();
        }));

        root.findViewById(R.id.logout).setOnClickListener(v -> {
            Api.getInstance().logout();
            MainActivity.navigationUpdateListener.invalidateNavigationBar();
        });

        return root;
    }
}
