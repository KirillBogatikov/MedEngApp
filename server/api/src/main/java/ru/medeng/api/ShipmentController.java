package ru.medeng.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.medeng.domain.ShipmentService;
import ru.medeng.models.order.Operation;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.tools.AuthorizedController;

@RestController
@RequestMapping("/api/shipment")
public class ShipmentController extends AuthorizedController {
	@Autowired
	private ShipmentService service;
	
	@GetMapping("/rest")
	public ResponseEntity<?> getRest(@RequestHeader("Authorization") String tokenHeader) {
		var status = auth(tokenHeader, AccessLevel.Operator);
		if (status != null) {
			return status;
		}
		
		var result = service.getRest();
		if (result.hasError()) {
			return error();
		}
		
		return ok(result.getData());
	}
	
	@PostMapping
	public ResponseEntity<?> add(@RequestHeader("Authorization") String tokenHeader, @RequestBody Operation operation) {
		var status = auth(tokenHeader, AccessLevel.Storekeeper);
		if (status != null) {
			return status;
		}
		
		var result = service.add(operation);
		if (result.isEntityFound()) {
			return ok(result.getData());
		}
		
		if (result.hasError()) {
			return error();
		}
		
		return notFound();
	}

}
