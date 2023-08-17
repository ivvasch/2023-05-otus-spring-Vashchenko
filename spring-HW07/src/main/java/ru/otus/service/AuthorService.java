package ru.otus.service;


import ru.otus.model.Author;

public interface AuthorService {
    Author save(Author author);
    Author findById(String authorId);
    boolean deleteById(String authorId);
    boolean update(Author author);
}
