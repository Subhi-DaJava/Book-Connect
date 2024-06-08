package com.uyghurjava.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Activation token expired")
public class ActivationTokenExpiredException extends RuntimeException {
    public ActivationTokenExpiredException(String s) {
        super(s);
    }
}
