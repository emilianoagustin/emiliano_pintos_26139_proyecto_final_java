package com.techlab.productcrud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult()
              .getFieldErrors()
              .forEach(error -> {
                  errors.put(
                          error.getField(),
                          error.getDefaultMessage()
                  );
              });
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Validation failed");
    response.put("errors", errors);
    return response;
  }
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException exception) {
    Map<String, String> error = new HashMap<>();
    error.put("message", exception.getMessage());
    return error;
  }
}