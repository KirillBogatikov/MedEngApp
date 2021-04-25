package ru.medeng.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.medeng.domain.AuthService;
import ru.medeng.tools.MedEngController;

@RestController
@RequestMapping("/api/login")
public class LoginController extends MedEngController {
	@Autowired
	private AuthService authService;
	
	public static class TokenHolder {
		public String token;
		
		public TokenHolder(String token) {
			this.token = token;
		}
	}
	
	@GetMapping
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
