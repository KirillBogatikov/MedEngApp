package ru.medeng.mobile.ui.order;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.medeng.mobile.R;
import ru.medeng.models.order.Item;
import ru.medeng.models.order.Order;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    private TextView date, status;
    private RecyclerView items;
    private OrderClickListener listener;
    private Order order;

    public OrderViewHolder(View view, OrderClickListener listener) {
        super(view);

        date = view.findViewById(R.id.date);
        status = view.findViewById(R.id.status);
        items = view.findViewById(R.id.items);
        items.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.listener = listener;

        view.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderClick(order);
            }
        });
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.date.setText(sdf.format(date));
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public void setItems(List<Item> items) {
        this.items.setAdapter(new OrderItemAdapter(items, null));
    }

    public void setOrder(Order o) {
        this.order = o;
    }
}