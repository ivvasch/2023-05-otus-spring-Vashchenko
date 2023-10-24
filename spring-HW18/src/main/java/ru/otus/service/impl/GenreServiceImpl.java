package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    public GenreServiceImpl(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
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
        return genreRepository.save(genre) != null;
    }
}
