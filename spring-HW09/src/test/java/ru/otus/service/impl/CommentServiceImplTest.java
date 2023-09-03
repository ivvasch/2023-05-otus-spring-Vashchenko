package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("При работе с commentService ")
class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    private Book book;

    private ArrayList<Comment> comments;

    @BeforeEach
    void setUp() {
        Comment comment = new Comment();
        comment.setComment("Когда она... Состязание роботов гитаристов, гитаристов");
        commentService.save(comment);
        Comment newComment = new Comment();
        newComment.setComment("Ты кефир местный пробовал?...");
        commentService.save(comment);
        Genre fantastic = new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "fantastic");
        genreService.save(fantastic);
        Author author = new Author("cbe4d2c7-f61f-4e2f-a582-b10dcdf6d1a8", "Kir Bulichev");
        authorService.save(author);
        comments = new ArrayList<>();
        comments.add(comment);
        comments.add(newComment);
        book = new Book("100 years ago", author, fantastic);
        book.setComments(comments);
    }

    @Test
    @DisplayName("находятся все комментарии по id книги")
    void findAllCommentsByBookId() {
        Book saved = bookService.saveBook(book);
        Book bookById = bookService.findBookById(saved.getId());
        bookById.getComments().forEach(comment -> {
            comment.setBook(saved);
            commentService.save(comment);
        });
        bookService.saveBook(bookById);
        List<Comment> allCommentsByBookId = commentService.findAllCommentsByBookId(saved.getId());
        assertEquals(2, allCommentsByBookId.size());
    }

    @Test
    @DisplayName("находится комментарий по его id")
    void findById() {
        Author author = new Author();
        author.setName("Engels");
        Genre genre = new Genre();
        genre.setName("social");
        Book newBook = new Book("Анти-Дюринг", author, genre);
        Comment comm = new Comment();
        comm.setComment("Вечность во времени, бесконечность в пространстве");
        newBook.getComments().add(comm);
        Comment saved = commentService.save(comm);
        Book savedBook = bookService.saveBook(newBook);
        Optional<Comment> byId = commentService.findById(savedBook.getComments().get(0).getCommentId());
        Comment actual = byId.orElse(null);
        assertEquals(comm.getComment(), actual != null ? actual.getComment() : null);
    }

    @Test
    @DisplayName("удаляется комментарий из базы по его id")
    void deleteById() {
        Author author = new Author();
        author.setName("Marks");
        Genre genre = new Genre();
        genre.setName("social");
        Book newBook = new Book("Capital", author, genre);
        Comment comm = new Comment();
        comm.setComment("Не сознание людей определяет их бытие, а, наоборот, их общественное бытие определяет их сознание.");
        newBook.getComments().add(comm);
        Comment saved = commentService.save(comm);
        bookService.saveBook(newBook);
        commentService.deleteById(saved.getCommentId());
        assertTrue(commentService.findById(saved.getCommentId()).isEmpty());
    }
}
