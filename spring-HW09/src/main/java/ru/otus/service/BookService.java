package ru.otus.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.model.Book;

import java.util.List;

public interface BookService {

    Book saveBook(Book book);

    Book findBookById(String bookId);

    List<Book> findAllBookByGenreId(String genreId);

    boolean updateBook(Book book);

    boolean deleteBookById(String bookId);

    List<Book> findAllBookByAuthorId(String id);

    List<Book> findAll();
    Page<Book> findPaginated(Pageable pageable);

    void deleteCommentById(String commentId);

}
