package ru.medeng.mobile.ui.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.medeng.mobile.R;
import ru.medeng.models.Product;
import ru.medeng.models.order.Item;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> productList;
    private Map<Product, Integer> orderMap;
    private boolean allowCart;

    public ProductAdapter(List<Product> productList, boolean allowCart) {
        this.productList = productList;
        this.allowCart = allowCart;
        this.orderMap = new HashMap<>();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ProductViewHolder(inflater.inflate(R.layout.view_product, parent, false), orderMap);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = productList.get(position);
        holder.setName(p.getName());
        holder.setDescription(p.getDescription());
        holder.setProduct(p, allowCart);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public Map<Product, Integer> getOrderMap() {
        return orderMap;
    }
}
