package ru.medeng.mobile.ui.storekeeper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.EmployeeServiceImpl;

public class EmployeeListFragment extends Fragment {
    private RecyclerView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_employees, container, false);

        final Api api = Api.getInstance();
        final EmployeeServiceImpl employeeService = api.getEmployees();

        list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(container.getContext()));
        invalidateList(employeeService);

        root.findViewById(R.id.add).setOnClickListener(v -> {
            EmployeeAlert alert = new EmployeeAlert(getContext(), () -> {
                invalidateList(employeeService);
            });
            alert.show();
        });

        return root;
    }

    void invalidateList(EmployeeServiceImpl employeeService) {
        list.setAdapter(new EmployeeAdapter(employeeService.list(), e -> {
            if(employeeService.delete(e.getId())) {
                Toast.makeText(getContext(), "Сотрудник удален", Toast.LENGTH_SHORT).show();
                invalidateList(employeeService);
            }
        }));
    }

}
