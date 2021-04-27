package ru.medeng.mobile.ui.user;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.medeng.mobile.R;
import ru.medeng.models.user.Customer;

public class CustomerViewHolder extends RecyclerView.ViewHolder {
    private TextView lastName, firstName, patronymic;
    private Customer c;
    private CustomerClickListener listener;

    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);

        firstName = itemView.findViewById(R.id.first_name);
        lastName = itemView.findViewById(R.id.last_name);
        patronymic = itemView.findViewById(R.id.patronymic);

        itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.showOrders(c);
            }
        });
    }

    public void setFirstName(CharSequence n) {
        this.firstName.setText(n);
    }

    public void setLastName(CharSequence n) {
        this.lastName.setText(n);
    }

    public void setPatronymic(CharSequence n) {
        this.patronymic.setText(n);
    }

    public void setCustomer(Customer c) {
        this.c = c;
    }

    public void setListener(CustomerClickListener listener) {
        this.listener = listener;
    }
}
