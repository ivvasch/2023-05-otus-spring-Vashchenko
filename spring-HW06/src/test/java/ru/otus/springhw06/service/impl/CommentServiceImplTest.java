package ru.otus.springhw06.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.springhw06.model.Author;
import ru.otus.springhw06.model.Book;
import ru.otus.springhw06.model.Comment;
import ru.otus.springhw06.model.Genre;
import ru.otus.springhw06.repository.impl.AuthorRepositoryImpl;
import ru.otus.springhw06.repository.impl.BookRepositoryImpl;
import ru.otus.springhw06.repository.impl.CommentRepositoryImpl;
import ru.otus.springhw06.repository.impl.GenreRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("При работе с commentService ")
@Import({CommentRepositoryImpl.class,
        CommentServiceImpl.class,
        BookServiceImpl.class,
        GenreServiceImpl.class,
        BookRepositoryImpl.class,
        AuthorServiceImpl.class,
        GenreRepositoryImpl.class,
        AuthorRepositoryImpl.class})
class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private BookServiceImpl bookService;
    private Book book;

    @BeforeEach
    void setUp() {
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setComment("Когда она... Состязание роботов гитаристов, гитаристов");
        Comment newComment = new Comment();
        newComment.setComment("Ты кефир местный пробовал?...");
        comments.add(comment);
        comments.add(newComment);
        book = new Book("ebf2c099-1f7b-4c80-8c35-0853723bef3c", "100 years ago",
                new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "fantastic", new ArrayList<>())
                , new Author("cbe4d2c7-f61f-4e2f-a582-b10dcdf6d1a8", "Kir Bulichev", new ArrayList<>())
                , comments);
    }

    @Test
    @DisplayName("находятся все комментарии по id книги")
    void findAllCommentsByBookId() {
        bookService.saveBook(book);
        List<Comment> allCommentsByBookId = commentService.findAllCommentsByBookId(book.getId());
        assertEquals(2, allCommentsByBookId.size());
    }

    @Test
    @DisplayName("находится комментарий по его id")
    void findById() {
        Comment comm = new Comment();
        comm.setComment("Третий - Коля Герасимов. Спортсмен, закаляется, зимой на балконе в спальном мешке спал, потом три недели бронхитом болел.");
        book.getComments().add(comm);
        Book savedBook = bookService.saveBook(book);
        Optional<Comment> byId = commentService.findById(savedBook.getComments().get(2).getCommentId());
        assertEquals(comm, byId.orElse(null));
    }

    @Test
    @DisplayName("удаляется комментарий из базы по его id")
    void deleteById() {
        List<Comment> comments = new ArrayList<>();
        Comment comm = new Comment();
        comm.setComment("А хочешь «Жигули»? Ты представляешь — такой маленький, а уже «Жигули»!..");
        comments.add(comm);
        Book fantasticBook = new Book("ebf2c099-1f7b-4c80-8c35-0853723bef3c", "100 years ago",
                new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "fantastic", new ArrayList<>())
                , new Author("cbe4d2c7-f61f-4e2f-a582-b10dcdf6d1a8", "Kir Bulichev", new ArrayList<>())
                , comments);
        bookService.saveBook(fantasticBook);
        Book bookById = bookService.findBookById(fantasticBook.getId());
        commentService.deleteById(bookById.getComments().get(0).getCommentId());
        assertTrue(commentService.findById(bookById.getComments().get(0).getCommentId()).isEmpty());
    }
}
