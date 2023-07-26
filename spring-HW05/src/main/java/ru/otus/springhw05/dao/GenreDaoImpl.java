package ru.otus.springhw05.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.springhw05.mapper.GenreMapper;
import ru.otus.springhw05.model.Genre;

import java.util.List;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final JdbcOperations jdbc;

    public GenreDaoImpl(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Integer save(Genre genre) {
        Genre genreById = findById(genre.getId());
        return genreById != null ? 1 : jdbc.update("insert into t_genre values (?,?)", genre.getId(), genre.getGenreName());
    }

    @Override
    public Genre findById(String genreId) {
        List<Genre> genreList = jdbc.query("select id, name from t_genre where id = ?", new GenreMapper(), genreId);
        return genreList.isEmpty() ? null : genreList.get(0);
    }

    @Override
    public Genre findByName(String genreName) {
        List<Genre> genreList = jdbc.query("select id, name from t_genre where name = ?", new GenreMapper(), genreName);
        return genreList.isEmpty() ? null : genreList.get(0);
    }

    @Override
    public boolean deleteById(String genreId) {
        int deleteGenreResult = jdbc.update("delete from t_genre where id = ?", genreId);
        return deleteGenreResult == 1;
    }

    @Override
    public boolean update(Genre genre) {
        int updatedGenreResult = jdbc.update("update t_genre set name = ? where id = ?",
                genre.getGenreName(), genre.getId());
        return updatedGenreResult == 1;
    }
}
