package ru.otus.springhw05.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.springhw05.model.Author;
import ru.otus.springhw05.model.Book;
import ru.otus.springhw05.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("При работе с bookService,")
class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("52a823d7-684a-43c6-8485-cdda3141e09c", "The Child of the Cavern",
                new Genre("defd5e0b-1af4-45c0-a68a-1ea5a21296cf", "Adventure")
                , new Author("fbddd6ac-f4dd-4a39-804d-03d45d30241b", "Jules Verne"));
    }

    @Test
    @DisplayName("получаем по id корректно заполненный список книг")
    void shouldFindAllBookByGenreId() {
        List<Book> allBookByGenreId = bookService.findAllBookByGenreId("defd5e0b-1af4-45c0-a68a-1ea5a21296cf");
        assertEquals(13, allBookByGenreId.size());
        assertNotNull(allBookByGenreId.get(0).getGenre());
        assertNotNull(allBookByGenreId.get(0).getAuthor());

    }

    @Test
    @DisplayName("новая книга сохраняется корректно с новым автором и новым жанром")
    void shouldSaveBook() {
        Author rayBradbury = new Author("46a66c5d-4b74-47ce-90c5-1c4eee73c80f", "Ray Bradbury");
        Genre novel = new Genre("e535b857-72e5-4d4e-9e65-e98201bc603d", "Novel");
        Book bookForSave = new Book("ead42caa-d12a-4cb8-9e63-0896e911190b", "The Martian Chronicles ", novel, rayBradbury);
        Integer savedBookResult = bookService.saveBook(bookForSave);
        assertEquals(1, savedBookResult);
        Book bookById = bookService.findBookById("ead42caa-d12a-4cb8-9e63-0896e911190b");
        assertEquals(bookForSave, bookById);
    }

    @Test
    @DisplayName("новая книга обновляется корректно с новым автором, новым жанром и новым названием")
    void updateBook() {
        Author dahl = new Author("eb50cd1b-397a-4f5e-8877-708718097438", "Roald Dahl");
        Genre shortStory = new Genre("e01b9514-b49f-45fb-9e50-8b04cf701167", "short-story");
        Book bookForSave = new Book("4f63d4ec-8ed4-46ce-bb27-51270829b21e", "The Witches", shortStory, dahl);
        bookService.saveBook(bookForSave);
        Author halls = new Author("b62bb71e-885e-4f2c-be09-2eef376ec18a", "Stacey Halls");
        Genre novel = new Genre("e535b857-72e5-4d4e-9e65-e98201bc603d", "Novel");
        Book newBookForSave = new Book("4f63d4ec-8ed4-46ce-bb27-51270829b21e", "The Familiars", novel, halls);
        bookService.updateBook(newBookForSave);
        Book updatedBookById = bookService.findBookById("4f63d4ec-8ed4-46ce-bb27-51270829b21e");
        assertEquals("Stacey Halls", updatedBookById.getAuthor().getAuthorName());
        assertEquals("e535b857-72e5-4d4e-9e65-e98201bc603d", updatedBookById.getGenre().getId());
        assertEquals("The Familiars", updatedBookById.getTitle());
    }

    @Test
    @DisplayName("книга удаляется из базы")
    void shouldDeleteBookById() {
        bookService.saveBook(book);
        Book bookById = bookService.findBookById(book.getId());
        assertEquals(book.getId(), bookById.getId());
        boolean isDeletedById = bookService.deleteBookById(bookById.getId());
        assertTrue(isDeletedById);
    }

    @Test
    @DisplayName("находим все книги в базе по этому автору")
    void findAllBookByAuthorId() {
        List<Book> allBookByAuthorId = bookService.findAllBookByAuthorId("c20c2f9b-89c7-449e-a78c-63fb1fdb26c3");
        assertEquals(5, allBookByAuthorId.size());

    }
}
