package ru.medeng.models.user;

import java.util.UUID;

public class Employee {	
	private UUID id;
	private Auth auth;
	private AccessLevel role;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public Auth getAuth() {
		return auth;
	}
	
	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	
	public AccessLevel getRole() {
		return role;
	}
	
	public void setRole(AccessLevel role) {
		this.role = role;
	}
}
