package com.realjava.blogapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public InternalException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
