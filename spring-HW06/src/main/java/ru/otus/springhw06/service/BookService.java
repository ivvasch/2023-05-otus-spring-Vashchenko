package ru.otus.springhw06.service;

import ru.otus.springhw06.model.Book;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);
    Book findBookById(String bookId);
    List<Book> findAllBookByGenreId(String genreId);
    boolean updateBook(Book book);
    boolean deleteBookById(String bookId);
    List<Book> findAllBookByAuthorId(String id);
}
