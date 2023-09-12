package ru.otus.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("При работке с bookRepository")
class BookRepositoryImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    private List<Comment> comments;

    private Book book;

    @BeforeEach
    void setUp() {
        comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setComment("Дитя пещеры неплохая книжка...");
        commentRepository.save(comment);
        Comment comment2 = new Comment();
        comment2.setComment("Дитя пещеры хорошая книжка...");
        commentRepository.save(comment2);
        comments.add(comment2);
        comments.add(comment);
        Genre adventure = new Genre("defd5e0b-1af4-45c0-a68a-1ea5a21296cf", "Adventure");
        genreRepository.save(adventure);
        Author author = new Author("85cf8f06-1625-4d07-91d1-585760bc8749", "H. G. Wells");
        authorRepository.save(author);
        book = new Book("c5ae6e19-9f3a-4ecb-a6b9-6ba810cfbcd3", "When the Sleeper Wakes",
                adventure
                , author
                , comments);
    }

    @Test
    @DisplayName("книга сохраняется и обновляется корректно")
    void save() {
        Book saved = bookRepository.save(book);
        assertEquals(book.getTitle(), saved.getTitle());
        assertEquals(book.getComments().size(), saved.getComments().size());
        assertEquals(book.getAuthor().getName(), saved.getAuthor().getName());
        assertEquals(book.getGenre().getName(), saved.getGenre().getName());
        Comment comm = new Comment();
        comm.setComment("Замечательный роман...");
        comments.add(comm);
        commentRepository.save(comm);
        saved.setComments(comments);
        Book updated = bookRepository.save(saved);
        assertEquals(saved.getTitle(), updated.getTitle());
        assertEquals(saved.getComments().size(), updated.getComments().size());
        assertEquals(saved.getAuthor().getName(), updated.getAuthor().getName());
        assertEquals(saved.getGenre().getName(), updated.getGenre().getName());

    }

    @Test
    @DisplayName("книга находится по id")
    void findById() {
        bookRepository.save(book);
        Optional<Book> byId = bookRepository.findById(book.getId());
        Book actualBook = byId.orElse(null);
        assertEquals(book.getTitle(), actualBook.getTitle());
        assertEquals(book.getComments().size(), actualBook.getComments().size());
        assertEquals(book.getAuthor().getName(), actualBook.getAuthor().getName());
        assertEquals(book.getGenre().getName(), actualBook.getGenre().getName());
    }

    @Test
    @DisplayName("находятся все книги по id жанра")
    void findBooksByGenreId() {
        List<Book> booksByGenreId = bookRepository.findBooksByGenreId("b67847da-c4fa-44f0-a4cf-9d53fbddfb96");
        assertEquals(2, booksByGenreId.size());
    }

    @Test
    @DisplayName("получаем список книг корректно")
    void findAll() {
        List<Book> books = bookRepository.findAll();
        assertEquals(16, books.size());
    }

    @Test
    @DisplayName("корректно получаем список книг автора")
    void findBooksByAuthorId() {
        List<Book> booksByAuthorId = bookRepository.findBooksByAuthorId("e07e6aab-d89f-4f85-b453-efccaaff0975");
        assertEquals(5, booksByAuthorId.size());
    }

    @Test
    @DisplayName("книга корректно удаляется из базы")
    void deleteBookById() {
        Book saved = bookRepository.save(book);
        Optional<Book> byId = bookRepository.findById(saved.getId());
        assertEquals(saved, byId.orElse(null));
        bookRepository.deleteBookById(book.getId());
        Optional<Book> byIdAfterDelete = bookRepository.findById(saved.getId());
        assertTrue(byIdAfterDelete.isEmpty());
    }
}
