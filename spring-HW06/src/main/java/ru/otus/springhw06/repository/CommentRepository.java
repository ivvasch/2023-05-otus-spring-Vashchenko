package ru.otus.springhw06.repository;

import ru.otus.springhw06.model.Comment;

import java.util.List;
import java.util.Optional;


public interface CommentRepository {
    void deleteById(String commentId);
    Optional<Comment> findById(String commentId);
    List<Comment> findAllCommentsByBookId(String bookId);
    Comment merge(Comment comment);
}
