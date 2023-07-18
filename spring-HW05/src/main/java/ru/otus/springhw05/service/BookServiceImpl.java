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
        if (!ifExist(book.getAuthor())) {
            authorService.save(book.getAuthor());
        } else{
            authorService.update(book.getAuthor());
        }
        if (!ifExist(book.getGenre())) {
            genreService.save(book.getGenre());
        } else {
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

    private boolean ifExist(Author author) {
        Author authorById = authorService.findById(author.getId());
        return authorById != null
                && authorById.getAuthorName().equals(author.getAuthorName())
                && authorById.getId().equals(author.getId());
    }

    private boolean ifExist(Genre genre) {
        Genre genreById = genreService.findById(genre.getId());
        return genreById != null
                && genreById.getGenreName().equals(genre.getGenreName())
                && genreById.getId().equals(genre.getId());
    }
}
