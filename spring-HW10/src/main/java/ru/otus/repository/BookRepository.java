package ru.otus.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Book save(Book book);

    Optional<Book> findById(String bookId);

    @Query("{'genre.$id':  ?0}")
    List<Book> findBooksByGenreId(String genreId);

    List<Book> findAll();

    List<Book> findBooksByAuthorId(String authorId);

    void deleteBookById(String bookId);

    @Query("{'title':  ?0}")
    Optional<Book> findBookByName(String title);

    @Query("{'comments.$id': ObjectId(?0)}")
    Book findBookByCommentId(String commentId);
}
