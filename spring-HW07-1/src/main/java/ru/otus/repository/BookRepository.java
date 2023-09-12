package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    
    Book save( Book book);
    
    Optional<Book> findById( String bookId);

    List<Book> findBooksByGenreId(String genreId);
    
    List<Book> findAll();

    List<Book> findBooksByAuthorId(String authorId);

    void deleteBookById(String bookId);
}
