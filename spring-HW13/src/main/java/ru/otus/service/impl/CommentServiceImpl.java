package ru.otus.service.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.model.Comment;
import ru.otus.repository.CommentRepository;
import ru.otus.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment update(Comment comment) {
            return commentRepository.save(comment);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllCommentsByBookId(String bookId) {
        return commentRepository.findAllCommentsByBookId(bookId);
    }

    @Override
    public Optional<Comment> findById(String commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void deleteById(@NonNull String commentId) {
    commentRepository.deleteById(commentId);
    }
}
