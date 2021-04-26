package ru.medeng.domain;

import java.util.List;
import java.util.UUID;

import ru.medeng.app.db.EmployeeRepository;
import ru.medeng.models.user.Employee;
import ru.medeng.tools.Hash;
import ru.medeng.tools.Result;

public class EmployeeService {
	private EmployeeRepository repo;

	public EmployeeService(EmployeeRepository repo) {
		super();
		this.repo = repo;
	}

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
			var auth = employee.getAuth();
			
			if (employee.getId() == null) {
				var password = auth.getPassword(); 
				auth.setPassword(Hash.hexHash(password));
				
				employee.setId(UUID.randomUUID());
				employee.getAuth().setId(UUID.randomUUID());
				repo.insert(employee);
			} else {
				var password = auth.getPassword();
				if (password == null) {
					auth.setPassword(repo.getPassword(employee.getId()));
				} else {
					auth.setPassword(Hash.hexHash(password));
				}
				
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
