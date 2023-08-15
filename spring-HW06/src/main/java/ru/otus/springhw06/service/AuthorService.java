package ru.otus.springhw06.service;

import ru.otus.springhw06.model.Author;

public interface AuthorService {
    Author save(Author author);
    Author findById(String authorId);
    boolean deleteById(String authorId);
    boolean update(Author author);
}
