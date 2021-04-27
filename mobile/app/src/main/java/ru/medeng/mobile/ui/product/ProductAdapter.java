package ru.medeng.mobile.ui.product;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.models.Product;
import ru.medeng.models.Rest;
import ru.medeng.models.order.Item;
import ru.medeng.models.user.AccessLevel;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Rest> restList;
    private List<Product> productList;
    private Map<Product, Integer> orderMap;

    public ProductAdapter(List<Rest> restList, List<Product> productList, boolean allowCart) {
        this.restList = restList;
        this.productList = productList;
        if (allowCart) {
            this.orderMap = new HashMap<>();
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ProductViewHolder(inflater.inflate(R.layout.view_product, parent, false), orderMap);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (productList != null) {
            Product p = productList.get(position);
            holder.setName(p.getName());
            holder.setDescription(p.getDescription());
            holder.setProduct(p, orderMap != null);
        } else if (restList != null) {
            Rest rest = restList.get(position);
            Product p = rest.getProduct();
            holder.setName(p.getName());
            holder.setDescription(p.getDescription());
            holder.setAvailableCount(rest.getAvailable());
            holder.setBookedCount(rest.getBooked());
        }
    }

    @Override
    public int getItemCount() {
        if (restList != null) {
            return restList.size();
        }
        if (productList != null) {
            return productList.size();
        }

        return 0;
    }

    public Map<Product, Integer> getOrderMap() {
        return orderMap;
    }
}
