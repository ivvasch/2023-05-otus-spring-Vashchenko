package ru.otus.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.GenreService;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
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
        return genreRepository.findByGenreName(genreName);
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
