package com.uyghurjava.book.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST, reason = "Operation not permitted")
public class OperationNotPermittedException extends RuntimeException {
    public OperationNotPermittedException(String s) {
        super(s);
    }
}
