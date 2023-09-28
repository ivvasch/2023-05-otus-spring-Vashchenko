package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.model.dto.BookDTO;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Book saveBook(Book book) {
        Optional<Book> bookByName = bookRepository.findBookByName(book.getTitle());
        Book findBook = bookByName.orElse(null);
        if (findBook != null && findBook.equals(book)) {
            return book;
        }
        if (book.getAuthor() != null) {
            Author authorByName = authorRepository.findByName(book.getAuthor().getName());
            if (authorByName == null) {
                authorRepository.save(book.getAuthor());
            } else {
                book.setAuthor(authorByName);
            }
        }
        if (book.getGenre() != null) {
            Genre genreByName = genreRepository.findByName(book.getGenre().getName());
            if (genreByName == null) {
                genreRepository.save(book.getGenre());
            } else {
                book.setGenre(genreByName);
            }
        }
        if (!book.getComments().isEmpty()) {
            List<Comment> notSavedComments = book.getComments().stream().filter(comment -> comment.getCommentId() == null).toList();
            if (!notSavedComments.isEmpty()) {
                notSavedComments.forEach(commentRepository::save);
            }
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book findBookById(String bookId) {
        Optional<Book> byId;
            byId = bookRepository.findById(bookId);
            byId.ifPresent(book -> book.getComments().size());
        return byId.orElse(null);
    }

    @Override
    public List<Book> findAllBookByGenreId(String genreId) {
        return bookRepository.findBooksByGenreId(genreId);
    }

    @Override
    @Transactional
    public boolean updateBook(Book book) {
        return saveBook(book) != null;
    }

    @Override
    @Transactional
    public boolean deleteBookById(String bookId) {
        if (findBookById(bookId) != null) {
            bookRepository.deleteBookById(bookId);
        }
        return findBookById(bookId) == null;
    }

    @Override
    public List<Book> findAllBookByAuthorId(String authorId) {
        return bookRepository.findBooksByAuthorId(authorId);
    }

    @Override
    public List<BookDTO> findAll() {
        List<Book> all = bookRepository.findAll();
        List<BookDTO> bookDTOList = all.stream().map(BookDTO::toDto).toList();
        return bookDTOList;
    }

    @Override
    public void deleteCommentById(String commentId) {
        Book bookByCommentId = bookRepository.findBookByCommentId(commentId);
        List<Comment> comments = bookByCommentId.getComments().stream()
                .filter(comm -> !comm.getCommentId().equals(commentId.toLowerCase()))
                .toList();
        bookByCommentId.setComments(comments);
        bookRepository.save(bookByCommentId);
    }
}
