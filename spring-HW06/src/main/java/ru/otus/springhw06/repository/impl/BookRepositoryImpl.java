package ru.otus.springhw06.repository.impl;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import ru.otus.springhw06.model.Book;
import ru.otus.springhw06.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Book save(Book book) {
            entityManager.persist(book);
            return book;
    }

    @Override
    public Book merge(Book book) {
        return entityManager.merge(book);
    }

    @Override
    public Optional<Book> findById(String bookId) {
        if (bookId != null) {

            Book book = entityManager.find(Book.class, bookId);
            return Optional.ofNullable(book);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findBooksByGenreId(String genreId) {
        if (genreId != null) {
            TypedQuery<Book> query = entityManager.createQuery("select b from Book b where b.genre.id = :id", Book.class);
            query.setParameter("id", genreId);
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("comments-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchGraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> findBooksByAuthorId(String authorId) {
        if (authorId != null) {
            TypedQuery<Book> query = entityManager.createQuery("select b from Book b where b.author.id = :id", Book.class);
            query.setParameter("id", authorId);
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteBookById(String bookId) {
        Book book = entityManager.find(Book.class, bookId);
        entityManager.remove(book);
    }
}
