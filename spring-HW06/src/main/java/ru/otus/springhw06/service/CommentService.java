package ru.otus.springhw06.service;

import ru.otus.springhw06.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllCommentsByBookId(String bookId);
    Optional<Comment> findById(String commentId);
    void deleteById(String commentId);
    Comment update(Comment comment);
}
