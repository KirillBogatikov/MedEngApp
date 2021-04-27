package ru.medeng.mobile.ui.storekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.ShipmentServiceImpl;
import ru.medeng.models.Product;
import ru.medeng.models.order.Operation;

public class ShipmentAlert {
    private AlertDialog dialog;
    private ShipmentServiceImpl shipmentService;
    private Spinner spinner;
    private List<Product> productList;
    private TextView countView;

    public ShipmentAlert(Context ctx) {
        shipmentService = Api.getInstance().getShipments();

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Добавить поставку");

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.dialog_shipment, null);
        spinner = view.findViewById(R.id.product);
        TextView count = view.findViewById(R.id.count);

        builder.setPositiveButton(R.string.save_label, (d, w) -> {
            Operation shipment = new Operation();
            int index = spinner.getSelectedItemPosition();
            shipment.setProduct(productList.get(index));
            shipment.setCount(Integer.parseInt(count.getText().toString()));

            if (shipmentService.add(shipment)) {
                Toast.makeText(ctx, "Информация о поставке сохранена", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    public void setProducts(List<Product> products) {
        this.productList = products;
        List<CharSequence> productNames = new ArrayList<>();
        for (Product p : products) {
            productNames.add(p.getName());
        }
        spinner.setAdapter(new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_list_item_1, productNames));
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
