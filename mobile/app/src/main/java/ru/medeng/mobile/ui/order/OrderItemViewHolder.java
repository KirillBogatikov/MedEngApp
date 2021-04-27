package ru.medeng.mobile.ui.order;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ru.medeng.mobile.R;

public class OrderItemViewHolder extends RecyclerView.ViewHolder {
    private TextView product, count;

    public OrderItemViewHolder(View view) {
        super(view);

        product = view.findViewById(R.id.product);
        count = view.findViewById(R.id.count);
    }

    public void setProduct(CharSequence product) {
        this.product.setText(product);
    }

    public void setCount(int count) {
        this.count.setText(String.valueOf(count));
    }

    public void setListener(CountListener listener) {
        this.count.setEnabled(true);
        this.count.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onChange(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
