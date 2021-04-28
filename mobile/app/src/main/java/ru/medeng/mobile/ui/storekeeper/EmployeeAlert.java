package ru.medeng.mobile.ui.storekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import ru.medeng.mobile.R;
import ru.medeng.mobile.api.Api;
import ru.medeng.mobile.api.impl.EmployeeServiceImpl;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Auth;
import ru.medeng.models.user.Employee;

public class EmployeeAlert {
    private AlertDialog dialog;
    private EmployeeServiceImpl employeeService;
    private TextView login, password;
    private Spinner spinner;

    public EmployeeAlert(Context ctx, Runnable onSave) {
        employeeService = Api.getInstance().getEmployees();

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Добавить сотрудника");

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.dialog_create_employee, null);
        spinner = view.findViewById(R.id.role);
        login = view.findViewById(R.id.login);
        password = view.findViewById(R.id.password);

        builder.setPositiveButton(R.string.save_label, (d, w) -> {
            Employee e = new Employee();
            Auth a = new Auth();
            e.setAuth(a);

            int index = spinner.getSelectedItemPosition();
            a.setLogin(login.getText());
            a.setPassword(password.getText());
            e.setRole(AccessLevel.forCode(index + 1));

            if (employeeService.save(e)) {
                Toast.makeText(ctx, "Сотрудник зарегистрирован", Toast.LENGTH_SHORT).show();
                onSave.run();
            }

            dialog.dismiss();
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
