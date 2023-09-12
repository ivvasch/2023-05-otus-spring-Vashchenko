package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.impl.BookServiceImpl;
import ru.otus.service.impl.CommentServiceImpl;
import ru.otus.testConfig.TestConfig;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import({BookServiceImpl.class, TestConfig.class, CommentServiceImpl.class})
@DisplayName("При обращении к методу контроллера ")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId("1");
        book.setTitle("New book");
        Author author = new Author();
        author.setId("a");
        author.setName("A");
        Genre genre = new Genre();
        genre.setId("g");
        genre.setName("G");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setComments(new ArrayList<>());
        bookService.saveBook(book);
    }

    @Test
    @DisplayName("findBookById страница с книгой отображается корректно")
    void findBookById() {
        try {
            mockMvc.perform(get("/bookpage/{id}", book.getId()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("bookPage"))
                    .andExpect(content().string(containsString("New book")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("editBookById страница для редактирования книги отображается корректно")
    void editBookById() {
        try {
            mockMvc.perform(get("/editbook/{id}", book.getId()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("editBook"))
                    .andExpect(content().string(containsString("New book")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("updateBookById страница книг отображается с измененными данными")
    void updateBookById() {
        try {
            mockMvc.perform(post("/update", book))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/library"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("addBook страница с новой книгой отображается корректно")
    void addBook() {
        Book book2 = new Book();
        book2.setId("2");
        book2.setTitle("New book2");
        Author author = new Author();
        author.setId("au");
        author.setName("AU");
        Genre genre = new Genre();
        genre.setId("ge");
        genre.setName("GE");
        book2.setAuthor(author);
        book2.setGenre(genre);
        book2.setComments(new ArrayList<>());
        bookService.saveBook(book2);
        try {
            mockMvc.perform(post("/add", book2))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/library"));

            mockMvc.perform(get("/bookpage/{id}", book2.getId()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("bookPage"))
                    .andExpect(content().string(containsString("New book2")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("deleteBookById страница с книгами отображается без удаленной")
    void deleteBookById() {
        try {
            mockMvc.perform(get("/deletebook/{id}", book.getId()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/library"));
            mockMvc.perform(get("/bookpage/{id}", book.getId()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("notFound"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}