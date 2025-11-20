package com.example.obligatorio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.obligatorio.modelo.PeajeException;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(PeajeException.class)
    public ResponseEntity<String> manejarException(PeajeException ex) {
        
       return ResponseEntity.status(299).body(ex.getMessage());
    }
}
