package com.devskiller.tasks.blog.service;

import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import com.devskiller.tasks.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	CommentService commentService;

	@Test
	public void shouldAddComment() {
		Post post = createTestPost();

		NewCommentDto comment = new NewCommentDto("Author", "Content");
		String commentId = commentService.addComment(post.id(), comment);

		assertThat("Comment id shouldn't be null", commentId, notNullValue());
	}

	private Post createTestPost() {
		Post post = new Post(
			null,
			"Test title",
			"Test content",
			LocalDateTime.of(2018, 5, 20, 20, 51, 16));
		return postRepository.save(post);
	}

	@Test
	public void shouldReturnAddedComment() {
		Post post = createTestPost();

		NewCommentDto comment = new NewCommentDto("Author", "Content");
		String commentId = commentService.addComment(post.id(), comment);
		System.out.println("### commentId = " + commentId);

		List<CommentDto> comments = commentService.getCommentsForPost(post.id());

		assertThat("There should be one comment", comments, hasSize(1));
		assertThat(comments.get(0).author(), equalTo("Author"));
		assertThat(comments.get(0).comment(), equalTo("Content"));
	}
}
