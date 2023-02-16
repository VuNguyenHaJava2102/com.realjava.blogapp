package com.realjava.blogapp.controller;

import com.realjava.blogapp.entity.Post;
import com.realjava.blogapp.payload.CommentDto;
import com.realjava.blogapp.payload.PostDto;
import com.realjava.blogapp.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Create new comment
    @PostMapping("/posts/{postId}/comments/create")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long id,
                                                    @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
    }

    // Get all comment by post ID
    @GetMapping("/posts/{postId}/comments/all")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") long postId) {
        return ResponseEntity.ok(commentService.getAllByPostId(postId));
    }

    // Get single comment
    @GetMapping("/posts/{postId}/comments/{commentId}/get-one")
    public ResponseEntity<CommentDto> getComment(@PathVariable("postId") long postId,
                                                 @PathVariable("commentId") int commentId) {
        return ResponseEntity.ok(commentService.getSingleComment(postId, commentId));
    }

    // Update comment
    @PutMapping("/posts/{postId}/comments/{commentId}/update")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable("commentId") int commentId,
                                                    @RequestBody CommentDto requestComment) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, requestComment));
    }

    // Delete comment
    @DeleteMapping("/posts/{postId}/comments/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
                                                @PathVariable("commentId") int commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Delete comment successfully!");
    }
}
