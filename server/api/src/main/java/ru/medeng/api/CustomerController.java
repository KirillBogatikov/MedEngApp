package ru.medeng.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.medeng.domain.CustomerService;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Customer;
import ru.medeng.tools.AuthorizedController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends AuthorizedController {
	@Autowired
	private CustomerService service;
	
	@PostMapping
	public ResponseEntity<?> signup(@RequestBody Customer customer) {
		var result = service.create(customer);
		if (result.hasError()) {
			return error();
		}
		
		return created(result.getData());
	}
	
	@PutMapping
	public ResponseEntity<?> save(@RequestHeader("Authorization") String tokenHeader, @RequestBody Customer customer) {
		var status = auth(tokenHeader, customer.getAuth());
		if (status != null) {
			return status;
		}
		
		var result = service.update(customer);
		if (result.hasError()) {
			return error();
		}
		
		return ok(result.getData());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> get(@RequestHeader("Authorization") String tokenHeader, @PathVariable String id) {
		var uuid = UUID.fromString(id);
		var status = auth(tokenHeader, uuid);
		if (status != null) {
			return status;
		}
		
		var result = service.get(uuid);
		if (result.hasError()) {
			return error();
		}
		
		return ok(result.getData());
	}

	@GetMapping
	public ResponseEntity<?> search(@RequestHeader("Authorization") String tokenHeader, @RequestParam(required=false) String query) {
		var status = auth(tokenHeader, AccessLevel.Operator);
		if (status != null) {
			return status;
		}
		
		var result = service.search(query);
		if (result.hasError()) {
			return error();
		}
		
		return ok(result.getData());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@RequestHeader("Authorization") String tokenHeader, @PathVariable String id) {
		var uuid = UUID.fromString(id);
		var status = auth(tokenHeader, uuid);
		if (status != null) {
			return status;
		}
		
		var result = service.delete(id);
		if (!result.isEntityFound()) {
			return notFound();
		}
		
		if (result.hasError()) {
			return error();
		}
		
		return ok();
	}
}
