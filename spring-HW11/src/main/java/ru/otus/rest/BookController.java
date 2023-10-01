package ru.otus.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Book;
import ru.otus.model.dto.BookDTO;
import ru.otus.repository.BookRepository;

@Slf4j
@RestController
@RequestMapping
public class BookController {


    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("api/book")
    public Flux<BookDTO> getAllBooks() {
        return bookRepository.findAll().map(BookDTO::toDto);
    }

    @GetMapping("api/book/{id}")
    public Mono<BookDTO> getBook(@PathVariable("id") String id) {
        BookDTO demo = new BookDTO("demo_id", "demo-title", "demo-authorName", "demo-genreName");
        return bookRepository.findById(id).map(BookDTO::toDto).switchIfEmpty(Mono.fromCallable(() -> demo));
    }
    @PostMapping("api/book")
    public Mono<Book> addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
}
