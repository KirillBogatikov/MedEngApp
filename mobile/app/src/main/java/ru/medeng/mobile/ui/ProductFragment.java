package ru.medeng.mobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.models.Product;
import ru.medeng.models.user.AccessLevel;

public class ProductFragment extends Fragment {
    private static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description;

        public ProductViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setDescription(String description) {
            this.description.setText(description);
        }
    }

    private static class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
        private List<Product> productList;

        public ProductAdapter(List<Product> productList) {
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ProductViewHolder(inflater.inflate(R.layout.view_product, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Product p = productList.get(position);
            holder.setName(p.getName());
            holder.setDescription(p.getDescription());
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new ProductAdapter(Api.getInstance().listProducts()));

        if (Api.getInstance().getCachedAccessLevel() == AccessLevel.Operator) {
            root.findViewById(R.id.add).setVisibility(View.VISIBLE);
        };

        return root;
    }
}