package ru.medeng.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MedEngController {
	public <T> ResponseEntity<T> ok(T body) {
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	public <T> ResponseEntity<T> ok() {
		return ok(null);
	}
	
	public <T> ResponseEntity<T> created(T body) {
		return new ResponseEntity<T>(body, HttpStatus.CREATED);
	}
	
	public <T> ResponseEntity<T> error() {
		return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public <T> ResponseEntity<T> notFound() {
		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
	}
	
	public <T> ResponseEntity<T> forbidden() {
		return new ResponseEntity<T>(HttpStatus.FORBIDDEN);
	}
	
	public <T> ResponseEntity<T> unauthorized() {
		return new ResponseEntity<T>(HttpStatus.UNAUTHORIZED);
	}
	
	public <T> ResponseEntity<T> badRequest() {
		return new ResponseEntity<T>(HttpStatus.BAD_REQUEST);
	}
}
