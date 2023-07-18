package ru.otus.springhw05.service;

import ru.otus.springhw05.model.Author;

public interface AuthorService {
    Integer save(Author author);
    Author findById(String authorId);
    boolean deleteById(String authorId);
    boolean update(Author author);
}
