package ru.otus.service;


import ru.otus.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllCommentsByBookId(String bookId);
    Optional<Comment> findById(String commentId);
    void deleteById(String commentId);
    Comment update(Comment comment);
}
