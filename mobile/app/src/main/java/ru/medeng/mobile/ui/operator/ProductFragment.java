package ru.medeng.mobile.ui.operator;

import android.os.Bundle;
import android.util.Log;
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
import ru.medeng.mobile.api.impl.OrderServiceImpl;
import ru.medeng.mobile.api.impl.ProductServiceImpl;
import ru.medeng.mobile.api.impl.ShipmentServiceImpl;
import ru.medeng.mobile.ui.product.ProductAdapter;
import ru.medeng.models.Product;
import ru.medeng.models.Rest;
import ru.medeng.models.order.Operation;
import ru.medeng.models.user.AccessLevel;

public class ProductFragment extends Fragment {
    private View root;
    private ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        final Api api = Api.getInstance();
        final ShipmentServiceImpl shipmentService = api.getShipments();

        List<Rest> restList = shipmentService.getRest();
        adapter = new ProductAdapter(restList, null, false);
        list.setAdapter(adapter);

        return root;
    }
}