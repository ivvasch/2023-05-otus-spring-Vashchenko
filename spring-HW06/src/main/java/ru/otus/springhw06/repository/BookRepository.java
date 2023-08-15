package ru.otus.springhw06.repository;

import ru.otus.springhw06.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(String s);
    List<Book> findBooksByGenreId(String genreId);
    List<Book> findAll();
    List<Book> findBooksByAuthorId(String authorId);
    void deleteBookById(String bookId);
    Book merge(Book book);
}
