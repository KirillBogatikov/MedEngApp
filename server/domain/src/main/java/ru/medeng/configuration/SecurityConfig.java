package ru.medeng.configuration;

import java.security.Key;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class SecurityConfig {
	private Key secret;
	private String salt;
	private int saltIndex;
	private long tokenLifeTime;
	
	public Key getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public int getSaltIndex() {
		return saltIndex;
	}
	
	public void setSaltIndex(int saltIndex) {
		this.saltIndex = saltIndex;
	}

	public long getTokenLifeTime() {
		return tokenLifeTime;
	}

	public void setTokenLifeTime(long tokenLifeTime) {
		this.tokenLifeTime = tokenLifeTime;
	}
}
