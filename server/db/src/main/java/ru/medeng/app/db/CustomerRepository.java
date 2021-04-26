package ru.medeng.app.db;

import static ru.medeng.tools.Resources.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import ru.medeng.models.user.Customer;

public class CustomerRepository extends SqlRepository {
	private static final String SQL_DIR = "customer";
	public static Mapper<Customer> customer = r -> {
		var c = new Customer();
		
		c.setAuth(AuthRepository.auth.apply(r));
		c.setId(r.getObject("customer_id", UUID.class));
		c.setFirstName(r.getString("customer_first_name"));
		c.setLastName(r.getString("customer_last_name"));
		c.setPatronymic(r.getString("customer_patronymic"));
		c.setPhone(r.getString("customer_phone"));
		c.setEmail(r.getString("customer_email"));
		
		return c;
	};

	public CustomerRepository(String url) {
		super(url);
	}
	
	public boolean has(UUID id) throws SQLException, IOException {
		return query(r -> 1, sql(SQL_DIR, "has"), id) != null;
	}

	public Customer get(UUID uuid) throws SQLException, IOException {
		return query(customer, sql(SQL_DIR, "get"), uuid);
	}

	public void insert(Customer customer) throws SQLException, IOException {
		var auth = customer.getAuth();
		execute(sql(SQL_DIR, "insert"), auth.getId(), auth.getLogin(), auth.getPassword(), 
				customer.getId(), auth.getId(), customer.getFirstName(), customer.getLastName(), customer.getPatronymic(), customer.getPhone(), customer.getEmail());
	}

	public boolean update(Customer customer) throws SQLException, IOException {
		if (!has(customer.getId())) {
			return false;
		}
		
		var auth = customer.getAuth();
		execute(sql(SQL_DIR, "update"), auth.getLogin(), auth.getPassword(), auth.getId(), 
				customer.getFirstName(), customer.getLastName(), customer.getPatronymic(), customer.getPhone(), customer.getEmail(), customer.getId());
		
		return true;
	}

	public List<Customer> search(String query) throws SQLException, IOException {
		return queryList(customer, sql(SQL_DIR, "search"), query, query, query, query, query, query);
	}

	public boolean delete(UUID id) throws SQLException, IOException {
		if (!has(id)) {
			return false;
		}
		
		execute(sql(SQL_DIR, "delete"), id);
		return true;
	}

}
