package ru.medeng.mobile.api.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;
import ru.medeng.mobile.api.NetworkThread;
import ru.medeng.mobile.api.rest.OrderService;
import ru.medeng.models.Product;
import ru.medeng.models.order.Item;
import ru.medeng.models.order.Operation;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;

public class OrderServiceImpl {
    private static final String TAG = OrderService.class.getSimpleName();
    private AuthServiceImpl auth;
    private OrderService orders;
    private NetworkThread thread;
    private Map<Product, Integer> currentOrderItems;

    public OrderServiceImpl(AuthServiceImpl auth, OrderService orders, NetworkThread thread) {
        this.auth = auth;
        this.orders = orders;
        this.thread = thread;
    }

    public List<Order> listOrders() {
        return thread.await(() -> {
            try {
                Call<List<Order>> call = orders.list(auth.getToken());
                Response<List<Order>> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body();
                }

                Log.d(TAG, "Unexpected status: " + resp.code());
            } catch (Exception e) {
                Log.d(TAG, "Failed to list orders", e);
            }

            return Collections.emptyList();
        });
    }

    public void setOrderMap(Map<Product, Integer> currentOrderItems) {
        this.currentOrderItems = currentOrderItems;
    }

    public Map<Product, Integer> getOrderMap() {
        return currentOrderItems;
    }

    public List<Item> getOrderItems() {
        List<Item> items = new ArrayList<>();

        for (Map.Entry<Product, Integer> e : currentOrderItems.entrySet()) {
            Item item = new Item();
            Operation o = new Operation();
            o.setProduct(e.getKey());
            o.setCount(e.getValue());
            item.setBooking(o);
            items.add(item);
        }

        return items;
    }

    public int createOrder() {
        return thread.await(() -> {
            try {
                Order order = new Order();

                List<Item> items = new ArrayList<>();
                for (Item oldItem : getOrderItems()) {
                    Operation oldBooking = oldItem.getBooking();
                    Product oldProduct = oldBooking.getProduct();

                    Item newItem = new Item();
                    Operation newBooking = new Operation();
                    Product newProduct = new Product();

                    newProduct.setId(oldProduct.getId());
                    newBooking.setProduct(newProduct);
                    newBooking.setCount(oldBooking.getCount());
                    newItem.setBooking(newBooking);
                    items.add(newItem);
                }
                order.setItems(items);

                Call<Void> call = orders.create(auth.getToken(), order);
                Response<Void> resp = call.execute();
                return resp.code();
            } catch(Exception e) {
                Log.d(TAG, "Failed to create order", e);
                return 500;
            }
        });
    }

    public List<Order> listCustomerOrders(UUID id) {
        return thread.await(() -> {
            try {
                Call<List<Order>> call = orders.listCustomer(auth.getToken(), id);
                Response<List<Order>> resp = call.execute();
                if (resp.code() == 200) {
                    return resp.body();
                }

                Log.d(TAG, "Unexpected status " + resp.code());
            } catch(Exception e) {
                Log.d(TAG, "Failed to list customer's order", e);
            }

            return Collections.emptyList();
        });
    }

    public int saveStatus(UUID orderId, Status value) {
        return thread.await(() -> {
            try {
                Call<Void> call = orders.saveStatus(auth.getToken(), orderId, value);
                Response<Void> resp = call.execute();
                if (resp.code() == 200) {
                    return 200;
                }

                Log.d(TAG, "Unexpected status " + resp.code());
                return resp.code();
            } catch(Exception e) {
                Log.d(TAG, "Failed to list customer's order", e);
                return 500;
            }
        });
    }
}
