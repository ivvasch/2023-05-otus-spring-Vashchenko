package ru.otus.springhw06.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.springhw06.model.Book;
import ru.otus.springhw06.model.Comment;
import ru.otus.springhw06.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> findAllCommentsByBookId(String bookId) {
        Book book = entityManager.find(Book.class, bookId);
        return book.getComments();
    }

    @Override
    public Comment merge(Comment comment) {
        return entityManager.merge(comment);
    }

    @Override
    public Optional<Comment> findById(String commentId) {
        Comment comment = entityManager.find(Comment.class, commentId);
        return Optional.ofNullable(comment);
    }

    @Override
    public void deleteById(String commentId) {
        Comment comment = entityManager.find(Comment.class, commentId);
        entityManager.remove(comment);
    }
}
