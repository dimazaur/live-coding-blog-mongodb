package com.devskiller.tasks.blog.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Comment(@Id String id, String postId, String comment, String author, LocalDateTime creationDate) {
}
