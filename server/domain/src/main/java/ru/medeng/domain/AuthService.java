package ru.medeng.domain;

import java.util.UUID;

import ru.medeng.app.db.AuthRepository;
import ru.medeng.configuration.SecurityConfig;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.tools.Hash;
import ru.medeng.tools.Result;

public class AuthService {	
	private SecurityConfig security;
	private AuthRepository repo;
	
	public AuthService(SecurityConfig security, AuthRepository repo) {
		this.security = security;
		this.repo = repo;
	}

	public static class AuthData {
		private Token token;
		private AccessLevel accessLevel;
		
		private AuthData(Token token, AccessLevel accessLevel) {
			this.token = token;
			this.accessLevel = accessLevel;
		}

		public Token getToken() {
			return token;
		}

		public void setToken(Token token) {
			this.token = token;
		}

		public AccessLevel getAccessLevel() {
			return accessLevel;
		}

		public void setAccessLevel(AccessLevel accessLevel) {
			this.accessLevel = accessLevel;
		}		
	}
	
	public Result<String> login(String login, String password) {
		try {
			var auth = repo.login(login, Hash.hexHash(password));
			if (auth == null) {
				return Result.notFound();
			}
			
			var accessLevel = repo.getAccessLevel(auth.getId());
			UUID userId;
			if (accessLevel == AccessLevel.Operator || accessLevel == AccessLevel.Storekeeper) {
				userId = repo.getEmployeeId(auth.getId());
			} else {
				userId = repo.getCustomerId(auth.getId());
			}
			
			var time = System.currentTimeMillis();
			var token = new Token(UUID.randomUUID(), auth.getId(), userId, time, time + security.getTokenLifeTime());
			return Result.ok(token.encode(security.getSecret()));
		} catch(Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	public Result<AuthData> parseToken(String header) {
		try {
			var token = Token.decode(security.getSecret(), header);
			var accessLevel = repo.getAccessLevel(token.getAuthId());
			
			return Result.ok(new AuthData(token, accessLevel));
		} catch(Exception e) {
			e.printStackTrace();
			return Result.notFound();
		}
	}
}
