package ru.otus.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.model.dto.BookDTO;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.testConfig.TestConfig;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(TestConfig.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;
    @MockBean
    CommentService commentService;
    @MockBean
    CommentRepository commentRepository;
    @MockBean
    BookRepository bookRepository;
    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

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
    }

    @Test
    void getAllBooks() {
        List<BookDTO> books = new ArrayList<>();
        books.add(BookDTO.toDto(book));
        Mockito.when(bookService.findAll()).thenReturn(books);
        try {
            mockMvc.perform(get("/api/book"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$[0].title", Matchers.equalTo("New book")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("getBook получаем корректно заполненный DTO книги")
    void getBook() {
        Mockito.when(bookService.findBookById(book.getId())).thenReturn(book);
        try {
            mockMvc.perform(get("/api/book/" + book.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", Matchers.equalTo("New book")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addBook() {
        BookDTO dto = BookDTO.toDto(book);
        Mockito.when(bookService.saveBook(any(Book.class))).thenReturn(book);
        try {
            mockMvc.perform(post("/api/book")
                            .content(mapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("New book"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
