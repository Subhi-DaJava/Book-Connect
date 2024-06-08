package com.uyghurjava.book.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND, reason = "User not found with given user id")
public class UserNotFoundWithGivenUserId extends RuntimeException {
    public UserNotFoundWithGivenUserId(String userNotFound) {
        super(userNotFound);
    }
}
