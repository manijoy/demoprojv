package com.wipro.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotfoundException extends RuntimeException {


	public UserNotfoundException(String msg) {
		super(msg);
	}
	
}
