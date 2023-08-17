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
                new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "fantastic")
                , new Author("cbe4d2c7-f61f-4e2f-a582-b10dcdf6d1a8", "Kir Bulichev")
                , comments);
    }

    @Test
    @DisplayName("находятся все комментарии по id книги")
    @Transactional
    void findAllCommentsByBookId() {
        bookService.saveBook(book);
        List<Comment> allCommentsByBookId = commentService.findAllCommentsByBookId(book.getId());
        assertEquals(2, allCommentsByBookId.size());
    }

    @Test
    @DisplayName("находится комментарий по его id")
    @Transactional
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
    @Transactional
    void deleteById() {
        List<Comment> comments = new ArrayList<>();
        Comment comm = new Comment();
        comm.setComment("А хочешь «Жигули»? Ты представляешь — такой маленький, а уже «Жигули»!..");
        comments.add(comm);
        Book fantasticBook = new Book("ebf2c099-1f7b-4c80-8c35-0853723bef3c", "100 years ago",
                new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "fantastic")
                , new Author("cbe4d2c7-f61f-4e2f-a582-b10dcdf6d1a8", "Kir Bulichev")
                , comments);
        bookService.saveBook(fantasticBook);
        Book bookById = bookService.findBookById(fantasticBook.getId());
        commentService.deleteById(bookById.getComments().get(0).getCommentId());
        assertTrue(commentService.findById(bookById.getComments().get(0).getCommentId()).isEmpty());
    }
}
