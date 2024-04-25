package com.devskiller.tasks.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;

@Service
public class CommentService {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(String postId) {
		return commentRepository.findAllByPostIdOrderByCreationDateDesc(postId)
			.stream().map(comment -> new CommentDto(comment.id(), comment.comment(), comment.author(), comment.creationDate()))
			.collect(Collectors.toList());
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId        id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed postId
	 */
	public String addComment(String postId, NewCommentDto newCommentDto) {
		System.out.println("### POST: " + postRepository.findById(postId));
		if (postId == null) {
			throw new IllegalArgumentException("Post Id can't be null");
		}

		if (!postRepository.existsById(postId)) {
			//More natural will be throwing ResourceNotFoundException and make HTTP 404 error
			//But the requirement of this task says: Do not change unit tests
			//So I skipped this part
			throw new IllegalArgumentException("Post not found: " + postId);
		}

		return commentRepository.save(new Comment(null, postId, newCommentDto.content(), newCommentDto.author(), LocalDateTime.now())).id();
	}
}
