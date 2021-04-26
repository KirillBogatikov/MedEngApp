package ru.medeng.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.medeng.domain.AuthService;
import ru.medeng.models.user.AccessLevel;
import ru.medeng.tools.AuthorizedController;

@RestController
@RequestMapping("/api")
public class LoginController extends AuthorizedController {
	public static class LevelHolder {
		public String level;

		public LevelHolder(AccessLevel level) {
			super();
			this.level = level.toString();
		}		
	}
	
	public static class TokenHolder {
		public String token;
		
		public TokenHolder(String token) {
			this.token = token;
		}
	}
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("user/level")
	public ResponseEntity<?> getAccessLevel(@RequestHeader("Authorization") String token) {
		var status = auth(token);
		if (status != null) {
			return status;
		}
		
		return ok(new LevelHolder(accessLevel));
	}
	
	@GetMapping("login")
	public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
		var result = authService.login(login, password);
		if (result.isEntityFound()) {
			return ok(new TokenHolder(result.getData()));
		}
		
		if (result.hasError()) {
			return error();
		}
		
		return notFound();
	}
}
