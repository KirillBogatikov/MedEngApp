package ru.medeng.mobile.ui.guest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.OrderServiceImpl;
import ru.medeng.mobile.api.impl.ProductServiceImpl;
import ru.medeng.mobile.ui.product.ProductAdapter;
import ru.medeng.models.Product;

public class ProductFragment extends Fragment {
    private View root;
    private ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        final Api api = Api.getInstance();
        final ProductServiceImpl productService = api.getProducts();
        final OrderServiceImpl orderService = api.getOrders();
        List<Product> productList = productService.listProducts();
        adapter = new ProductAdapter(null, productList, false);
        list.setAdapter(adapter);
        orderService.setOrderMap(adapter.getOrderMap());

        return root;
    }
}