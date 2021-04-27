package ru.medeng.mobile.ui.product;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.Map;
import java.util.UUID;

import ru.medeng.mobile.R;
import ru.medeng.models.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private TextView name, description;
    private View addCart, removeCart;
    private View availableParent, bookedParent;
    private TextView availableCount, bookedCount;
    private Product product;
    private Map<Product, Integer> cart;

    public ProductViewHolder(View view, Map<Product, Integer> cart) {
        super(view);
        this.cart = cart;

        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        addCart = view.findViewById(R.id.add_cart);
        addCart.setOnClickListener(v -> {
            cart.put(product, 1);
            addCart.setVisibility(View.GONE);
            removeCart.setVisibility(View.VISIBLE);
        });

        removeCart = view.findViewById(R.id.remove_cart);
        removeCart.setOnClickListener(v -> {
            cart.remove(product);
            removeCart.setVisibility(View.GONE);
            addCart.setVisibility(View.VISIBLE);
        });

        availableParent = view.findViewById(R.id.available_parent);
        availableCount = availableParent.findViewById(R.id.available_count);
        bookedParent = view.findViewById(R.id.booked_parent);
        bookedCount = bookedParent.findViewById(R.id.booked_count);
    }

    public void setName(CharSequence name) {
        this.name.setText(name);
    }

    public void setDescription(CharSequence description) {
        this.description.setText(description);
    }

    public void setProduct(Product p, boolean allowCart) {
        this.product = p;

        if (allowCart) {
            if (cart.get(p) == null) {
                addCart.setVisibility(View.VISIBLE);
            } else {
                removeCart.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setAvailableCount(int availableCount) {
        availableParent.setVisibility(View.VISIBLE);
        this.availableCount.setText(String.valueOf(availableCount));
    }

    public void setBookedCount(int bookedCount) {
        bookedParent.setVisibility(View.VISIBLE);
        this.bookedCount.setText(String.valueOf(bookedCount));
    }
}
