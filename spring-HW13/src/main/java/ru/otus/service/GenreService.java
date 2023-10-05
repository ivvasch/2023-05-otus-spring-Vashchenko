package ru.otus.service;


import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;

public interface GenreService {

    Genre save(Genre genre);

    Genre findById(String authorId);

    Genre findByName(String genreName);

    boolean deleteById(String authorId);

    boolean update(Genre genre);

    List<Book> findAllBooksByGenreId(String genreId);

}
