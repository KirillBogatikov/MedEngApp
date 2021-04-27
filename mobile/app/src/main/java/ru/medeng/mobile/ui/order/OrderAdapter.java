package ru.medeng.mobile.ui.order;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;
import ru.medeng.models.order.StatusInfo;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private List<Order> list;
    private OrderClickListener listener;

    public OrderAdapter(List<Order> list, OrderClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new OrderViewHolder(inflater.inflate(R.layout.view_order, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order o = list.get(position);
        List<StatusInfo> history = o.getHistory();
        Collections.sort(history, (a, b) -> a.getDate().compareTo(b.getDate()));
        holder.setDate(history.get(0).getDate());
        holder.setStatus(history.get(history.size() - 1).getStatus());
        holder.setItems(o.getItems());
        holder.setOrder(o);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
