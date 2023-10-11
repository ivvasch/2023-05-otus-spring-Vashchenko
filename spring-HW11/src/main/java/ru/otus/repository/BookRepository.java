package ru.otus.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {
    Mono<Book> save(Book book);

    Mono<Book> findById(String bookId);

    Flux<Book> findAll();
}
