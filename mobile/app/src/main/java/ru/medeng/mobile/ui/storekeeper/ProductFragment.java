package ru.medeng.mobile.ui.storekeeper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.ProductServiceImpl;
import ru.medeng.mobile.ui.product.ProductAdapter;
import ru.medeng.models.Product;
import ru.medeng.models.Rest;
import ru.medeng.models.order.Operation;
import ru.medeng.models.user.AccessLevel;

public class ProductFragment extends Fragment {
    private View root;
    private ProductAdapter adapter;
    private RecyclerView list;
    private ProductServiceImpl productService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);

        list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        final Api api = Api.getInstance();
        productService = api.getProducts();

        List<Product> productList = productService.listProducts();
        invalidateList();

        root.findViewById(R.id.add).setVisibility(View.VISIBLE);
        root.findViewById(R.id.add).setOnClickListener(v -> {
            ProductAlert productAlert = new ProductAlert(getContext(), this::invalidateList, () -> {
                ShipmentAlert shipmentAlert = new ShipmentAlert(getContext());
                shipmentAlert.setProducts(productList);
                shipmentAlert.show();
            });
            productAlert.show();
        });

        return root;
    }

    void invalidateList() {
        adapter = new ProductAdapter(null, productService.listProducts(), false);
        list.setAdapter(adapter);
    }
}