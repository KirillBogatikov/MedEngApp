package ru.medeng.mobile.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.ProductServiceImpl;
import ru.medeng.models.Product;
import ru.medeng.models.Rest;
import ru.medeng.models.order.Operation;
import ru.medeng.models.user.AccessLevel;

public class ProductFragment extends Fragment {
    private View root;
    private ProductAdapter adapter;
    private AlertDialog addProductDialog, addShipmentDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        final Api api = Api.getInstance();
        final ProductServiceImpl productService = api.getProducts();

        AccessLevel level = api.getAuth().getCachedAccessLevel();
        List<Product> productList = Collections.emptyList();
        List<Rest> restList = null;

        switch(level) {
            case Operator:
                restList = api.getShipments().getRest();
                break;
            case Storekeeper:
                productList = productService.listProducts();
                root.findViewById(R.id.add).setVisibility(View.VISIBLE);
                break;
            case Customer: case Guest:
                productList = productService.listProducts();
                break;
        };

        adapter = new ProductAdapter(restList, productList);
        list.setAdapter(adapter);
        api.getOrders().setOrderMap(adapter.getOrderMap());

        root.findViewById(R.id.add).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
            builder.setTitle("Добавить продукт");

            View view = inflater.inflate(R.layout.dialog_create_product, null);

            TextView name = view.findViewById(R.id.name);
            TextView description = view.findViewById(R.id.description);

            builder.setPositiveButton(R.string.save_label, (d, w) -> {
                Product p = new Product();
                p.setName(name.getText());
                p.setDescription(description.getText());

               if(api.getProducts().add(p)) {
                   Toast.makeText(getContext(), "Продукт добавлен", Toast.LENGTH_SHORT).show();
               }
            });
            builder.setNeutralButton("Зарегистрировать поставку", (d, w) -> {
                showShipmentDialog(api.getProducts().listProducts(), inflater, api);
                addProductDialog.dismiss();
            });

            builder.setView(view);
            addProductDialog = builder.create();
            addProductDialog.show();
        });

        return root;
    }

    private void showShipmentDialog(List<Product> productList, LayoutInflater inflater, Api api) {
        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        builder.setTitle("Добавить поставку");

        View view = inflater.inflate(R.layout.dialog_shipment, null);
        Spinner spinner = view.findViewById(R.id.product);
        List<CharSequence> productNames = new ArrayList<>();
        for (Product p : productList) {
            productNames.add(p.getName());
        }
        spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, productNames));
        TextView count = view.findViewById(R.id.count);

        builder.setPositiveButton(R.string.save_label, (d, w) -> {
            Operation o = new Operation();
            int i = spinner.getSelectedItemPosition();
            o.setProduct(productList.get(i));
            o.setCount(Integer.parseInt(count.getText().toString()));
            if (api.getShipments().add(o)) {
                Toast.makeText(getContext(), "Информация о поставке сохранена", Toast.LENGTH_SHORT).show();
            }

            addShipmentDialog.dismiss();
        });

        builder.setView(view);
        addShipmentDialog = builder.create();
        addShipmentDialog.show();
    }
}