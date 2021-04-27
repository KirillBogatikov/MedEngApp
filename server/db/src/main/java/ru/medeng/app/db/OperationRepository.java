package ru.medeng.app.db;

import static ru.medeng.tools.Resources.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.medeng.models.Rest;
import ru.medeng.models.order.Operation;
import ru.medeng.models.order.Operation.Type;

public class OperationRepository extends SqlRepository {
	public static final Mapper<Operation> operation = r -> {
		var o = new Operation();
		
		o.setId(r.getObject("operation_id", UUID.class));
		o.setType(Type.valueOf(r.getString("operation_type")));
		o.setCount(r.getInt("operation_count"));
		o.setDate(r.getTimestamp("operation_date"));
		o.setProduct(ProductRepository.product.apply(r));
		
		return o;
	};
	
	public OperationRepository(String url) {
		super(url);
	}
	
	public boolean hasProduct(UUID id) throws SQLException, IOException {
		return query(r -> 1, sql("operation", "has_product"), id) != null;
	}

	public boolean insert(Operation o) throws SQLException, IOException {
		o.setDate(new Timestamp(System.currentTimeMillis()));
		var p = o.getProduct();
		if (hasProduct(p.getId())) {
			execute(sql("operation", "insert").formatted(o.getType()), o.getId(), p.getId(), o.getCount(), o.getDate());
			return true;
		}
		
		return false;
	}

	public List<Rest> getRest() throws SQLException, IOException {
		var products = queryList(ProductRepository.product, sql("operation", "list_product"));
		var result = new ArrayList<Rest>();
		
		for (var p : products) {
			var rest = new Rest();
			
			rest.setProduct(p);
			
			var total = query(r -> r.getInt("count"), sql("operation", "count").formatted(Type.Shipment), p.getId());
			var booked = query(r -> r.getInt("count"), sql("operation", "count").formatted(Type.Booking), p.getId());
			
			rest.setAvailable(Math.max(total - booked, 0));
			rest.setBooked(booked);
			
			result.add(rest);
		}
		
		return result;
	}

}
