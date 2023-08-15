package ru.otus.springhw06.service;

import ru.otus.springhw06.model.Book;
import ru.otus.springhw06.model.Genre;

import java.util.List;

public interface GenreService {
    Genre save(Genre genre);
    Genre findById(String authorId);
    Genre findByName(String genreName);
    List<Book> findAllBooksByGenreId(String genreId);
    boolean deleteById(String authorId);
    boolean update(Genre genre);
}
