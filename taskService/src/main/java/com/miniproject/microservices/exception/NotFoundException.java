package com.miniproject.microservices.exception;


import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{

    private String title;


    public NotFoundException(String message, String title) {
        super(message);
        this.title = title;
    }
}
