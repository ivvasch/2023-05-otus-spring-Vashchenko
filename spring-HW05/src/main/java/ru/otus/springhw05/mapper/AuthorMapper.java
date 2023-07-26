package ru.otus.springhw05.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.springhw05.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        return new Author(id, name);
    }
}
