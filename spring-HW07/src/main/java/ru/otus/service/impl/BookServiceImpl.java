package ru.otus.service.impl;

import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        Optional<Book> bookById = bookRepository.findById(book.getId());
        Book findBook = bookById.orElse(null);
        if (findBook != null && findBook.equals(book)) {
            return book;
        }
        Optional<Author> authorById = authorRepository.findById(book.getAuthor().getId());
        Optional<Genre> genreById = genreRepository.findById(book.getGenre().getId());
        if (bookById.isEmpty() && authorById.isEmpty() && genreById.isEmpty()) {
            return bookRepository.save(book);
        } else {
            return bookRepository.merge(book);
        }
    }

    @Override
    @Transactional
    public Book findBookById(String bookId) {
        Optional<Book> byId;
        try {
            byId = bookRepository.findById(bookId);
            byId.ifPresent(book -> book.getComments().size());
        } catch (NoResultException e) {
            return null;
        }
        return byId.orElse(null);
    }

    @Override
    public List<Book> findAllBookByGenreId(String genreId) {
        return bookRepository.findBooksByGenreId(genreId);
    }

    @Override
    @Transactional
    public boolean updateBook(Book book) {
        return bookRepository.merge(book) != null;
    }

    @Override
    @Transactional
    public boolean deleteBookById(String bookId) {
        if (findBookById(bookId) != null) {
            bookRepository.deleteBookById(bookId);
        }
        return findBookById(bookId) == null;
    }

    @Override
    public List<Book> findAllBookByAuthorId(String authorId) {
        return bookRepository.findBooksByAuthorId(authorId);
    }
}
