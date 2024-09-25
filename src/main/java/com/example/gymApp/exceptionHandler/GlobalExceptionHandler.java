package com.example.gymApp.exceptionHandler;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;

//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//  @ExceptionHandler(NoSuchElementException.class)
//  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
//  }
//
//  @ExceptionHandler(HttpMessageNotReadableException.class)
//  public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body: " + e.getMessage());
//  }
//
//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> handleGenericException(Exception e) {
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//        .body("An unexpected error occurred: " + e.getMessage());
//  }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
    logger.error("Resource not found", e);
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        "Resource not found: " + e.getMessage(),
        System.currentTimeMillis()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    logger.error("Invalid request body", e);
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Invalid request body: " + e.getMessage(),
        System.currentTimeMillis()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
    logger.error("An unexpected error occurred", e);
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "An unexpected error occurred: " + e.getMessage(),
        System.currentTimeMillis()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
