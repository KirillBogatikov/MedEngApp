package ru.medeng.models.order;

import java.util.List;
import java.util.UUID;

import ru.medeng.models.user.Customer;

public class Order {
	private UUID id;
	private Customer customer;
	private List<Item> items;
	private List<StatusInfo> history;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
}
