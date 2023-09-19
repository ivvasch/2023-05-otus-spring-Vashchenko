package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.model.Author;
import ru.otus.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("При работе с authorService,")
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private BookServiceImpl bookService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("255be70b-a76a-4eda-8892-b8e929780bfb", "Herbert George Wells");
    }

    @Test
    @DisplayName("автор записывается в базу корректно")
    void shouldSaveAuthorCorrect() {
        Author saveResult = authorService.save(author);
        System.out.println("authorService ==>-------------------------------------");
        assertEquals(author.getId(), saveResult.getId());
        assertEquals(author.getName(), saveResult.getName());
        System.out.println("shouldSaveAuthorCorrect ========================================");
    }

    @Test
    @DisplayName("автор удаляется из базы, только если нет в базе его книг")
    void shouldDeleteAuthorByIdIfHisBooksIsNullOrNotDeleteIfHisBooksPresent() {
        boolean isDeleteById = authorService.deleteById("cc02c80f-de17-4d98-94b6-994670a08ce7");
        System.out.println("authorService ==>-------------------------------------");
        List<Book> allBookByAuthorId = bookService.findAllBookByAuthorId("cc02c80f-de17-4d98-94b6-994670a08ce7");
        System.out.println("authorService ==>-------------------------------------");
        if (allBookByAuthorId.isEmpty()) {
            assertTrue(isDeleteById);
        } else {
            assertFalse(isDeleteById);
        }
        System.out.println("shouldDeleteAuthorByIdIfHisBooksIsNullOrNotDeleteIfHisBooksPresent ========================================");
    }

    @Test
    @DisplayName("автор находится в базе по его id")
    void shouldFindAuthorByIdService() {
        authorService.save(author);
        System.out.println("authorService ==>-------------------------------------");
        Author authorById = authorService.findById("255be70b-a76a-4eda-8892-b8e929780bfb");
        System.out.println("authorService ==>-------------------------------------");
        assertEquals("Herbert George Wells", authorById.getName());
        System.out.println("shouldFindAuthorByIdService ========================================");
    }

    @Test
    @DisplayName("имя автора обновляется в базе")
    void shouldUpdateAuthorNameService() {
        authorService.save(author);
        System.out.println("authorService ==>-------------------------------------");
        String newName = "H.G. Wells";
        author = new Author("255be70b-a76a-4eda-8892-b8e929780bfb", newName);
        authorService.update(author);
        System.out.println("authorService ==>-------------------------------------");
        Author authorById = authorService.findById("255be70b-a76a-4eda-8892-b8e929780bfb");
        System.out.println("authorService ==>-------------------------------------");
        assertEquals("H.G. Wells", authorById.getName());
        System.out.println("shouldUpdateAuthorNameService ========================================");
    }
}
