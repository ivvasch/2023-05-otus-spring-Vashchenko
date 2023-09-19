package ru.otus.service;


import ru.otus.model.Book;
import ru.otus.model.dto.BookDTO;

import java.util.List;

public interface BookService {

    Book saveBook(Book book);

    Book findBookById(String bookId);

    List<Book> findAllBookByGenreId(String genreId);

    boolean updateBook(Book book);

    boolean deleteBookById(String bookId);

    List<Book> findAllBookByAuthorId(String id);

    List<BookDTO> findAll();
//    Page<Book> findPaginated(Pageable pageable);

    void deleteCommentById(String commentId);

}
