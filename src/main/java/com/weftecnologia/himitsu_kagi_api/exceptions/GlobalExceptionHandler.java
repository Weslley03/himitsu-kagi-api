package com.weftecnologia.himitsu_kagi_api.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.CheckingPasswordException;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.GenericNotFoundException;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.JwtValidationException;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.UserAlreadyExistsException;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.database.DatabaseException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleBodyValidationsException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(err -> {
      errors.put(err.getField(), err.getDefaultMessage());
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        Map.of(
            "message", "body request validation error.",
            "status", String.valueOf(HttpStatus.BAD_REQUEST.value()),
            "errors", errors));
  }

  @ExceptionHandler(CheckingPasswordException.class)
  public ResponseEntity<?> handleUserAlreadyExistsException(CheckingPasswordException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        Map.of(
            "message", ex.getMessage(),
            "status", String.valueOf(HttpStatus.BAD_REQUEST.value())));
  }

  @ExceptionHandler(GenericNotFoundException.class)
  public ResponseEntity<?> handleGenericNotFoundException(GenericNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        Map.of(
            "message", ex.getMessage(),
            "status", String.valueOf(HttpStatus.NOT_FOUND.value())));
  }

  @ExceptionHandler(JwtValidationException.class)
  public ResponseEntity<?> handleJwtValidationException(JwtValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        Map.of(
            "message", ex.getMessage(),
            "status", String.valueOf(HttpStatus.BAD_REQUEST.value())));
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<?> handleDatabaseException(DatabaseException ex) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
        Map.of(
            "message", ex.getMessage(),
            "status", String.valueOf(HttpStatus.BAD_REQUEST.value())));
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        Map.of(
            "message", ex.getMessage(),
            "status", String.valueOf(HttpStatus.BAD_REQUEST.value())));
  }
}
