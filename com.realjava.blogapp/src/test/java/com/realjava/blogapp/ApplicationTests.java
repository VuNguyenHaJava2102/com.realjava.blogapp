package com.realjava.blogapp;

import com.realjava.blogapp.entity.Post;
import com.realjava.blogapp.payload.PostDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private ModelMapper mapper;

	@Test
	void contextLoads() {
		Post post = new Post();
		post.setId(1);
		post.setTitle("?");

		PostDto postDto = mapper.map(post, PostDto.class);
		System.out.println("_______________________");
		System.out.println(postDto.toString());
	}

}
