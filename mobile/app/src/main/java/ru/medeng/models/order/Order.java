package ru.medeng.models.order;

import java.util.List;
import java.util.UUID;

public class Order {
	private UUID id;
	private List<Item> items;
	private List<StatusInfo> history;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<StatusInfo> getHistory() {
		return history;
	}

	public void setHistory(List<StatusInfo> history) {
		this.history = history;
	}

	public Status getStatus() {
		return history.get(history.size() - 1).getStatus();
	}
}
