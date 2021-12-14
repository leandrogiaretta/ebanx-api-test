package com.ebanx.api.test.model;

public class Response <T>{
	
	private int httpStatus;
	
	private T data;
	
	
	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

		
	
	
}
