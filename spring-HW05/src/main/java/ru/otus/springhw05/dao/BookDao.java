package ru.otus.springhw05.dao;

import ru.otus.springhw05.model.Book;

import java.util.List;

public interface BookDao {

    Integer saveBook(Book book);
    Book findBookById(String bookId);
    List<Book> findAllBookByGenreId(String genreId);
    boolean updateBook(Book book);
    boolean deleteBookById(String bookId);
    List<Book> findAllBookByAuthorId(String id);
}
