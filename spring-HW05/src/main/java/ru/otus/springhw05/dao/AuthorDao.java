package ru.otus.springhw05.dao;

import ru.otus.springhw05.model.Author;

public interface AuthorDao {

    Integer save(Author author);
    Author findById(String authorId);
    Author findByName(String authorName);
    boolean deleteById(String authorId);
    boolean update(Author author);
}
