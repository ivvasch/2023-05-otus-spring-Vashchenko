package ru.otus.springhw06.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springhw06.model.Book;
import ru.otus.springhw06.model.Comment;
import ru.otus.springhw06.repository.BookRepository;
import ru.otus.springhw06.repository.CommentRepository;
import ru.otus.springhw06.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
            return commentRepository.merge(comment);
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        commentRepository.save(comment);
        Optional<Comment> byId = commentRepository.findById(comment.getCommentId());
        return byId.orElse(null);
    }

    @Override
    @Transactional
    public List<Comment> findAllCommentsByBookId(String bookId) {
        Optional<Book> byId = bookRepository.findById(bookId);
        return byId.orElse(new Book()).getComments();
    }

    @Override
    @Transactional
    public Optional<Comment> findById(String commentId) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isEmpty()) {
            return byId;
        }
        byId.get().getBook().getTitle();
        return byId;
    }

    @Override
    @Transactional
    public void deleteById(String commentId) {
    commentRepository.deleteById(commentId);
    }
}
