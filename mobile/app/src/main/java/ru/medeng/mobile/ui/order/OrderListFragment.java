package ru.medeng.mobile.ui.order;

import android.os.Bundle;
import android.util.Log;
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
import ru.medeng.mobile.api.impl.OrderServiceImpl;
import ru.medeng.models.Product;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;
import ru.medeng.models.order.StatusInfo;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Customer;

public class OrderListFragment extends Fragment {

    private Spinner currentOrderStatus;
    private RecyclerView currentOrderList, allOrdersList;
    private UUID orderId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);

        currentOrderStatus = root.findViewById(R.id.current_order_status);
        currentOrderList = root.findViewById(R.id.current_order_items);
        currentOrderList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        Api api = Api.getInstance();
        OrderServiceImpl orderService = api.getOrders();
        Map<Product, Integer> currentOrderItems = orderService.getOrderMap();
        if (currentOrderItems != null) {
            currentOrderList.setAdapter(new OrderItemAdapter(orderService.getOrderItems(), currentOrderItems));
        }

        allOrdersList = root.findViewById(R.id.orders);
        allOrdersList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        List<Order> list = null;
        switch (api.getAuth().getCachedAccessLevel()) {
            case Customer:
                list = orderService.listOrders();
                break;
            case Storekeeper:
                list = orderService.listOrders();
                currentOrderStatus.setVisibility(View.VISIBLE);
                break;
            case Operator:
                Customer customer = api.getCustomers().getSelected();
                if (customer == null) {
                    Toast.makeText(getContext(), "Сначала выберите клиента", Toast.LENGTH_LONG).show();
                    list = Collections.emptyList();
                } else {
                    list = orderService.listCustomerOrders(customer.getId());
                }
                currentOrderStatus.setVisibility(View.VISIBLE);
                break;
        }

        allOrdersList.setAdapter(new OrderAdapter(list, o -> {
            List<Status> values = Arrays.asList(Status.values());
            List<StatusInfo> history = o.getHistory();
            int i = values.indexOf(history.get(history.size() - 1).getStatus());

            currentOrderStatus.setSelection(i);
            orderId = o.getId();
        }));

        root.findViewById(R.id.save).setOnClickListener(v -> {
            if (orderId == null) {
                orderService.createOrder();
            } else {
                int id = currentOrderStatus.getSelectedItemPosition();
                switch (orderService.saveStatus(orderId, Status.values()[id])) {
                    case 200:
                        Toast.makeText(getContext(), "Статус обновлен", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(getContext(), "Вы не можете установить этот статус", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        return root;
    }
}