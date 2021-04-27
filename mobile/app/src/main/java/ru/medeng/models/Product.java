package ru.medeng.models;

import java.util.Objects;
import java.util.UUID;

public class Product {
	private UUID id;
	private CharSequence name;
	private CharSequence description;
	
	public UUID getId() {		
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public CharSequence getName() {
		return name;
	}
	
	public void setName(CharSequence name) {
		this.name = name;
	}

	public CharSequence getDescription() {
		return description;
	}

	public void setDescription(CharSequence description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
