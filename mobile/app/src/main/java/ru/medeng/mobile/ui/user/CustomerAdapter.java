package ru.medeng.mobile.ui.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.models.user.Customer;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {
    private List<Customer> list;
    private CustomerClickListener listener;

    public CustomerAdapter(List<Customer> list, CustomerClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CustomerViewHolder(inflater.inflate(R.layout.view_customer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer c = list.get(position);
        holder.setFirstName(c.getFirstName());
        holder.setLastName(c.getLastName());
        holder.setPatronymic(c.getPatronymic());
        holder.setCustomer(c);
        holder.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
