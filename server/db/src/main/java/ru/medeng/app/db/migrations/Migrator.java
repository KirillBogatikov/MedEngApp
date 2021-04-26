package ru.medeng.app.db.migrations;

import static ru.medeng.tools.Resources.sql;

import java.io.IOException;
import java.sql.SQLException;

import ru.medeng.app.db.SqlRepository;

public class Migrator {
	private SqlRepository repository;
		
	public Migrator(String jdbc) throws SQLException, IOException {
		repository = new SqlRepository(jdbc);
	}

	public boolean init() throws SQLException, IOException {
		if (repository.query(r -> 1, sql("init", "check")) == null) {
			System.out.println("Database is raw. Starting migration...");
			repository.execute(sql("init", "create"));
			
			return true;
		}
		
		return false;
	}
	
	private void importFile(String name) throws SQLException, IOException {
		repository.execute(sql("init", name));
	}
	
	public void importProducts() throws SQLException, IOException {
		importFile("product");
	}
	
	public void importCustomers() throws SQLException, IOException {
		importFile("customer");
	}
	
	public void importOrders() throws SQLException, IOException {
		importFile("orders");
	}
	
	public void importEmployee() throws SQLException, IOException {
		importFile("employee");
	}
}
