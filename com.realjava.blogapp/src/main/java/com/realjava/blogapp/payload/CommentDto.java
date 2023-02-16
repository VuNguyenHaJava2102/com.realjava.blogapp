package com.realjava.blogapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private int id;
    private String name;
    private String email;
    private String body;
}
