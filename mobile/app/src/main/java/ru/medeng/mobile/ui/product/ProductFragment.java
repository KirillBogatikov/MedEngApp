package ru.medeng.mobile.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.models.user.AccessLevel;

public class ProductFragment extends Fragment {
    private View root;
    private ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        Api api = Api.getInstance();
        boolean cart = false;
        AccessLevel level = api.getCachedAccessLevel();
        switch(level) {
            case Operator: root.findViewById(R.id.add).setVisibility(View.VISIBLE); break;
            case Customer: cart = true; break;
        };

        adapter = new ProductAdapter(Api.getInstance().listProducts(), cart);
        list.setAdapter(adapter);
        api.setOrderMap(adapter.getOrderMap());

        return root;
    }
}