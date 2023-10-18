package ru.otus.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.GenreService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private static final Logger log = LogManager.getLogger(GenreServiceImpl.class);

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Genre save(Genre genre) {
        log.info("Method save got genre with name {} for save genre.", genre.getName());
        return genreRepository.save(genre);
    }

    @Override
    public Genre findById(String genreId) {
        log.info("Method findById got genre id {} for search genre.", genreId);
        return genreRepository.findById(genreId).orElse(null);
    }

    @Override
    public Genre findByName(String genreName) {
        log.info("Method findByName got genre with name {} for find genre.", genreName);
        return genreRepository.findByName(genreName);
    }

    @Override
    public List<Book> findAllBooksByGenreId(String genreId) {
        log.info("Method findAllBooksByGenreId got genre id {} for find all books by genre.", genreId);
        List<Book> books = bookRepository.findAll();
        Optional<Genre> byId = genreRepository.findById(genreId);
        List<Book> bookList = null;
        if (byId.orElse(null) != null) {
            bookList = books.stream().filter(book -> book.getGenre().getId().equals(genreId)).toList();
        }
        return bookList;
    }

    @Override
    @Transactional
    public boolean deleteById(String genreId) {
        log.info("Method deleteById got genre id {} for delete these genre.", genreId);
        List<Book> booksByGenreId = bookRepository.findBooksByGenreId(genreId);
        if (!booksByGenreId.isEmpty()) {
            return false;
        } else {
            genreRepository.deleteById(genreId);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean update(Genre genre) {
        log.info("Method update got genre with id {} for update genre.", genre.getName());
        return genreRepository.save(genre) != null;
    }
}
