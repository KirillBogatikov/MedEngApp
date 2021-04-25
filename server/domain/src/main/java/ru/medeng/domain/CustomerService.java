package ru.medeng.domain;

import java.util.List;
import java.util.UUID;

import ru.medeng.app.db.CustomerRepository;
import ru.medeng.models.user.Customer;
import ru.medeng.tools.Hash;
import ru.medeng.tools.Result;

public class CustomerService {
	private CustomerRepository repo;
	
	public CustomerService(CustomerRepository repo) {
		this.repo = repo;
	}

	public Result<UUID> create(Customer customer) {
		try {
			var id = UUID.randomUUID();
			customer.setId(id);
			
			var auth = customer.getAuth();
			auth.setId(UUID.randomUUID());
			auth.setPassword(Hash.hexHash(auth.getPassword()));
			
			repo.insert(customer);
			return Result.ok(id);
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<Customer> get(UUID uuid) {
		try {
			var customer = repo.get(uuid);
			if (customer == null) {
				return Result.notFound();
			}
			
			return Result.ok(customer);
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<UUID> update(Customer customer) {
		try {
			var auth = customer.getAuth();
			auth.setPassword(Hash.hexHash(auth.getPassword()));
			var found = repo.update(customer);
			return found ? Result.ok(customer.getId()) : Result.notFound();
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<List<Customer>> search(String query) {
		try {
			return Result.ok(repo.search(query));
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
