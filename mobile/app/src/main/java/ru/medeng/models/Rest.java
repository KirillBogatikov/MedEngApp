package ru.medeng.models;

public class Rest {
	private Product product;
	private int booked;
	private int available;
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getBooked() {
		return booked;
	}
	
	public void setBooked(int booked) {
		this.booked = booked;
	}
	
	public int getAvailable() {
		return available;
	}
	
	public void setAvailable(int available) {
		this.available = available;
	}
}
