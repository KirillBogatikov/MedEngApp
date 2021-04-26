package ru.medeng.models.client;

import java.util.UUID;

public class ClientItem {
	private UUID product;
	private int count;
	
	public UUID getProduct() {
		return product;
	}
	
	public void setProduct(UUID product) {
		this.product = product;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
