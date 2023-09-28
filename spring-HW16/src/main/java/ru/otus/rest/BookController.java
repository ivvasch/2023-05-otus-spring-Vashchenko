package ru.otus.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Book;
import ru.otus.model.dto.BookDTO;
import ru.otus.service.BookService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("api/book")
    public List<BookDTO> getAllBooks() {
        return service.findAll();
    }

    @GetMapping("api/book/{id}")
    public BookDTO getBook(@PathVariable("id") String id) {
        Book book = service.findBookById(id);
        BookDTO bookDTO = null;
        if (book != null) {
            bookDTO = BookDTO.toDto(book);
        }
        return bookDTO;
    }
    @PostMapping("api/book")
    public Book addBook(@RequestBody BookDTO bookDTO) {
        Book book = bookDTO.dtoToBook();
        return service.saveBook(book);
    }
}
