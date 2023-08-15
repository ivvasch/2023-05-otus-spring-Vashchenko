package ru.otus.springhw06.service;

import ru.otus.springhw06.model.Genre;

public interface GenreService {
    Genre save(Genre genre);
    Genre findById(String authorId);
    Genre findByName(String genreName);
    boolean deleteById(String authorId);
    boolean update(Genre genre);
}
