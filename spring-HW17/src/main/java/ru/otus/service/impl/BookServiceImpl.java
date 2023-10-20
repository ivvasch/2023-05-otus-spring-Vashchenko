package ru.otus.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LogManager.getLogger(BookServiceImpl.class);

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
        log.info("Method saveBook got book with title {}, author {}, genre {} for save.",
                book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());
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
        log.info("Method findBookById got book id {} for search book.", bookId);
        Optional<Book> byId;
            byId = bookRepository.findById(bookId);
            byId.ifPresent(book -> book.getComments().size());
        return byId.orElse(null);
    }

    @Override
    public List<Book> findAllBookByGenreId(String genreId) {
        log.info("Method findAllBookByGenreId got genre id {} for search all books in these genre.", genreId);
        return bookRepository.findBooksByGenreId(genreId);
    }

    @Override
    @Transactional
    public boolean updateBook(Book book) {
        log.info("Method updateBook got book with id {} for update book.", book.getId());
        return saveBook(book) != null;
    }

    @Override
    @Transactional
    public boolean deleteBookById(String bookId) {
        log.info("Method deleteBookById got book id {} for delete book.", bookId);
        if (findBookById(bookId) != null) {
            bookRepository.deleteBookById(bookId);
        }
        return findBookById(bookId) == null;
    }

    @Override
    public List<Book> findAllBookByAuthorId(String authorId) {
        log.info("Method findAllBookByAuthorId got author id {} for find all books.", authorId);
        return bookRepository.findBooksByAuthorId(authorId);
    }

    @Override
    public List<Book> findAll() {
        log.info("Method findAll -> search all books.");
        List<Book> all = bookRepository.findAll();
        return all;
    }

    @Override
    public Page<Book> findPaginated(Pageable pageable) {
        log.info("Method findPaginated ");
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startItem = pageNumber * pageSize;
        List<Book> books = findAll();
        List<Book> list;
        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }
        Page<Book> bookPage = new PageImpl<>(list, PageRequest.of(pageNumber, pageSize), books.size());
        return bookPage;
    }

    @Override
    public void deleteCommentById(String commentId) {
        log.info("Method deleteCommentById got comment id {} for delete it.", commentId);
        Book bookByCommentId = bookRepository.findBookByCommentId(commentId);
        List<Comment> comments = bookByCommentId.getComments().stream()
                .filter(comm -> !comm.getCommentId().equals(commentId.toLowerCase()))
                .toList();
        bookByCommentId.setComments(comments);
        bookRepository.save(bookByCommentId);
    }
}
