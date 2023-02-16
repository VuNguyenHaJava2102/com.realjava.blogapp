package com.realjava.blogapp.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class PostDto {

    private long id;

    @Size(min = 2, message = "Post's title should have at least 2 characters")
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;

    /*
    In Spring Framework, Data Transfer Object (DTO) is an object that carries data between processes. When you’re
    working with a remote interface, each call is expensive. As a result, you need to reduce the number of calls.
    The solution is to create a Data Transfer Object that can hold all the data for the call. It needs to be
    serializable to go across the connection
    (Source: https://www.geeksforgeeks.org/data-transfer-object-dto-in-spring-mvc-with-example/)
    */
}
