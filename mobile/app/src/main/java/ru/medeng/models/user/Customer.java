package ru.medeng.models.user;

import java.util.UUID;

public class Customer {
	private UUID id;
	private CharSequence firstName;
	private CharSequence lastName;
	private CharSequence patronymic;
	private Auth auth;
	private CharSequence phone;
	private CharSequence email;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public CharSequence getFirstName() {
		return firstName;
	}

	public void setFirstName(CharSequence firstName) {
		this.firstName = firstName;
	}

	public CharSequence getLastName() {
		return lastName;
	}

	public void setLastName(CharSequence lastName) {
		this.lastName = lastName;
	}

	public CharSequence getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(CharSequence patronymic) {
		this.patronymic = patronymic;
	}
	
	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public CharSequence getPhone() {
		return phone;
	}

	public void setPhone(CharSequence phone) {
		this.phone = phone;
	}

	public CharSequence getEmail() {
		return email;
	}

	public void setEmail(CharSequence email) {
		this.email = email;
	}
}
