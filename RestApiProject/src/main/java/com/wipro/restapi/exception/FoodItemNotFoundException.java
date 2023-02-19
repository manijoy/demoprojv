package com.wipro.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FoodItemNotFoundException extends RuntimeException {

	public FoodItemNotFoundException(String msg) {
		super(msg);
	
	}
}
