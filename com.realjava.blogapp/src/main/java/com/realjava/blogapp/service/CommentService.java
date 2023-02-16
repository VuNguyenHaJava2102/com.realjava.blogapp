package com.realjava.blogapp.service;

import com.realjava.blogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllByPostId(long postId);

    CommentDto getSingleComment(long postId, int commentId);

    CommentDto updateComment(long postId, int commentId, CommentDto requestComment);

    void deleteComment(long postId, int commentId);

}
