package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    @NonNull
    Book save(@NonNull Book book);
    @NonNull
    Optional<Book> findById(@NonNull String bookId);
    List<Book> findBooksByGenreId(String genreId);
    @NonNull
    List<Book> findAll();
    List<Book> findBooksByAuthorId(String authorId);
    void deleteBookById(String bookId);
    Book merge(Book book);
}
