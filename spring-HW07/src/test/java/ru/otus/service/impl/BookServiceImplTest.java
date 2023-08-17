package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("При работе с bookService,")
class BookServiceImplTest {
    @Autowired
    private BookService bookService;
    private Book book;
    List<Comment> comments;

    @BeforeEach
    void setUp() {
        comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setComment("Дитя пещеры неплохая книжка...");
        Comment comment2 = new Comment();
        comment2.setComment("Дитя пещеры хорошая книжка...");
        comments.add(comment);
        comments.add(comment2);
        book = new Book("52a823d7-684a-43c6-8485-cdda3141e09c", "The Child of the Cavern",
                new Genre("defd5e0b-1af4-45c0-a68a-1ea5a21296cf", "Adventure")
                , new Author("fbddd6ac-f4dd-4a39-804d-03d45d30241b", "Jules Verne")
                , comments);
    }

    @Test
    @DisplayName("получаем по id жанра корректно заполненный список книг")
    @Transactional
    void shouldFindAllBookByGenreId() {
        Book savedBook = bookService.saveBook(book);
        Book bookById = bookService.findBookById(book.getId());
        assertEquals(savedBook, bookById);
        List<Book> allBookByGenreId = bookService.findAllBookByGenreId("defd5e0b-1af4-45c0-a68a-1ea5a21296cf");
        System.out.println("bookService ==> -------------------------------------");
        assertEquals(14, allBookByGenreId.size());
        assertNotNull(allBookByGenreId.get(0).getGenre());
        assertNotNull(allBookByGenreId.get(0).getAuthor());
        System.out.println("shouldFindAllBookByGenreId ========================================");
    }

    @Test
    @DisplayName("новая книга сохраняется корректно с новым автором, новым жанром и комментарием")
    void shouldSaveBook() {
        Author rayBradbury = new Author("46a66c5d-4b74-47ce-90c5-1c4eee73c80f", "Ray Bradbury");
        Genre novel = new Genre("e535b857-72e5-4d4e-9e65-e98201bc603d", "Novel");
        Book bookForSave = new Book("ead42caa-d12a-4cb8-9e63-0896e911190b", "The Martian Chronicles ", novel, rayBradbury, comments);
        Book savedBookResult = bookService.saveBook(bookForSave);
        Book findBook = bookService.findBookById(savedBookResult.getId());
        System.out.println("bookService ==> -------------------------------------");
        assertEquals(savedBookResult.getId(), findBook.getId());
        assertEquals(savedBookResult.getTitle(), findBook.getTitle());
        assertEquals(savedBookResult.getAuthor().getId(), findBook.getAuthor().getId());
        assertEquals(savedBookResult.getAuthor().getAuthorName(), findBook.getAuthor().getAuthorName());
        assertEquals(savedBookResult.getGenre().getId(), findBook.getGenre().getId());
        assertEquals(savedBookResult.getGenre().getGenreName(), findBook.getGenre().getGenreName());
        assertEquals(savedBookResult.getComments().size(), findBook.getComments().size());
        System.out.println("bookService ==> -------------------------------------");
        System.out.println("shouldSaveBook ========================================");
    }

    @Test
    @DisplayName("новая книга обновляется корректно с новым автором, новым жанром и новым названием")
    void updateBook() {
        Author dahl = new Author("eb50cd1b-397a-4f5e-8877-708718097438", "Roald Dahl");
        Genre shortStory = new Genre("e01b9514-b49f-45fb-9e50-8b04cf701167", "short-story");
        Book bookForSave = new Book("4f63d4ec-8ed4-46ce-bb27-51270829b21e", "The Witches", shortStory, dahl, comments);
        bookService.saveBook(bookForSave);
        System.out.println("bookService ==> -------------------------------------");
        Author halls = new Author("b62bb71e-885e-4f2c-be09-2eef376ec18a", "Stacey Halls");
        Genre novel = new Genre("e535b857-72e5-4d4e-9e65-e98201bc603d", "Novel");
        Book newBookForSave = new Book("4f63d4ec-8ed4-46ce-bb27-51270829b21e", "The Familiars", novel, halls, new ArrayList<>());
        bookService.saveBook(newBookForSave);
        System.out.println("bookService ==> -------------------------------------");
        Book updatedBookById = bookService.findBookById("4f63d4ec-8ed4-46ce-bb27-51270829b21e");
        System.out.println("bookService ==> -------------------------------------");
        assertEquals("Stacey Halls", updatedBookById.getAuthor().getAuthorName());
        assertEquals("e535b857-72e5-4d4e-9e65-e98201bc603d", updatedBookById.getGenre().getId());
        assertEquals("The Familiars", updatedBookById.getTitle());
        assertTrue(updatedBookById.getComments().isEmpty());
        System.out.println("updateBook ========================================");
    }

    @Test
    @DisplayName("книга удаляется из базы")
    void shouldDeleteBookById() {
        bookService.saveBook(book);
        System.out.println("bookService ==> -------------------------------------");
        Book bookById = bookService.findBookById(book.getId());
        System.out.println("bookService ==> -------------------------------------");
        assertEquals(book.getId(), bookById.getId());
        boolean isDeletedById = bookService.deleteBookById(bookById.getId());
        System.out.println("bookService ==> -------------------------------------");
        assertTrue(isDeletedById);
        System.out.println("shouldDeleteBookById ========================================");
    }

    @Test
    @DisplayName("находим все книги в базе по этому автору")
    void findAllBookByAuthorId() {
        List<Book> allBookByAuthorId = bookService.findAllBookByAuthorId("c20c2f9b-89c7-449e-a78c-63fb1fdb26c3");
        System.out.println("bookService ==> -------------------------------------");
        assertEquals(5, allBookByAuthorId.size());
        System.out.println("findAllBookByAuthorId ========================================");
    }

    @Test
    @DisplayName("находим все книги в базе по этому жанру")
    void findAllBookByGenreId() {
        List<Book> allBookByGenreId = bookService.findAllBookByGenreId("defd5e0b-1af4-45c0-a68a-1ea5a21296cf");
        System.out.println("bookService ==> -------------------------------------");
        assertEquals(13, allBookByGenreId.size());
        System.out.println("findAllBookByGenreId ========================================");

    }
}
