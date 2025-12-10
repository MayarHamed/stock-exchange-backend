package com.example.stock.exchange.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        String msg = String.format(
                "%s | Exception: %s | Status: %s",
                ex.getMessage(),
                ex.getCause() != null ? ex.getCause().getMessage() : "none",
                ex.getStatus()
        );

        System.out.println(msg); // logs to console
        return ResponseEntity.status(ex.getStatus()).body(msg); // returns to client
    }

    // Optional fallback for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        String msg = String.format(
                "Unexpected Error | Exception: %s | Status: %s",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        System.out.println(msg);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
    }
}
