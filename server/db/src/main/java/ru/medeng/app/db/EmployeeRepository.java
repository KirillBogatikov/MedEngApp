package ru.medeng.app.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Employee;

import static ru.medeng.tools.Resources.sql;

public class EmployeeRepository extends SqlRepository {
	public static final Mapper<Employee> employee = r -> {
		var e = new Employee();
		
		e.setId(r.getObject("employee_id", UUID.class));
		e.setAuth(AuthRepository.auth.apply(r));
		e.setRole(AccessLevel.valueOf(r.getString("employee_role")));
		
		return e;
	};
	
	public EmployeeRepository(String url) {
		super(url);
	}
	
	public String getPassword(UUID id) throws SQLException, IOException {
		return query(r -> r.getString("password"), sql("employee", "get_password"), id);
	}

	public boolean has(UUID id) throws SQLException, IOException {
		return query(r -> 1, sql("employee", "has"), id) != null;
	}
	
	public List<Employee> list(String query) throws SQLException, IOException {
		return queryList(employee, sql("employee", "search"), query);
	}

	public void insert(Employee e) throws SQLException, IOException {
		var a = e.getAuth();
		execute(sql("employee", "insert"), a.getId(), a.getLogin(), a.getPassword(), e.getId(), a.getId(), e.getRole().toString());
	}
	
	public boolean update(Employee e) throws SQLException, IOException {
		if (!has(e.getId())) {
			return false;
		}
		
		var a = e.getAuth();
		execute(sql("employee", "update"), a.getLogin(), a.getPassword(), a.getId(), e.getRole().toString(), e.getId());
		return true;
	}

	public boolean delete(UUID id) throws SQLException, IOException {
		if (!has(id)) {
			return false;
		}
		
		execute(sql("employee", "delete"), id);
		return true;
	}

}
