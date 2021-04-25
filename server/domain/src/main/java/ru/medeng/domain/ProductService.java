package ru.medeng.domain;

import java.util.List;
import java.util.UUID;

import ru.medeng.app.db.ProductRepository;
import ru.medeng.models.order.Product;
import ru.medeng.tools.Result;

public class ProductService {
	private ProductRepository repo;
	
	public ProductService(ProductRepository repo) {
		this.repo = repo;
	}

	public Result<List<Product>> search(String query) {
		try {
			if (query == null || query.isEmpty()) {
				query = ".*";
			} else {
				query = ".*%s.*".formatted(query);
			}
			
			var list = repo.search(query);
			return Result.ok(list);
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<UUID> save(Product product) {
		try {
			if (product.getId() == null) {
				product.setId(UUID.randomUUID());
				repo.insert(product);
			} else {
				if (!repo.update(product)) {
					return Result.notFound();
				}
			}
			
			return Result.ok(product.getId());
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
