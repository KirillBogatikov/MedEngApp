package ru.medeng.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.medeng.app.db.OperationRepository;
import ru.medeng.app.db.OrderRepository;
import ru.medeng.app.db.ProductRepository;
import ru.medeng.models.client.ClientItem;
import ru.medeng.models.order.Item;
import ru.medeng.models.order.Operation;
import ru.medeng.models.order.Operation.Type;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;
import ru.medeng.models.order.StatusInfo;
import ru.medeng.tools.Result;

public class OrderService {
	private OrderRepository orders;
	private OperationRepository operations;
	private ProductRepository products;
	
	public OrderService(OrderRepository orders, OperationRepository operations, ProductRepository products) {
		this.orders = orders;
		this.operations = operations;
		this.products = products;
	}

	public Result<List<Order>> list(UUID userId, String orderStatus) {
		try {
			return Result.ok(orders.list(userId, orderStatus == null ? null : Status.valueOf(orderStatus)));
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<Order> add(UUID customerId, List<ClientItem> clientItems) {
		try {
			var order = new Order();
			order.setId(UUID.randomUUID());
			
			var found = orders.insert(customerId, order.getId());
			if (!found) {
				return Result.notFound();
			}
			
			var items = new ArrayList<Item>();
			for (ClientItem clientItem : clientItems) {
				var o = new Operation();
				
				o.setId(UUID.randomUUID());
				o.setProduct(products.get(clientItem.getProduct()));
				o.setCount(clientItem.getCount());
				o.setType(Type.Booking);
				
				operations.insert(o);

				var item = new Item();
				item.setId(UUID.randomUUID());
				item.setBooking(o);
				items.add(item);
				
				orders.insertItem(order.getId(), item);				
			}
			order.setItems(items);
			
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
