package ru.medeng.app.db;

import static ru.medeng.tools.Resources.sql;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import ru.medeng.models.order.Item;
import ru.medeng.models.order.Order;
import ru.medeng.models.order.Status;
import ru.medeng.models.order.StatusInfo;

public class OrderRepository extends SqlRepository {
	private static final Mapper<Item> item = r -> {
		var i = new Item();
		
		i.setId(r.getObject("item_id", UUID.class));
		i.setBooking(OperationRepository.operation.apply(r));
		
		return i;
	};
	private static final Mapper<Order> order = r -> {
		var o = new Order();
		
		o.setId(r.getObject("order_id", UUID.class));
		
		return o;
	};
	private static final Mapper<StatusInfo> statusInfo = r -> {
		var s = new StatusInfo();
		
		s.setId(r.getObject("status_id", UUID.class));
		s.setOrderId(r.getObject("status_order_id", UUID.class));
		s.setStatus(Status.valueOf(r.getString("status_status")));
		s.setDate(r.getDate("status_date"));
		
		return s;
	};
	
	public OrderRepository(String url) {
		super(url);
	}
	
	public boolean hasCustomer(UUID id) throws SQLException, IOException {
		return query(r -> 1, sql("order", "has_customer"), id) != null;
	}
	
	public List<Order> list(UUID userId, Status status) throws SQLException, IOException {
		var data = queryList(order, sql("order", "list"), userId);
		
		if (status != null) {
			for (int i = 0; i < data.size(); ) {
				var last = query(r -> Status.valueOf(r.getString("status")), sql("order/status", "last"), data.get(i).getId());
				if (last == status) {
					i++;
				} else {
					data.remove(i);
				}
			}
		}
		
		for (var o : data) {
			o.setHistory(queryList(statusInfo, sql("order/status", "list_by_order"), o.getId()));
			o.setItems(queryList(item, sql("order/item", "list_by_order"), o.getId()));
		};
		
		return data;
	}

	public boolean insert(UUID customerId, UUID orderId) throws SQLException, IOException {
		if (hasCustomer(customerId)) {
			execute(sql("order", "insert"), orderId, customerId);
			return true;
		}
		
		return false;
	}
	
	public void insertItem(UUID orderId, Item item) throws SQLException, IOException {
		execute(sql("order/item", "insert"), item.getId(), orderId,  item.getBooking().getId());
	}

	public void insertStatus(StatusInfo status) throws SQLException, IOException {
		status.setDate(new Date(System.currentTimeMillis()));
		execute(sql("order/status", "insert"), status.getId(), status.getOrderId(), status.getStatus().toString(), status.getDate());
	}
}
