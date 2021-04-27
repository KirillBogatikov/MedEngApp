package ru.medeng.domain;

import java.util.List;
import java.util.UUID;

import ru.medeng.app.db.OperationRepository;
import ru.medeng.models.Rest;
import ru.medeng.models.order.Operation;
import ru.medeng.models.order.Operation.Type;
import ru.medeng.tools.Result;

public class ShipmentService {
	private OperationRepository repo;
	
	public ShipmentService(OperationRepository repo) {
		this.repo = repo;
	}

	public Result<UUID> add(Operation operation) {
		try {
			operation.setId(UUID.randomUUID());
			operation.setType(Type.Shipment);
			var found = repo.insert(operation);
			return found ? Result.ok(operation.getId()) : Result.notFound();
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<List<Rest>> getRest() {
		try {
			return Result.ok(repo.getRest());
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

}
