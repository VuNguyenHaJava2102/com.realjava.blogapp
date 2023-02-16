package com.realjava.blogapp.controller;

import com.realjava.blogapp.payload.PostDto;
import com.realjava.blogapp.payload.PostResponse;
import com.realjava.blogapp.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // Create a post
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto responsePost = postService.createPost(postDto);
        return new ResponseEntity<>(responsePost, HttpStatus.CREATED);
    }

    // Get all posts
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // Get by page
    @GetMapping("/page")
    public ResponseEntity<PostResponse> getByPage(@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                  @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                  @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                  @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return ResponseEntity.ok(postService.getByPage(pageNo, pageSize, sortBy, sortDir));
    }

    // Get post by ID
    @GetMapping("/get-one/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") int id) {

        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Update existing post
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable("id") int id) {
        PostDto responsePostDto = postService.updatePost(postDto, id);
        return ResponseEntity.ok(responsePostDto);
    }

    // Delete existing post
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Delete post with ID: " + String.valueOf(id) + " successfully!");
    }
}
