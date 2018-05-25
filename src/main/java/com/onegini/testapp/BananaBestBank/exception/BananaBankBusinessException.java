package com.onegini.testapp.BananaBestBank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BananaBankBusinessException extends Exception {

    public BananaBankBusinessException(String message) {
        super(message);
    }
}
