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

import ru.medeng.domain.EmployeeService;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Employee;
import ru.medeng.tools.AuthorizedController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController extends AuthorizedController {
	@Autowired
	private EmployeeService service;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestHeader("Authorization") String tokenHeader, @RequestParam(required=false) String query) {
		var status = auth(tokenHeader, AccessLevel.Operator);
		if (status != null) {
			return status;
		}
		
		var result = service.list(query);
		if (result.hasError()) {
			return error();
		}
		
		return ok(result.getData());
	}

	@PutMapping
	public ResponseEntity<?> save(@RequestHeader("Authorization") String tokenHeader, @RequestBody Employee employee) {
		var status = auth(tokenHeader, AccessLevel.Operator);
		if (status != null) {
			return status;
		}
		
		var result = service.save(employee);
		if (result.isEntityFound()) {
			return ok(result.getData());
		}
		
		if (result.hasError()) {
			return error();
		}
		
		return notFound();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@RequestHeader("Authorization") String tokenHeader, @PathVariable String id) {
		var status = auth(tokenHeader, AccessLevel.Operator);
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
