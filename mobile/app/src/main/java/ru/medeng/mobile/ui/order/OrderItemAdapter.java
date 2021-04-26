package ru.medeng.mobile.ui.order;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import ru.medeng.mobile.R;
import ru.medeng.models.Product;
import ru.medeng.models.order.Item;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder> {
    private List<Item> list;
    private Map<Product, Integer> cart;

    public OrderItemAdapter(List<Item> list, Map<Product, Integer> cart) {
        this.list = list;
        this.cart = cart;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new OrderItemViewHolder(inflater.inflate(R.layout.view_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        Item i = list.get(position);
        holder.setProduct(i.getBooking().getProduct().getName());
        holder.setCount(i.getBooking().getCount());
        if (cart != null) {
            holder.setListener(c -> {
                Product p = i.getBooking().getProduct();
                cart.put(p, c);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
