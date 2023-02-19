package com.wipro.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhoneNumberInvalidException extends RuntimeException {

	public PhoneNumberInvalidException(String msg) {
		super(msg);
	}
	
}
