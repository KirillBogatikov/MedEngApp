package ru.medeng.models.user;

import java.util.UUID;

public class Employee {
	public static enum Role {
		Operator, Storekeeper;
	}
	
	private UUID id;
	private Auth auth;
	private Role role;
	
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
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
}
