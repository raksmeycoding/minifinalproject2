package com.miniproject.microservices.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@ControllerAdvice
public class TaskException {


    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFoundException(
            HttpServletRequest request,
            NotFoundException notFoundException,
            WebRequest webRequest) {

        URI uri = URI.create(buildBaseUrl(request) + "/error/");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, notFoundException.getMessage()
        );
        problemDetail.setTitle(notFoundException.getTitle());
        problemDetail.setType(uri);
        return problemDetail;




    }


    // Utility method to build the base URL
    private String buildBaseUrl(HttpServletRequest request) {
        return UriComponentsBuilder
                .fromHttpRequest((HttpRequest) ServletUriComponentsBuilder.fromRequest(request))
                .replacePath(null)
                .build()
                .toUriString();
    }
}
