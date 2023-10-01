package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.service.BookService;
import ru.otus.service.impl.BookServiceImpl;
import ru.otus.service.impl.CommentServiceImpl;
import ru.otus.testConfig.TestConfig;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @WithMockUser(username = "ivan", password = "navi")
    @Test
    void mainPage() throws Exception {
        mockMvc.perform(get("/library"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/update", book).with(csrf()))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/addcomment/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/editbook/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/bookpage/1"))
                .andExpect(status().isOk());

    }
}
