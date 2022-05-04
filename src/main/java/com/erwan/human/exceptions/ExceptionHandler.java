package com.erwan.human.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BeanNotFound.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<Object> handleEntityNotFound(
            BeanNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(
            ApiException ex) {
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }
}
