package ru.otus.springhw05.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.springhw05.model.Author;
import ru.otus.springhw05.model.Book;
import ru.otus.springhw05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BookMapper implements RowMapper<Book> {

    Map<String, Author> authorMap = new HashMap<>();
    Map<String, Genre> genreMap = new HashMap<>();

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.getString(1).isEmpty()) {
            return null;
        }
        Genre genre;
        Author author;
        String id = rs.getString("id");
        String title = rs.getString("title");
        String genreId = rs.getString("genreId");
        String genreName = rs.getString("genreName");
        String authorId = rs.getString("authorId");
        String authorName = rs.getString("authorName");
        if (!genreMap.containsKey(genreId)) {
            genre = new Genre(genreId, genreName);
            genreMap.put(genreId, genre);
        } else {
            genre = genreMap.get(genreId);
        }
        if (!authorMap.containsKey(authorId)) {
            author = new Author(authorId, authorName);
            authorMap.put(authorId, author);
        } else {
            author = authorMap.get(authorId);
        }
        return new Book(id, title,  genre, author);
    }
}
