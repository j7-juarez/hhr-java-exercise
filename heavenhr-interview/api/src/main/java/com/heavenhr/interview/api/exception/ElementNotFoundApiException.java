package com.heavenhr.interview.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ElementNotFoundApiException extends RuntimeException {

	private static final long serialVersionUID = 8807874260076143755L;
	
	public ElementNotFoundApiException(String message){
		super (message);
	}
	
}
