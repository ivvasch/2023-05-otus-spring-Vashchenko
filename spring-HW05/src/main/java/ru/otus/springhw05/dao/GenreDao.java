package ru.otus.springhw05.dao;

import ru.otus.springhw05.model.Genre;

public interface GenreDao {
    Integer save(Genre genre);
    Genre findById(String genreId);
    Genre findByName(String genreName);
    boolean deleteById(String genreId);
    boolean update(Genre genre);
}
