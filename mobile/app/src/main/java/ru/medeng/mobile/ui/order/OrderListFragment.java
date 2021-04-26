package ru.medeng.mobile.ui.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.models.Product;

public class OrderListFragment extends Fragment {

    private Spinner currentOrderStatus;
    private RecyclerView currentOrderList, allOrdersList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);

        currentOrderStatus = root.findViewById(R.id.current_order_status);
        currentOrderList = root.findViewById(R.id.current_order_items);
        currentOrderList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        Map<Product, Integer> currentOrderItems = Api.getInstance().getOrderMap();
        if (currentOrderItems != null) {
            currentOrderList.setAdapter(new OrderItemAdapter(Api.getInstance().getOrderItems(), currentOrderItems));
        }

        allOrdersList = root.findViewById(R.id.orders);
        allOrdersList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        allOrdersList.setAdapter(new OrderAdapter(Api.getInstance().listOrders()));

        root.findViewById(R.id.save).setOnClickListener(v -> {
            Api.getInstance().createOrder();
        });

        return root;
    }
}