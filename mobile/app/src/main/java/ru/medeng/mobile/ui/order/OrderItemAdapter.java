package ru.medeng.mobile.ui.order;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.models.order.Item;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder> {
    private List<Item> list;

    public OrderItemAdapter(List<Item> list) {
        this.list = list;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
