package ru.medeng.domain;

import java.security.Key;
import java.util.HashMap;
import java.util.UUID;

import io.jsonwebtoken.Jwts;

public class Token {
	private UUID id;
	private UUID authId;
	private UUID userId;
	private long iat;
	private long exp;

	public Token(UUID id, UUID authId, UUID userId, long iat, long exp) {
		this.id = id;
		this.authId = authId;
		this.userId = userId;
		this.iat = iat;
		this.exp = exp;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getAuthId() {
		return authId;
	}

	public void setAuthId(UUID authId) {
		this.authId = authId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public long getIat() {
		return iat;
	}

	public void setIat(long iat) {
		this.iat = iat;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public String encode(Key secret) {
		var claims = new HashMap<String, Object>();
		claims.put("jti", id);
		claims.put("authId", authId);
		claims.put("userId", userId);
		claims.put("iat", iat);
		claims.put("exp", exp);
		return Jwts.builder().addClaims(claims).signWith(secret).compact();
	}

	public static Token decode(Key secret, String signed) {
		var jws = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(signed);

		var claims = jws.getBody();
		var token = new Token(UUID.fromString(claims.getId()), 
				UUID.fromString(claims.get("authId", String.class)),
				UUID.fromString(claims.get("userId", String.class)), 
				claims.getIssuedAt().getTime(),
				claims.getExpiration().getTime());
		return token;
	}
}
