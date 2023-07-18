package ru.otus.springhw05.service;

import org.springframework.stereotype.Service;
import ru.otus.springhw05.dao.BookDao;
import ru.otus.springhw05.model.Author;
import ru.otus.springhw05.model.Book;
import ru.otus.springhw05.model.Genre;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreService genreService;
    private final AuthorService authorService;

    public BookServiceImpl(BookDao bookDao, GenreService genreService, AuthorService authorService) {
        this.bookDao = bookDao;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @Override
    public Integer saveBook(Book book) {
        genreService.save(book.getGenre());
        authorService.save(book.getAuthor());
        return bookDao.saveBook(book);
    }

    @Override
    public Book findBookById(String bookId) {
        return bookDao.findBookById(bookId);
    }

    @Override
    public List<Book> findAllBookByGenreId(String genreId) {
        return bookDao.findAllBookByGenreId(genreId);
    }

    @Override
    public boolean updateBook(Book book) {
        if (!ifExist(book, book.getAuthor())) {
            authorService.update(book.getAuthor());
        }
        if (!ifExist(book, book.getGenre())) {
            genreService.update(book.getGenre());
        }
        return bookDao.updateBook(book);
    }

    @Override
    public boolean deleteBookById(String bookId) {
        return bookDao.deleteBookById(bookId);
    }

    @Override
    public List<Book> findAllBookByAuthorId(String id) {
        return bookDao.findAllBookByAuthorId(id);
    }

    private <T> boolean ifExist(Book book, T clazz) {
        boolean ifExist = false;
        if (clazz instanceof Author) {
            Author authorById = authorService.findById(book.getAuthor().getId());
            ifExist = authorById != null
                    && authorById.getAuthorName().equals(book.getAuthor().getAuthorName())
                    && authorById.getId().equals(book.getAuthor().getId());
        }
        if (clazz instanceof Genre) {
            Genre genreById = genreService.findById(book.getGenre().getId());
            ifExist = genreById != null
                    && genreById.getGenreName().equals(book.getGenre().getGenreName())
                    && genreById.getId().equals(book.getGenre().getId());
        }
        return ifExist;
    }
}
