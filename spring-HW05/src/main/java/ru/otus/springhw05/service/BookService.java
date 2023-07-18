package ru.otus.springhw05.service;

import ru.otus.springhw05.model.Book;

import java.util.List;

public interface BookService {
    Integer saveBook(Book book);
    Book findBookById(String bookId);
    List<Book> findAllBookByGenreId(String genreId);
    boolean updateBook(Book book);
    boolean deleteBookById(String bookId);
    List<Book> findAllBookByAuthorId(String id);
}
