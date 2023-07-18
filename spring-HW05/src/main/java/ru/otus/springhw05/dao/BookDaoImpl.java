package ru.otus.springhw05.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.springhw05.mapper.BookMapper;
import ru.otus.springhw05.model.Book;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    private final JdbcOperations jdbc;
    private static final String SQL_QUERY_JOIN_GENRE_JOIN_AUTHOR = """
            select b.id, b.title, b.genreId, g.name as genreName, b.authorId, a.name as authorName from t_book b
            join t_genre g on b.genreId = g.id
            join t_author a on b.authorId = a.id
            """;
            

    public BookDaoImpl(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Integer saveBook(Book book) {
        Book bookById = findBookById(book.getId());
        return bookById != null ? 1 : jdbc.update("insert into t_book values (?, ?, ?, ?)",
                book.getId(), book.getTitle(), book.getGenre().getId(), book.getAuthor().getId());
    }

    @Override
    public Book findBookById(String bookId) {
        List<Book> books = jdbc.query(SQL_QUERY_JOIN_GENRE_JOIN_AUTHOR + " where b.id = ?", new BookMapper(), bookId);
        return books.isEmpty() ? null : books.get(0);
    }

    @Override
    public List<Book> findAllBookByGenreId(String genreId) {
        return jdbc.query(SQL_QUERY_JOIN_GENRE_JOIN_AUTHOR + " where genreId = ?", new BookMapper(), genreId);
    }

    @Override
    public boolean updateBook(Book book) {
        int updatedBook = jdbc.update("update t_book set title = ?, genreId = ?, authorId = ? where id = ?",
                book.getTitle(), book.getGenre().getId(), book.getAuthor().getId(), book.getId());
        return updatedBook == 1;
    }

    @Override
    public boolean deleteBookById(String bookId) {
        int delete = jdbc.update("delete from t_book where id = ?", bookId);
        return delete == 1;
    }

    @Override
    public List<Book> findAllBookByAuthorId(String authorId) {
        return jdbc.query(SQL_QUERY_JOIN_GENRE_JOIN_AUTHOR + " where authorId = ?", new BookMapper(), authorId);
    }
}
