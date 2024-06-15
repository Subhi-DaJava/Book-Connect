package com.uyghurjava.book.handler;

import com.uyghurjava.book.exception.ActivationTokenExpiredException;
import com.uyghurjava.book.exception.OperationNotPermittedException;
import com.uyghurjava.book.exception.UserNotFoundWithGivenUserId;
import com.uyghurjava.book.exception.ValidTokenNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.uyghurjava.book.handler.enums.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException ex)
    {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ACCOUNT_LOCKED.getCode())
                        .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException ex)
    {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ACCOUNT_DISABLED.getCode())
                        .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException ex)
    {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BAD_CREDENTIALS.getCode())
                        .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException ex)
    {
        log.info("Messaging exception: {}, from GlobaleExceptionHandler", ex.getMessage());
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException ex)
    {
        Set<String> errors = new HashSet<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error ->
                        errors.add(error.getDefaultMessage()));

        log.info("Validation errors: {}, from GlobalExceptionHandler", errors);

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .validationErrors(errors)
                        .build());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex)
    {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .businessErrorDescription("Internal server error, contact IT support")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException ex)
    {
        log.info("OperationNotPermittedException exception: {}, from GlobaleExceptionHandler", ex.getMessage());
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(ActivationTokenExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleException(ActivationTokenExpiredException ex)
    {
        log.info("ActivationTokenExpiredException exception: {}, from GlobaleExceptionHandler", ex.getMessage());
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(UserNotFoundWithGivenUserId.class)
    public ResponseEntity<ExceptionResponse> handleException(UserNotFoundWithGivenUserId ex)
    {
        log.info("UserNotFoundWithGivenUserId exception: {}, from GlobaleExceptionHandler", ex.getMessage());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(ValidTokenNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ValidTokenNotFoundException ex)
    {
        log.info("ValidTokenNotFoundException exception: {}, from GlobaleExceptionHandler", ex.getMessage());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException ex)
    {
        log.info("EntityNotFoundException exception: {}, from GlobaleExceptionHandler", ex.getMessage());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .businessErrorDescription("Resource Not Found, contact IT support")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorDescription("ISBN format should be like '978-0-596-52068-7'")
                        .build());
    }

}
