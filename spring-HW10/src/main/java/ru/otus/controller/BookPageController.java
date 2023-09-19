package ru.otus.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.dto.CommentDto;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class BookPageController {

    private final BookService bookService;
    private final CommentService commentService;

    public BookPageController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }


    @GetMapping()
    public String mainPage() {
        return "main";
    }
    @GetMapping("/bookpage/{id}")
    public String findBookById(@PathVariable("id") String id, Model model) {
        Book bookById = bookService.findBookById(id);
        if (bookById == null) {
            return "notFound";
        }
        model.addAttribute("book", bookById);
        return "bookPage";
    }
    @GetMapping("/editbook/{id}")
    public String editBookById(@PathVariable("id") String id, Model model) {
        Book bookById = bookService.findBookById(id);
        model.addAttribute("book", bookById);
        return "editBook";
    }
    @PostMapping("/update")
    public String updateBookById(Book book) {
        bookService.updateBook(book);
        return "redirect:/library";
    }
    @GetMapping("/add")
    public String getFormForAddBook() {
        return "addBook";
    }
    @PostMapping("/add")
    public String addBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/library";
    }

    @GetMapping("/addcomment/{bookId}")
    public String addCommentByBookIdPage(@PathVariable("bookId") String id, Model model) {
        Book bookById = bookService.findBookById(id);
        model.addAttribute("book", bookById);
        return "addComment";
    }
    @GetMapping("/deletecomment/{commentId}/{bookId}")
    public String deleteCommentById(@PathVariable("commentId") String commentId, @PathVariable("bookId") String bookId) {
        Book bookById = bookService.findBookById(bookId);
        List<Comment> comments = bookById.getComments().stream().filter(comment ->
                !comment.getCommentId().equals(commentId)).toList();
        bookById.setComments(comments);
        commentService.deleteById(commentId);
        bookService.saveBook(bookById);
        return "redirect:/bookpage/" + bookId;
    }
    @GetMapping("/deletebook/{bookId}")
    public String deleteBookById(@PathVariable("bookId") String bookId) {
        Book bookById = bookService.findBookById(bookId);
        bookById.getComments().forEach(comment -> commentService.deleteById(comment.getCommentId()));
        bookService.deleteBookById(bookId);
        return "redirect:/library";
    }

    @PostMapping("/comment")
    public String addCommentByBookId(CommentDto commentDto) {
        Book bookById = bookService.findBookById(commentDto.getId());
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setBook(bookById);
        bookById.getComments().add(comment);
        bookService.saveBook(bookById);
        return "redirect:/library";
    }

    @GetMapping("/library")
    public String getLibrary() {
        log.info("Get all books in library");
        return "mainPage";
    }
}
