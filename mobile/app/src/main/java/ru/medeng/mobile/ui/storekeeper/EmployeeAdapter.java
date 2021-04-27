package ru.medeng.mobile.ui.storekeeper;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.rest.EmployeeService;
import ru.medeng.models.user.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {
    private List<Employee> list;
    private EmployeeClickListener listener;

    public EmployeeAdapter(List<Employee> list, EmployeeClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new EmployeeViewHolder(inflater.inflate(R.layout.view_employee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee e = list.get(position);
        holder.setEmployee(e);
        holder.setLogin(e.getAuth().getLogin());
        holder.setRole(e.getRole());
        holder.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
