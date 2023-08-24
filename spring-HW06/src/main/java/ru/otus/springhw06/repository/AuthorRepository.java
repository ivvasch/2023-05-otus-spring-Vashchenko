package ru.otus.springhw06.repository;

import ru.otus.springhw06.model.Author;

import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);
    Optional<Author> findById(String authorId);
    Author findByAuthorName(String authorName);
    void deleteById(String authorId);
}
