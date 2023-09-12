package ru.otus.ui;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class UIShellInterface {

    private String userName;
    private Book book;
    private final BookService bookService;
    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private static final String USER_NOT_LOGIN = "Book with id %s wasn't %s because you are not login";
    private static final String USER_LOGIN = "Book %s was successfully %s";
    private static final String SMTH_WRONG = "Something wrong when we want to %s Book with title %s";
    private static final String NOT_FIND = "We didn't find your book with id %s";
    private static final String NOT_FIND_COMMENT = "We didn't find comment with id %s";
    private static final String SAVED = "saved";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String FOUND = "found";
    private static final String NULL_BOOK = "Null_book";
    private static final String NULL_COMMENT = "Null_comment";


    public UIShellInterface(BookService bookService, CommentService commentService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption String username) {
        this.userName = username;
        return String.format("""
                Hello %s. you can configure your book in next format:
                add book:                       'add <title>space<genreId>space<genreName>space<authorId>space<authorName>'
                delete book:                    'delete <bookId>'
                find book:                      'find <bookId>'
                find all books:                 'findAll'
                find all books by genre id:     'findAllByGenreID <genreId>'
                update book:                    'update <bookId>space<title>space<genreId or null>space<genreName or null>space<authorId or null>space<authorName or null>'
                add comment to book:            'addComment <bookId>space<text of comment>'
                delete comment from book:       'delComment <commentId>'
                update comment:                 'apComment <commentId>space<new text of comment>'
                find comment:                   'findComment <commentId>'
                find all comments by book id:   'findAllComments <bookId>'
                """, username);
    }

    @ShellMethod(value = "Add Book to library", key = {"add", "addBook"})
    public String addBook(@ShellOption String title, @ShellOption String genreId,
                          @ShellOption String genreName, @ShellOption String authorId, @ShellOption String authorName) {
        String result = String.format(USER_NOT_LOGIN, title, SAVED);
        if (isAvailable()) {
            if (!StringUtils.isBlank(title)
                    && !StringUtils.isBlank(genreId) && !StringUtils.isBlank(genreName)
                    && !StringUtils.isBlank(authorId) && !StringUtils.isBlank(authorName)) {
                Genre genre = new Genre();
                genre.setId(genreId);
                genre.setName(genreName);
                Author author = new Author();
                author.setId(authorId);
                author.setName(authorName);
                this.book = new Book(title, author, genre);
                Book savedBook = bookService.saveBook(book);
                Book bookById = bookService.findBookById(savedBook.getId());
                result = getResult(title, bookById != null, SAVED);
            }
        }
        return result;
    }

    @ShellMethod(value = "Delete the book from library", key = {"del", "delete"})
    public String deleteBook(@ShellOption String bookId) {
        String result = String.format(USER_NOT_LOGIN, bookId, DELETE);
        if (isAvailable()) {
            Book bookById = bookService.findBookById(bookId);
            if (bookById == null) {
                result = String.format(NOT_FIND, bookId);
            } else {
                boolean deleteById = bookService.deleteBookById(bookId);
                result = getResult(bookId, deleteById, DELETE);
            }
        }
        return result;
    }

    @ShellMethod(value = "Update the book from library", key = {"upd", "update"})
    public String updateBook(@ShellOption String bookId, @ShellOption String title, @ShellOption String genreId,
                             @ShellOption String genreName, @ShellOption String authorId, @ShellOption String authorName) {
        String result = String.format(USER_NOT_LOGIN, bookId, UPDATE);
        if (isAvailable()) {
            Book bookById = bookService.findBookById(bookId);
            if (bookById != null) {
                if (!StringUtils.isBlank(title) && !bookById.getTitle().equals(title)) {
                    bookById.setTitle(title);
                }
                createGenreForUpdate(bookById, genreId, genreName);
                createAuthorForUpdate(bookById, authorId, authorName);
                boolean isUpdateBook = bookService.updateBook(bookById);
                result = getResult(bookId, isUpdateBook, UPDATE);
            }
        }
        return result;
    }

    @ShellMethod(value = "Find book by id from library", key = {"fnd", "find"})
    public String findBook(@ShellOption String bookId) {
        String result = String.format(USER_NOT_LOGIN, bookId, "find");
        if (isAvailable()) {
            Book bookById = bookService.findBookById(bookId);
            if (bookById != null) {
                result = getResultBookString(bookById);

            } else {
                result = String.format(NOT_FIND, bookId);
            }
        }
        return result;
    }

    @ShellMethod(value = "Find all books ", key = {"fndAll", "findAll"})
    public String findAll() {
        String result;
        if (isAvailable()) {
            List<Book> books = bookService.findAll();
            result = books.stream().map(UIShellInterface::getResultList).collect(Collectors.joining("\n"));
        } else {
            result = String.format(NOT_FIND, NULL_BOOK);
        }
        return result;
    }

    @ShellMethod(value = "Find all books by genre id", key = {"findAllByGenreID"})
    public String findAllByGenreId(@ShellOption String genreId) {
        String result = String.format(USER_NOT_LOGIN, null, "find");
        if (isAvailable()) {
            List<Book> books = genreService.findAllBooksByGenreId(genreId);
            result = books != null
                    ? books.stream().map(UIShellInterface::getResultList).collect(Collectors.joining("\n"))
                    : String.format(NOT_FIND, NULL_BOOK);
        }
        return result;
    }

    @ShellMethod(value = "Add Comment to book", key = {"addComment"})
    public String addComment(@ShellOption String bookId, @ShellOption String comment) {
        Book bookById = bookService.findBookById(bookId);
        if (bookById == null) {
            return String.format(NOT_FIND, bookId);
        }
        Comment comm = new Comment();
        comm.setComment(comment);
        comm.setBook(bookById);
        bookById.getComments().add(comm);
        Book savedBook = bookService.saveBook(bookById);
        return getResultBookString(savedBook);
    }

    @ShellMethod(value = "delete Comment from book", key = {"delComment"})
    public String deleteComment(@ShellOption String commentId) {
        Comment commentById = commentService.findById(commentId.toLowerCase()).orElse(null);
        if (commentById == null) {
            return String.format(NOT_FIND_COMMENT, commentId);
        }
        bookService.deleteCommentById(commentId);
        commentService.deleteById(commentId.toLowerCase());
        return String.format("Comment with id %s was successfully %s", commentId.toLowerCase(), DELETE);
    }

    @ShellMethod(value = "update Comment on book", key = {"upComment"})
    public String updateComment(@ShellOption String commentId, @ShellOption String newComment) {
        Comment commentById = commentService.findById(commentId.toLowerCase()).orElse(null);
        if (commentById == null) {
            return String.format(NOT_FIND_COMMENT, commentId);
        }
        commentById.setComment(newComment);
        commentService.update(commentById);
        return String.format("Comment with id %s was successfully %s", commentId.toLowerCase(), UPDATE);
    }

    @ShellMethod(value = "find Comment on book", key = {"findComment"})
    public String findComment(@ShellOption String commentId) {
        Comment commentById = commentService.findById(commentId.toLowerCase()).orElse(null);
        if (commentById == null) {
            return String.format(NOT_FIND_COMMENT, commentId);
        }
        return String.format("""
                        Comment with id %s and comment %s
                                 was successfully %s
                                 """,
                commentId.toLowerCase(),
                commentById.getComment(),
                FOUND);
    }


    @ShellMethod(value = "find all comments by book", key = {"findAllComments"})
    public String findAllCommentsByBookID(@ShellOption String bookId) {
        List<Comment> allCommentsByBookId = commentService.findAllCommentsByBookId(bookId);
        if (allCommentsByBookId.isEmpty()) {
            return String.format(NOT_FIND_COMMENT, NULL_COMMENT);
        }
        return String.format("""
                        Comments of book with id %s
                        was %s with comments:
                                                    %s
                        """
                , bookId
                , FOUND
                , allCommentsByBookId.isEmpty() ? "NO COMMENTS" : allCommentsByBookId.stream().map(Comment::toString)
                        .map(String::toLowerCase).collect(Collectors.joining(",\n\t\t\t\t\t\t\t")));
    }


    private static String getResultBookString(Book bookById) {
        String result;
        List<Comment> comments = bookById.getComments();
        result = String.format("""
                        Here your book: id - %s,
                                        title - %s,
                                        authorName - %s,
                                        genreName - %s,
                                        comments:{
                                                    %S
                                                  }
                                        """,
                bookById.getId(),
                bookById.getTitle(),
                bookById.getAuthor().getName(),
                bookById.getGenre().getName(),
                comments.isEmpty() ? "NO COMMENTS" : comments.stream().map(Comment::toString).map(String::toLowerCase).collect(Collectors.joining(",\n\t\t\t\t\t\t\t")));
        return result;
    }

    private static String getResultList(Book bookById) {
        String result;
        result = String.format("""
                        Here your book: id - %s,
                                        title - %s,
                                        authorName - %s,
                                        genreName - %s;
                                                  }
                                        """,
                bookById.getId(),
                bookById.getTitle(),
                bookById.getAuthor().getName(),
                bookById.getGenre().getName());
        return result;
    }

    private static String getResult(String title, boolean deleteById, String action) {
        String result;
        if (deleteById) {
            result = String.format(USER_LOGIN, title, action);
        } else {
            result = String.format(SMTH_WRONG, action, title);
        }
        return result;
    }

    private boolean isAvailable() {
        return islogin().isAvailable();
    }


    private Availability islogin() {
        return userName != null ? Availability.available()
                : Availability.unavailable("For start work with library please 'login'");
    }

    private void createGenreForUpdate(Book bookById, String genreId, String genreName) {
        Genre genre;
        if (!StringUtils.isBlank(genreId)
                && !StringUtils.isBlank(genreName)
                && !bookById.getGenre().getId().equals(genreId)
                && !bookById.getGenre().getName().equals(genreName)) {
            genre = new Genre();
            genre.setName(genreName);
            genre.setId(genreId);
            book.setGenre(genre);
        }
    }

    private void createAuthorForUpdate(Book bookById, String authorId, String authorName) {
        Author author;
        if (!StringUtils.isBlank(authorId)
                && !StringUtils.isBlank(authorName)
                && !bookById.getAuthor().getId().equals(authorId)
                && !bookById.getAuthor().getName().equals(authorName)) {
            author = new Author();
            author.setName(authorName);
            author.setId(authorId);
            book.setAuthor(author);
        }
    }
}
