package ru.medeng.tools;

public class Result<T> {
	private String error;
	private boolean entityFound;
	private T data;
	
	private Result(String error, boolean entityFound, T data) {
		this.error = error;
		this.entityFound = entityFound;
		this.data = data;
	}
	
	public Result() {
		this(null, false, null);
	}
	
	public static <T> Result<T> error(String e) {
		return new Result<T>(e, false, null);
	}
	
	public static <T> Result<T> notFound() {
		return new Result<T>(null, false, null);
	}
	
	public static <T> Result<T> ok(T data) {
		return new Result<T>(null, true, data);
	}
	
	public boolean hasError() {
		return error != null;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isEntityFound() {
		return entityFound;
	}

	public void setEntityFound(boolean entityFound) {
		this.entityFound = entityFound;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

