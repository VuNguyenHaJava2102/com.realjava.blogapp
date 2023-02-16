package com.realjava.blogapp.service;

import com.realjava.blogapp.payload.PostDto;
import com.realjava.blogapp.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostResponse getByPage(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(int id);

    PostDto updatePost(PostDto postDto, int id);

    void deletePost(int id);
}
