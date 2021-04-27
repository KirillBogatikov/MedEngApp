package ru.medeng.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.medeng.app.db.OperationRepository;
import ru.medeng.app.db.OrderRepository;
import ru.medeng.models.order.Item;
import ru.medeng.models.order.Operation.Type;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;
import ru.medeng.models.order.StatusInfo;
import ru.medeng.tools.Result;

public class OrderService {
	private OrderRepository orders;
	private OperationRepository operations;
	
	public OrderService(OrderRepository orders, OperationRepository operations) {
		this.orders = orders;
		this.operations = operations;
	}

	public Result<List<Order>> list(UUID userId, String orderStatus) {
		try {
			var status = orderStatus == null ? null : Status.valueOf(orderStatus);
			List<Order> data;
			
			if (userId == null) {
				data = orders.all(status);
			} else {
				data = orders.list(userId, status);
			}
			
			return Result.ok(data);
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<Order> add(UUID customerId, List<Item> orderItems) {
		try {
			var order = new Order();
			order.setId(UUID.randomUUID());
			
			var found = orders.insert(customerId, order.getId());
			if (!found) {
				return Result.notFound();
			}
			
			for (Item item : orderItems) {
				var o = item.getBooking();
				
				o.setId(UUID.randomUUID());
				o.setType(Type.Booking);
				
				operations.insert(o);
				
				item.setId(UUID.randomUUID());
				orders.insertItem(order.getId(), item);
			}
			
			var history = new ArrayList<StatusInfo>();
			var status = new StatusInfo();
			status.setId(UUID.randomUUID());
			status.setOrderId(order.getId());
			status.setStatus(Status.Created);
			history.add(status);
			order.setHistory(history);
			
			orders.insertStatus(status);
			
			return Result.ok(order);
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<Void> updateStatus(String id, Status orderStatus) {
		try {
			var status = new StatusInfo();
			status.setId(UUID.randomUUID());
			status.setOrderId(UUID.fromString(id));
			status.setStatus(orderStatus);
			orders.insertStatus(status);
			return Result.ok(null);
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

}
