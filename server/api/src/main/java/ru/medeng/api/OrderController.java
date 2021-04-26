package ru.medeng.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.medeng.domain.OrderService;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.tools.AuthorizedController;

@RestController
@RequestMapping("/api/order")
public class OrderController extends AuthorizedController {
	@Autowired
	private OrderService service;

	@GetMapping
	public ResponseEntity<?> list(@RequestHeader("Authorization") String tokenHeader,
			@RequestParam(required = false) String orderStatus) {
		var status = auth(tokenHeader, AccessLevel.Customer, AccessLevel.Storekeeper);
		if (status != null) {
			return status;
		}

		var id = token.getUserId();
		if (accessLevel == AccessLevel.Storekeeper) {
			id = null;
		}
		
		var result = service.list(id, orderStatus);
		if (result.hasError()) {
			return error();
		}

		return ok(result.getData());
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<?> listCustomer(@RequestHeader("Authorization") String tokenHeader,
			@RequestParam(required = false) String orderStatus, @PathVariable String customerId) {
		var status = auth(tokenHeader, AccessLevel.Operator);
		if (status != null) {
			return status;
		}

		var result = service.list(UUID.fromString(customerId), orderStatus);
		if (result.hasError()) {
			return error();
		}

		return ok(result.getData());
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestHeader("Authorization") String tokenHeader, @RequestBody Order order) {
		var status = auth(tokenHeader, AccessLevel.Customer);
		if (status != null) {
			return status;
		}

		var result = service.add(token.getUserId(), order.getItems());
		if (result.isEntityFound()) {
			return created(result.getData());
		}

		if (result.hasError()) {
			return error();
		}

		return notFound();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> updateStatus(@RequestHeader("Authorization") String tokenHeader, @RequestParam String statusText, @PathVariable String id) {
		var orderStatus = Status.valueOf(statusText);
		var level = switch(orderStatus) {
			case Processing -> AccessLevel.Operator;
			case Booked -> AccessLevel.Operator;
			case Canceled -> AccessLevel.Operator;
			case Received -> AccessLevel.Operator;
			case Ready -> AccessLevel.Storekeeper;
			default -> null;
		};
		
		var status = auth(tokenHeader, level);
		if (status != null) {
			return status;
		}
		
		var result = service.updateStatus(id, orderStatus);
		if (result.isEntityFound()) {
			return ok(result.getData());
		}
		
		if (result.hasError()) {
			return error();
		}
		
		return notFound();
	}
}
