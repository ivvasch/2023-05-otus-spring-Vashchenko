package ru.otus.springhw06.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import ru.otus.springhw06.model.Comment;
import ru.otus.springhw06.repository.CommentRepository;

import java.util.Optional;

@Component
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment merge(Comment comment) {
        return entityManager.merge(comment);
    }

    @Override
    public void save(Comment comment) {
        entityManager.persist(comment);
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
