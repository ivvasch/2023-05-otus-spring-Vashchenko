package ru.otus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.model.Comment;
import ru.otus.repository.CommentRepository;
import ru.otus.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger log = LogManager.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment update(Comment comment) {
        log.info("Method update got comment with id {} for update comment.", comment.getCommentId());
        return commentRepository.save(comment);
    }

    @Override
    public Comment save(Comment comment) {
        log.info("Method save got comment {} for save comment.", comment.getComment());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllCommentsByBookId(String bookId) {
        log.info("Method findAllCommentsByBookId got book id {} for find all comments on book.", bookId);
        return commentRepository.findAllCommentsByBookId(bookId);
    }

    @Override
    public Optional<Comment> findById(String commentId) {
        log.info("Method findById got comment id {} for find comment.", commentId);
        return commentRepository.findById(commentId);
    }

    @Override
    public void deleteById(@NonNull String commentId) {
        log.info("Method deleteById got comment id {} for delete comment.", commentId);
        commentRepository.deleteById(commentId);
    }
}
