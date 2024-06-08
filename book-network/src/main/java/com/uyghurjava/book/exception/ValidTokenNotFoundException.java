package com.uyghurjava.book.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@ResponseStatus(value = NOT_FOUND, reason = "Valid token not found")
public class ValidTokenNotFoundException extends RuntimeException {
    public ValidTokenNotFoundException(String tokenNotFound) {
        super(tokenNotFound);
    }
}
