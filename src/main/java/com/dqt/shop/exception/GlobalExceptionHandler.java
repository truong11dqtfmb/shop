package com.dqt.shop.exception;

import com.dqt.shop.dto.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(ResponseMessage.error(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> res = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(
                (er) -> {
                    String fieldName = ((FieldError) er).getField();
                    String message = er.getDefaultMessage();
                    res.put(fieldName, message);
                }
        );

        return new ResponseEntity<>(ResponseMessage.error("Invalid", res), HttpStatus.BAD_REQUEST);

    }
}

