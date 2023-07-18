package ru.otus.springhw05.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.springhw05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }
}
