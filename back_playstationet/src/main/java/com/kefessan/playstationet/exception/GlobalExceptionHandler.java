package com.kefessan.playstationet.exception;

import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserExists(UserAlreadyExistsException ex) {
        System.out.println("💥 Exception atrapada: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        System.out.println("💥 DataIntegrityViolation atrapada: " + ex.getMessage());

        String message = "Error de integridad en la base de datos";

        if (ex.getMostSpecificCause() != null) {
            String causeMsg = ex.getMostSpecificCause().getMessage();
            if (causeMsg.contains("email")) {
                message = "El correo ya está registrado";
            } else if (causeMsg.contains("username")) {
                message = "El nombre de usuario ya existe";
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(Map.of("error", message));
    }
}