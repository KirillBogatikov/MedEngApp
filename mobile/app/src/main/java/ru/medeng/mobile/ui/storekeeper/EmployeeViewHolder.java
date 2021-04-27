package ru.medeng.mobile.ui.storekeeper;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.medeng.mobile.R;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Employee;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {
    private String[] rolesText;
    private TextView login, role;
    private View delete;
    private Employee employee;

    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);

        rolesText = itemView.getContext().getResources().getStringArray(R.array.access_level);

        login = itemView.findViewById(R.id.login);
        role = itemView.findViewById(R.id.role);
        delete = itemView.findViewById(R.id.delete);
    }

    public void setLogin(CharSequence login) {
        this.login.setText(login);
    }

    public void setRole(AccessLevel level) {
        this.role.setText(rolesText[level.getCode()]);
    }

    public void setEmployee(Employee e) {
        this.employee = e;
    }

    public void setListener(EmployeeClickListener listener) {
        delete.setOnClickListener(v -> listener.deleteEmployee(employee));
    }
}
