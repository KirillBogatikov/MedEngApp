package ru.medeng.models.user;

import java.util.UUID;

public class Auth {
	private UUID id;
	private CharSequence login;
	private CharSequence password;
	
	public Auth() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public CharSequence getLogin() {
		return login;
	}

	public void setLogin(CharSequence login) {
		this.login = login;
	}

	public CharSequence getPassword() {
		return password;
	}

	public void setPassword(CharSequence password) {
		this.password = password;
	}
}
