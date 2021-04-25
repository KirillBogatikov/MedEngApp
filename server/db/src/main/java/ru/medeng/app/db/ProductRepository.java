package ru.medeng.app.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import ru.medeng.models.order.Product;
import static ru.medeng.tools.Resources.sql;

public class ProductRepository extends SqlRepository {
	private static final String SQL_DIR = "product";
	private static final Mapper<Product> product = r -> {
		var p = new Product();
		
		p.setId(r.getObject("product_id", UUID.class));
		p.setName(r.getString("product_name"));
		p.setDescription(r.getString("product_description"));
		
		return p;
	};
	
	public ProductRepository(String url) {
		super(url);
	}
	
	public boolean has(UUID id) throws SQLException, IOException {
		return query(r -> 1, sql(SQL_DIR, "has"), id) != null;
	}

	public List<Product> search(String query) throws SQLException, IOException {
		return queryList(product, sql(SQL_DIR, "search"), query, query);
	}
	
	public void insert(Product p) throws SQLException, IOException {
		execute(sql(SQL_DIR, "insert"), p.getId(), p.getName(), p.getDescription());
	}
	
	public boolean update(Product p) throws SQLException, IOException {
		if (has(p.getId())) {
			execute(sql(SQL_DIR, "update"), p.getName(), p.getDescription(), p.getId());
			return true;
		}
		
		return false;
	}

	public boolean delete(UUID id) throws SQLException, IOException {
		if (has(id)) {
			execute(sql(SQL_DIR, "delete"), id);
			return true;
		}
		
		return false;
	}

}
