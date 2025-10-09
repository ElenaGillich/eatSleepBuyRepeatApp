package org.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyProductNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmptyProductNameException (EmptyProductNameException e){
        return e.getMessage();
    }

}
