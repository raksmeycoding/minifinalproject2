package com.miniproject.keycloakadminclient.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class NotFoundException extends RuntimeException{
    private String errorType;
    public NotFoundException(String errorType, String errorMessage) {
        super(errorMessage);
        this.errorType = errorType;
    }
}
