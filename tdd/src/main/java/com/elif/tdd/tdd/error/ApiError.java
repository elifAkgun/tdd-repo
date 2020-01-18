package com.elif.tdd.tdd.error;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {
	
	private long timeStamp = new Date().getTime();
	
	private int statusCode;
	
	private String message;
	
	private String url;
	
	private Map<String,String> validationErrors;

	public ApiError(int statusCode, String message, String url) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.url = url;
	}
	
	

}
