package ru.otus.springhw05.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.springhw05.mapper.AuthorMapper;
import ru.otus.springhw05.model.Author;

import java.util.List;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcOperations jdbc;

    public AuthorDaoImpl(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Integer insert(Author author) {
        Author authorById = findById(author.getId());
        return authorById != null ? 1 : jdbc.update("insert into t_author values (?, ?)", author.getId(), author.getAuthorName());
    }

    @Override
    public Author findById(String authorId) {
        List<Author> authorList = jdbc.query("select id, name from t_author where id = ?", new AuthorMapper(), authorId);
        return authorList.isEmpty() ? null : authorList.get(0);
    }

    @Override
    public Author findByName(String authorName) {
        List<Author> authorList = jdbc.query("select id, name from t_author where name = ?", new AuthorMapper(), authorName);
        return authorList.isEmpty() ? null : authorList.get(0);
    }

    @Override
    public boolean deleteById(String authorId) {
        int deletedAuthorResult = jdbc.update("delete from t_author where id = ?", authorId);
        return deletedAuthorResult == 1;
    }

    @Override
    public boolean update(Author author) {
        int updatedAuthorResult = jdbc.update("update t_author set name = ? where id = ?",
                author.getAuthorName(), author.getId());
        return updatedAuthorResult == 1;
    }
}
