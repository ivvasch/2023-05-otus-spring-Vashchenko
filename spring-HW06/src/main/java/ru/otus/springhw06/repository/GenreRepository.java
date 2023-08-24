package ru.otus.springhw06.repository;

import ru.otus.springhw06.model.Genre;

import java.util.Optional;

public interface GenreRepository{
    Genre save(Genre genre);
    Optional<Genre> findById(String genreId);
    Genre findByGenreName(String genreName);
    void deleteById(String genreId);
}
