package com.miniproject.keycloakadminclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

@ControllerAdvice
public class CustomExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleException(Exception ex) {
//        // Log the exception for debugging purposes
//        ex.printStackTrace();
//        return "An error occurred while processing your request.";
//    }



    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFoundException(NotFoundException notFoundException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, notFoundException.getMessage());
        problemDetail.setTitle(notFoundException.getErrorType());
        problemDetail.setType(URI.create("localhost:8080/error/"));
        return problemDetail;
    }

}
