package ru.otus.springhw05.service;

import ru.otus.springhw05.model.Genre;

public interface GenreService {
    Integer save(Genre genre);
    Genre findById(String authorId);
    Genre findByName(String genreName);
    boolean deleteById(String authorId);
    boolean update(Genre genre);
}
