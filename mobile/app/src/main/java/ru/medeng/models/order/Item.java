package ru.medeng.models.order;

import java.util.UUID;

public class Item {
	private UUID id;
	private Operation booking;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public Operation getBooking() {
		return booking;
	}

	public void setBooking(Operation booking) {
		this.booking = booking;
	}
}
