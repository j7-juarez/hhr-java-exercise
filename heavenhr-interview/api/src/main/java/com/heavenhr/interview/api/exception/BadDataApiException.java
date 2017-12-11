package com.heavenhr.interview.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadDataApiException extends RuntimeException {

	private static final long serialVersionUID = 8807874260076143755L;
	
	public BadDataApiException(String message){
		super (message);
	}
	
}
