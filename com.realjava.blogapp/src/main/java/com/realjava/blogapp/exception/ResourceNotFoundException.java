package com.realjava.blogapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
/*
- We can use @ResponseStatus with these error handler methods the same way we did with regular MVC methods in the
previous section.
- When we don't need dynamic error responses, the most straightforward solution is the third one:
marking the Exception class with @ResponseStatus:
(Source: https://www.baeldung.com/spring-response-status)
*/
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(resourceName + " not found with " + fieldName + ": " + fieldValue);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
