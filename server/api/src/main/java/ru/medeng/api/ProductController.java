package ru.medeng.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.medeng.domain.ProductService;
import ru.medeng.models.order.Product;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.tools.AuthorizedController;

@RestController
@RequestMapping("/api/product")
public class ProductController extends AuthorizedController {
	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<?> search(@RequestParam(required=false) String query) {
		var list = service.search(query);
		if (list.hasError()) {
			return error();
		}
		
		return ok(list.getData());
	}
	
	@PutMapping
	public ResponseEntity<?> save(@RequestHeader String tokenHeader, @RequestBody Product product) {
		var status = auth(tokenHeader, AccessLevel.Storekeeper);
		if (status != null) {
			return status;
		}
		
		var result = service.save(product);
		if (result.isEntityFound()) {
			return ok(result.getData());
		}
		
		if (result.getData() != null) {
			return created(result.getData());
		} 
		
		if (result.hasError()) {
			return error();
		}
		
		return notFound();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@RequestHeader String tokenHeader, @PathVariable String id) {
		var status = auth(tokenHeader, AccessLevel.Storekeeper);
		if (status != null) {
			return status;
		}
		
		var result = service.delete(id);
		if (result.isEntityFound()) {
			return ok();
		}
		
		if (result.hasError()) {
			return error();
		}
		
		return notFound();
	}
}
