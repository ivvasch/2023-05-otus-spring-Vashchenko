package ru.otus.springhw05.ui;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.springhw05.model.Author;
import ru.otus.springhw05.model.Book;
import ru.otus.springhw05.model.Genre;
import ru.otus.springhw05.service.BookService;

@ShellComponent
public class UIShellInterface {

    private String userName;
    private Book book;
    private final BookService bookService;
    private static final String USER_NOT_LOGIN = "Book with id %s wasn't %s because you are not login";
    private static final String USER_LOGIN = "Book %s was successfully %s";
    private static final String SMTH_WRONG = "Something wrong when we want to %s Book with id %s";
    private static final String NOT_FIND = "We didn't find your book with id %s";
    private static final String SAVED = "saved";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";


    public UIShellInterface(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption String username) {
        this.userName = username;
        return String.format("""
                Hello %s. you can configure your book:
                add book in next format:    'add <bookId>space<title>space<genreId>space<genreName>space<authorId>space<authorName>'
                delete book in next format: 'delete <bookId>'
                find book in next format:   'find <bookId>'
                update book in next format: 'update <bookId>space<title>space<genreId or null>space<genreName or null>space<authorId or null>space<authorName or null>'
                """, username);
    }

    @ShellMethod(value = "Add Book to library", key = {"add", "addBook"})
    public String addBook(@ShellOption String bookId, @ShellOption String title, @ShellOption String genreId,
                          @ShellOption String genreName, @ShellOption String authorId, @ShellOption String authorName) {
        String result = String.format(USER_NOT_LOGIN, bookId, SAVED);
        if (isAvailable()) {
            this.book = new Book();
            if (!StringUtils.isBlank(bookId))
                book.setId(bookId);
            if (!StringUtils.isBlank(title))
                book.setTitle(title);
            Genre genre = new Genre();
            if (!StringUtils.isBlank(genreId))
                genre.setId(genreId);
            if (!StringUtils.isBlank(genreName))
                genre.setGenreName(genreName);
            book.setGenre(genre);
            Author author = new Author();
            if (!StringUtils.isBlank(authorId))
                author.setId(authorId);
            if (!StringUtils.isBlank(authorName))
                author.setAuthorName(authorName);
            book.setAuthor(author);
            Integer savedBook = bookService.saveBook(book);
            result = getResult(bookId, savedBook == 1, SAVED);
        }
        return result;
    }

    @ShellMethod(value = "Delete the book from library", key = {"del", "delete"})
    public String deleteBook(@ShellOption String bookId) {
        String result = String.format(USER_NOT_LOGIN, bookId, DELETE);
        if (isAvailable()) {
            String title = bookService.findBookById(bookId).getTitle();
            if (title == null) {
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
                if (!StringUtils.isBlank(title))
                    book.setTitle(title);
                createGenreForUpdate(book, genreId, genreName);
                createAuthorForUpdate(book, authorId, authorName);
                boolean isUpdateBook = bookService.updateBook(book);
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
                result = String.format("""
                        Here your book: id - %s,
                                        title - %s,
                                        authorName - %s,
                                        genreName - %s!
                                        """,
                        bookById.getId(),
                        bookById.getTitle(),
                        bookById.getAuthor().getAuthorName(),
                        bookById.getGenre().getGenreName());
            } else {
                result = String.format(NOT_FIND, bookId);
            }
        }
        return result;
    }

    private static String getResult(String bookId, boolean deleteById, String action) {
        String result;
        if (deleteById) {
            result = String.format(USER_LOGIN, bookId, action);
        } else {
            result = String.format(SMTH_WRONG, action, bookId);
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

    private void createGenreForUpdate(Book book, String genreId, String genreName) {
        Genre genre;
        if (!StringUtils.isBlank(genreId) && !StringUtils.isBlank(genreName)) {
            genre = new Genre();
            genre.setGenreName(genreName);
            genre.setId(genreId);
            book.setGenre(genre);
        }
    }

    private void createAuthorForUpdate(Book book, String authorId, String authorName) {
        Author author;
        if (!StringUtils.isBlank(authorId) && !StringUtils.isBlank(authorName)) {
            author = new Author();
            author.setAuthorName(authorName);
            author.setId(authorId);
            book.setAuthor(author);
        }
    }
}
