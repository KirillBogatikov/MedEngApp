package ru.medeng.tools;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Objects;

import ru.medeng.domain.AuthService;
import ru.medeng.domain.Token;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.models.user.Auth;

public class AuthorizedController extends MedEngController {
	@Autowired
	private AuthService authService;
	protected Token token;
	protected AccessLevel accessLevel;
	
	public ResponseEntity<?> auth(String header, AccessLevel... allowed) {
		var result = authService.parseToken(header);
		if (result.hasError()) {
			return error();
		}
	
		if (!result.isEntityFound()) {
			return unauthorized();
		}
		
		var authData = result.getData();		
		this.token = authData.getToken();
		this.accessLevel = authData.getAccessLevel();
		
		if (allowed.length > 0) {
			for (var l : allowed) {
				if (authData.getAccessLevel() == l) {
					return null;
				}
			}

			return forbidden();
		}
		
		return null;
	}
	
	public ResponseEntity<?> auth(String header, UUID userId) {
		var status = auth(header);
		if (status == null && !Objects.equal(token.getUserId(), userId)) {
			this.token = null;
			this.accessLevel = null;
			return forbidden();
		}
		
		return status;
	}
	
	public ResponseEntity<?> auth(String header, Auth auth) {
		if (auth == null) {
			return badRequest();
		}
		
		var status = auth(header);
		if (status == null && !Objects.equal(token.getAuthId(), auth.getId())) {
			this.token = null;
			this.accessLevel = null;
			return forbidden();
		}
		
		return status;
	}
}
