package ru.otus.springhw05.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.springhw05.model.Author;
import ru.otus.springhw05.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("При работе с authorService,")
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    private Author author;
    @BeforeEach
    void setUp() {
        author = new Author("255be70b-a76a-4eda-8892-b8e929780bfb", "Herbert George Wells");
    }

    @Test
    @DisplayName("автор записывается в базу корректно")
    void shouldSaveAuthorCorrect() {
        Integer saveResult = authorService.save(author);
        assertEquals(1, saveResult);
    }

    @Test
    @DisplayName("автор и его книги удаляются из базы")
    void shouldDeleteAuthorByIdAndSetEmptyAuthorIdToBook() {
        boolean isDeleteById = authorService.deleteById("cc02c80f-de17-4d98-94b6-994670a08ce7");
        assertTrue(isDeleteById);
        List<Book> allBookByAuthorId = bookService.findAllBookByAuthorId("cc02c80f-de17-4d98-94b6-994670a08ce7");
        assertTrue(allBookByAuthorId.isEmpty());

    }

    @Test
    @DisplayName("автор находится в базе по его id")
    void shouldFindAuthorByIdService() {
        authorService.save(author);
        Author authorById = authorService.findById("255be70b-a76a-4eda-8892-b8e929780bfb");
        assertEquals("Herbert George Wells", authorById.getAuthorName());

    }

    @Test
    @DisplayName("имя автора обновляется в базе")
    void shouldUpdateAuthorNameService() {
        authorService.save(author);
        String newName = "H.G. Wells";
        author = new Author("255be70b-a76a-4eda-8892-b8e929780bfb", newName);
        authorService.update(author);
        Author authorById = authorService.findById("255be70b-a76a-4eda-8892-b8e929780bfb");
        assertEquals("H.G. Wells", authorById.getAuthorName());
    }
}
