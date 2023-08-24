package ru.otus.springhw06.repository;

import ru.otus.springhw06.model.Comment;

import java.util.Optional;


public interface CommentRepository {
    void deleteById(String commentId);
    Optional<Comment> findById(String commentId);
    Comment merge(Comment comment);
    void save(Comment comment);
}
