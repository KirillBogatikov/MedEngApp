package ru.medeng.mobile.ui.employee;

import android.graphics.Rect;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.AuthServiceImpl;
import ru.medeng.mobile.api.impl.CustomerServiceImpl;
import ru.medeng.mobile.api.impl.OrderServiceImpl;
import ru.medeng.mobile.ui.order.OrderAdapter;
import ru.medeng.mobile.ui.order.OrderItemAdapter;
import ru.medeng.models.Product;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;
import ru.medeng.models.order.StatusInfo;
import ru.medeng.models.user.Customer;

public class OrderListFragment extends Fragment {
    private Spinner currentOrderStatus;
    private RecyclerView allOrdersList;
    private UUID orderId;
    private AuthServiceImpl authService;
    private CustomerServiceImpl customerService;
    private OrderServiceImpl orderService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);

        currentOrderStatus = root.findViewById(R.id.current_order_status);
        currentOrderStatus.setVisibility(View.VISIBLE);
        RecyclerView currentOrderList = root.findViewById(R.id.current_order_items);
        currentOrderList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        final Api api = Api.getInstance();
        authService = api.getAuth();
        customerService = api.getCustomers();
        orderService = api.getOrders();

        allOrdersList = root.findViewById(R.id.orders);
        allOrdersList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        invalidateList();

        root.findViewById(R.id.save).setOnClickListener(v -> {
            int id = currentOrderStatus.getSelectedItemPosition();
            switch (orderService.saveStatus(orderId, Status.values()[id])) {
                case 200:
                    Toast.makeText(getContext(), "Статус обновлен", Toast.LENGTH_SHORT).show();
                    invalidateList();
                    break;
                case 403:
                    Toast.makeText(getContext(), "Вы не можете установить этот статус", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        return root;
    }

    List<Order> getOrders() {
        switch (authService.getCachedAccessLevel()) {
            case Storekeeper:
                return orderService.listOrders();
            case Operator:
                Customer customer = customerService.getSelected();
                if (customer == null) {
                    Toast.makeText(getContext(), "Сначала выберите клиента", Toast.LENGTH_LONG).show();
                 } else {
                    return orderService.listCustomerOrders(customer.getId());
                }
        }

        return Collections.emptyList();
    }

    void invalidateList() {
        allOrdersList.setAdapter(new OrderAdapter(getOrders(), o -> {
            currentOrderStatus.setSelection(o.getStatus().getCode());
            orderId = o.getId();
        }));
    }
}