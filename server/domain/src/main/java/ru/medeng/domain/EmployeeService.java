package ru.medeng.domain;

import java.util.List;
import java.util.UUID;

import ru.medeng.app.db.EmployeeRepository;
import ru.medeng.models.user.Employee;
import ru.medeng.tools.Result;

public class EmployeeService {
	private EmployeeRepository repo;

	public Result<List<Employee>> list(String query) {
		try {
			if (query == null) {
				query = ".*";
			}
			
			return Result.ok(repo.list(query));
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<UUID> save(Employee employee) {
		try {
			if (employee.getId() == null) {
				employee.setId(UUID.randomUUID());
				employee.getAuth().setId(UUID.randomUUID());
				repo.insert(employee);
			} else {
				var found = repo.update(employee);
				if (!found) {
					return Result.notFound();
				}
			}
			return Result.ok(employee.getId());
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<Void> delete(String id) {
		try {
			var found = repo.delete(UUID.fromString(id));
			return found ? Result.ok(null) : Result.notFound();
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
}
