package com.erwan.human.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BeanNotFound.class)
    public ResponseEntity<Object> handleEntityNotFound(
            BeanNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
    }
}
