package ru.medeng.models.user;

public enum AccessLevel {
	Customer(0),
	Operator(1),
	Storekeeper(2),
	Guest(3);

	private final int i;

	AccessLevel(int i) {
		this.i = i;
	}

	public int getCode() {
		return i;
	}
}
