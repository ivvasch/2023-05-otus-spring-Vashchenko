package ru.otus.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.dto.CommentDto;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class BookController {

    private static final Logger log = LogManager.getLogger(BookController.class);
    private final BookService bookService;
    private final CommentService commentService;

    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }


    @GetMapping()
    public String mainPage() {
        log.info("Main page started");
        return "main";
    }
    @GetMapping("/bookpage/{id}")
    public String findBookById(@PathVariable("id") String id, Model model) {
        log.info("Method findBookById got id {} of book", id);
        Book bookById = bookService.findBookById(id);
        if (bookById == null) {
            return "notFound";
        }
        model.addAttribute("book", bookById);
        return "bookPage";
    }
    @GetMapping("/editbook/{id}")
    public String editBookById(@PathVariable("id") String id, Model model) {
        log.info("Method editBookById got id {} of book", id);
        Book bookById = bookService.findBookById(id);
        model.addAttribute("book", bookById);
        return "editBook";
    }
    @PostMapping("/update")
    public String updateBookById(Book book) {
        log.info("Method updateBookById got book with id {}", book.getId());
        bookService.updateBook(book);
        return "redirect:/library";
    }
    @GetMapping("/add")
    public String getFormForAddBook() {
        log.info("Method getFormForAddBook started");
        return "addBook";
    }
    @PostMapping("/add")
    public String addBook(Book book) {
        log.info("Method addBook got book with title {} for add", book.getTitle());
        bookService.saveBook(book);
        return "redirect:/library";
    }

    @GetMapping("/addcomment/{bookId}")
    public String addCommentByBookIdPage(@PathVariable("bookId") String id, Model model) {
        log.info("Method addCommentByBookIdPage got id {} of book", id);
        Book bookById = bookService.findBookById(id);
        model.addAttribute("book", bookById);
        return "addComment";
    }
    @GetMapping("/deletecomment/{commentId}/{bookId}")
    public String deleteCommentById(@PathVariable("commentId") String commentId, @PathVariable("bookId") String bookId) {
        log.info("Method deleteCommentById got id {} of comment for delete on book with id {}", commentId, bookId);
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
        log.info("Method deleteBookById got id {} of book", bookId);
        Book bookById = bookService.findBookById(bookId);
        bookById.getComments().forEach(comment -> commentService.deleteById(comment.getCommentId()));
        bookService.deleteBookById(bookId);
        return "redirect:/library";
    }

    @PostMapping("/comment")
    public String addCommentByBookId(CommentDto commentDto) {
        log.info("Method addCommentByBookId started to add comment");
        Book bookById = bookService.findBookById(commentDto.getId());
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setBook(bookById);
        bookById.getComments().add(comment);
        bookService.saveBook(bookById);
        return "redirect:/library";
    }

    @GetMapping("/library")
    public String getLibrary(@RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size, Model model) {
        log.info("Get all books in library");
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        Page<Book> bookPage = bookService.findPaginated(PageRequest.of(currentPage, pageSize));
        model.addAttribute("bookPage", bookPage);

        return "mainPage";
    }
}
