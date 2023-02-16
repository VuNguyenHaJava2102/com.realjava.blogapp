package com.realjava.blogapp.service.impl;

import com.realjava.blogapp.entity.Comment;
import com.realjava.blogapp.entity.Post;
import com.realjava.blogapp.exception.InternalException;
import com.realjava.blogapp.exception.ResourceNotFoundException;
import com.realjava.blogapp.payload.CommentDto;
import com.realjava.blogapp.repository.CommentRepository;
import com.realjava.blogapp.repository.PostRepository;
import com.realjava.blogapp.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        /*
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
         */

        return commentDto;
    }

    private Comment convertToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);

        /*
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
         */

        return comment;
    }

    // Create new comment
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);

        // Get Post by ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(postId)));
        comment.setPost(post);

        // Save comment
        Comment savedComment = commentRepository.save(comment);

        // Convert to DTO
        CommentDto responseComment = convertToDto(savedComment);

        return responseComment;
    }

    // Get all comments by post ID
    @Override
    public List<CommentDto> getAllByPostId(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(postId)));
        List<Comment> commentList = commentRepository.findByPost(post);

        return commentList.stream().map(comment -> convertToDto(comment)).collect(Collectors.toList());
    }

    // Get single comment
    @Override
    public CommentDto getSingleComment(long postId, int commentId) {
        Comment comment = getCommentById(postId, commentId);
        return convertToDto(comment);
    }

    // Update comment
    @Override
    public CommentDto updateComment(long postId, int commentId, CommentDto requestComment) {
        Comment comment = getCommentById(postId, commentId);

        comment.setName(requestComment.getName());
        comment.setEmail(requestComment.getEmail());
        comment.setBody(requestComment.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment);
    }

    // Delete comment
    @Override
    public void deleteComment(long postId, int commentId) {
        getCommentById(postId, commentId);
        commentRepository.deleteByCommentId(commentId);
        System.out.println("Hey!!!");
    }

    // This method is used for get single comment, update comment, delete comment
    private Comment getCommentById(long postId, int commentId) {
        // Get post by ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(postId)));

        // Get comment by ID
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", String.valueOf(commentId)));

        // Check comment belongs to post or not
        if(!post.getComments().contains(comment)) {
            throw new InternalException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post!");
        }

        return comment;
    }
}