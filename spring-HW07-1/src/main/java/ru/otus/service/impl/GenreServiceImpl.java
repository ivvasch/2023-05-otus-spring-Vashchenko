package ru.otus.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre findById(String genreId) {
        return genreRepository.findById(genreId).orElse(null);
    }

    @Override
    public Genre findByName(String genreName) {
        return genreRepository.findByName(genreName);
    }

    @Override
    public List<Book> findAllBooksByGenreId(String genreId) {
        List<Book> books = new ArrayList<>();
        Optional<Genre> byId = genreRepository.findById(genreId);
        if (byId.orElse(null) != null) {
            books = byId.get().getBooks();
            books.size();
        }
        return books;
    }

    @Override
    public boolean deleteById(String genreId) {
        List<Book> booksByGenreId = bookRepository.findBooksByGenreId(genreId);
        if (!booksByGenreId.isEmpty()) {
            return false;
        } else {
            genreRepository.deleteById(genreId);
        }
        return true;
    }

    @Override
    public boolean update(Genre genre) {
        return genreRepository.save(genre) != null;
    }
}
