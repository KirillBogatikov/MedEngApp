package ru.medeng.mobile.ui.storekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.ProductServiceImpl;
import ru.medeng.models.Product;

public class ProductAlert {
    private AlertDialog dialog;
    private ProductServiceImpl productService;

    public ProductAlert(Context ctx, Runnable runPositive, Runnable runNeutral) {
        productService = Api.getInstance().getProducts();

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Добавить продукт");

        final LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.dialog_create_product, null);

        TextView name = view.findViewById(R.id.name);
        TextView description = view.findViewById(R.id.description);

        builder.setPositiveButton(R.string.save_label, (d, w) -> {
            Product p = new Product();
            p.setName(name.getText());
            p.setDescription(description.getText());

            if(productService.add(p)) {
                Toast.makeText(ctx, "Продукт добавлен", Toast.LENGTH_SHORT).show();
            }

            runPositive.run();
        });
        builder.setNeutralButton("Зарегистрировать поставку", (d, w) -> {
            runNeutral.run();
            dialog.dismiss();
        });

        builder.setView(view);
        dialog = builder.create();
    }

    public void show() {
        dialog.show();
    }
}
