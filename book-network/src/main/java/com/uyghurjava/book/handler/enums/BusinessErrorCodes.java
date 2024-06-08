package com.uyghurjava.book.handler.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCodes {

    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No code"),
    INCORRECT_CREDENTIALS(1001, HttpStatus.UNAUTHORIZED, "Incorrect credentials"),
    NEW_PASSWORD_DOES_NOT_MATCH(1002, HttpStatus.BAD_REQUEST, "New password does not match"),
    ACCOUNT_LOCKED(1003, HttpStatus.FORBIDDEN, "Account is locked"),
    ACCOUNT_DISABLED(1004, HttpStatus.FORBIDDEN, "Account is disabled"),
    BAD_CREDENTIALS(1005, HttpStatus.UNAUTHORIZED, "Bad credentials");

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
