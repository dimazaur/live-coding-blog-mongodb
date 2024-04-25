package com.devskiller.tasks.blog.service;

import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
public class PostServiceTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostService postService;

	@Test
	public void shouldReturnCreatedPost() {
		LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
		Post post = new Post(
			null,
			"Test title",
			"Test content",
			creationDate);
		post = postRepository.save(post);

		PostDto postDto = postService.getPost(post.id());

		assertNotNull("Post shouldn't be null", postDto);
		assertEquals("Test content", postDto.content());
		assertEquals("Test title", postDto.title());
		assertEquals(creationDate, postDto.creationDate());
	}

	@Test
	public void shouldReturnNullForNotExistingPost() {
		PostDto postDto = postService.getPost("123");

		assertNull(postDto);
	}
}
