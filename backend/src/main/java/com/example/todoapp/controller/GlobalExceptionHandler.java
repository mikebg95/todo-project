package com.example.todoapp.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {
        HttpStatusCode status = ex.getStatusCode();
        return ResponseEntity
                .status(status)
                .body(Map.of(
                        "status", status.value(),
                        "error", status.toString(),
                        "message", Objects.requireNonNull(ex.getReason())
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(Map.of(
                        "status", 500,
                        "error", "Internal Server Error",
                        "message", ex.getMessage()
                ));
    }
}
