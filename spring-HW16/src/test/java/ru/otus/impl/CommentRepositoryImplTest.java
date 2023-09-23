package ru.otus.impl;

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
@DisplayName("При работе с commentRepository")
class CommentRepositoryImplTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Comment comment2;

    private Book book;

    private List<Comment> comments;

    @BeforeEach
    void setUp() {
        Comment comment = new Comment();
        comment.setComment("Дитя пещеры неплохая книжка...");
        Author author = new Author("1", "name");
        authorRepository.save(author);
        Genre genre = new Genre("1", "name");
        genreRepository.save(genre);
        comments = new ArrayList<>();
        comment2 = new Comment();
        comment2.setComment("Хорошая книжка...");
        comments.add(comment);
        comments.add(comment2);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        book = new Book("ce74a4d0-7ff7-4425-b9c2-9aff4240d51d", "GoodBook", genre, author, comments);
    }

    @Test
    @DisplayName("сохраняет все комментарии к книге и находит их")
    void findAllCommentsByBookId() {
        Book saved = bookRepository.save(book);
        Optional<Book> byId = bookRepository.findById(saved.getId());
        assertEquals(2, byId.orElse(new Book()).getComments().size());
        Comment comment3 = new Comment();
        comment2.setComment("Очень хорошая книжка...");
        comments.add(comment3);
        commentRepository.save(comment3);
        Book savedTo = bookRepository.save(book);
        assertEquals(3, savedTo.getComments().size());
    }

    @Test
    @DisplayName("находит комментарий по id")
    void findById() {
        Book saved = bookRepository.save(book);
        Optional<Comment> byId = commentRepository.findById(saved.getComments().get(0).getCommentId());
        assertEquals(saved.getComments().get(0), byId.orElse(null));
        assertTrue(saved.getComments().get(0).equals(byId.orElse(null)));
    }

    @Test
    @DisplayName("корректно удаляет комментарий по id")
    void deleteById() {
        Book saved = bookRepository.save(book);
        commentRepository.deleteById(saved.getComments().get(0).getCommentId());
        Optional<Comment> byId = commentRepository.findById(saved.getComments().get(0).getCommentId());
        assertTrue(byId.isEmpty());
    }
}
