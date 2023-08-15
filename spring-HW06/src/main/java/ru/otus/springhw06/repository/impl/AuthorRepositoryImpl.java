package ru.otus.springhw06.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.springhw06.model.Author;
import ru.otus.springhw06.repository.AuthorRepository;

import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Author save(Author author) {
        Author findAuthor = entityManager.find(Author.class, author.getId());
        if (findAuthor != null && findAuthor.equals(author)) {
            return author;
        }
        if (findAuthor == null) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(String authorId) {
        Author author = entityManager.find(Author.class, authorId);
        return Optional.ofNullable(author);
    }

    @Override
    public Author findByAuthorName(String authorName) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.authorName = :name", Author.class);
        query.setParameter("name", authorName);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(String authorId) {
        Author author = entityManager.find(Author.class, authorId);
        entityManager.remove(author);
    }
}
