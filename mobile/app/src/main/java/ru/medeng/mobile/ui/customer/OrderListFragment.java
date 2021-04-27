package ru.medeng.mobile.ui.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.OrderServiceImpl;
import ru.medeng.mobile.ui.order.OrderAdapter;
import ru.medeng.mobile.ui.order.OrderItemAdapter;

public class OrderListFragment extends Fragment {
    private RecyclerView allOrdersList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);

        RecyclerView currentOrderList = root.findViewById(R.id.current_order_items);
        currentOrderList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        final Api api = Api.getInstance();
        final OrderServiceImpl orderService = api.getOrders();
        currentOrderList.setAdapter(new OrderItemAdapter(orderService.getOrderItems(), orderService.getOrderMap()));

        allOrdersList = root.findViewById(R.id.orders);
        allOrdersList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        invalidateList(orderService);

        root.findViewById(R.id.save).setOnClickListener(v -> {
            if (orderService.createOrder() == 201) {
                print("Заказ создан");
                orderService.setOrderMap(null);
                currentOrderList.setAdapter(new OrderItemAdapter(orderService.getOrderItems(), orderService.getOrderMap()));
                invalidateList(orderService);
            } else {
                print("Ошибка при создании заказа");
            }
        });

        return root;
    }

    void invalidateList(OrderServiceImpl orderService) {

        allOrdersList.setAdapter(new OrderAdapter(orderService.listOrders(), null));
    }

    void print(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}