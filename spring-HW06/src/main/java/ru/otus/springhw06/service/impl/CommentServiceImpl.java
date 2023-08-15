package ru.otus.springhw06.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.otus.springhw06.model.Comment;
import ru.otus.springhw06.repository.CommentRepository;
import ru.otus.springhw06.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
            return commentRepository.merge(comment);
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
    @Transactional
    public void deleteById(String commentId) {
    commentRepository.deleteById(commentId);
    }
}
