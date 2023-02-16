package com.realjava.blogapp.service.impl;

import com.realjava.blogapp.entity.Post;
import com.realjava.blogapp.exception.ResourceNotFoundException;
import com.realjava.blogapp.payload.PostDto;
import com.realjava.blogapp.payload.PostResponse;
import com.realjava.blogapp.repository.PostRepository;
import com.realjava.blogapp.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    // Convert DTO to Entity
    private Post convertToEntity(PostDto postDto) {
        /*
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
         */

        // Using ModelMapper
        Post post = mapper.map(postDto, Post.class);

        return post;
    }

    // Convert Entity to DTO
    private PostDto convertToDto(Post post) {

        /*
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        */

        // Using ModelMapper
        PostDto postDto = mapper.map(post, PostDto.class);

        return postDto;
    }

    // Create new post
    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = convertToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto responsePost = convertToDto(savedPost);

        return responsePost;
    }

    // Get all posts
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> convertToDto(post)).collect(Collectors.toList());
    }

    // Get post by page
    @Override
    public PostResponse getByPage(int pageNo, int pageSize, String sortBy, String sortDir) {

        // Initialize Sort and Pageable
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> postList = postPage.getContent();

        // Initialize fields of PostResponse instance
        List<PostDto> postDtoList = postList.stream().map(post -> convertToDto(post)).collect(Collectors.toList());
        int totalElements = (int) postPage.getTotalElements();
        int totalPages = postPage.getTotalPages();
        boolean isLastPage = postPage.isLast();

        // Create PostResponse instance and set its fields
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(totalElements);
        postResponse.setTotalPages(totalPages);
        postResponse.setLastPage(isLastPage);

        return postResponse;
    }

    // Get post by ID
    @Override
    public PostDto getPostById(int id) {
        Post post = postRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(id)));
        return convertToDto(post);
    }

    // Update existing post
    @Override
    public PostDto updatePost(PostDto postDto, int id) {
        Post post = postRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(id)));

        // Set all field to post
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);

        return convertToDto(updatedPost);
    }

    // Delete existing post
    @Override
    public void deletePost(int id) {
        Post post = postRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", String.valueOf(id)));
        postRepository.delete(post);
    }

}
