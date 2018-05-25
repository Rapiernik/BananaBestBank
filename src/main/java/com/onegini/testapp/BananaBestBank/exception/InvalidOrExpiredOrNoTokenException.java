package com.onegini.testapp.BananaBestBank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidOrExpiredOrNoTokenException extends Exception {

	public InvalidOrExpiredOrNoTokenException(String message) {
		super(message);
	}
}
