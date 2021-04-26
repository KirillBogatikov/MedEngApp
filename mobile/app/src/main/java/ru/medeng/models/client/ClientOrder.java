package ru.medeng.models.client;

import java.util.List;

public class ClientOrder {
	private List<ClientItem> items;

	public List<ClientItem> getItems() {
		return items;
	}

	public void setItems(List<ClientItem> items) {
		this.items = items;
	}
}
