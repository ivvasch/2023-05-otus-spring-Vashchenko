package ru.otus.springhw05.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.otus.springhw05.dao.BookDao;
import ru.otus.springhw05.dao.GenreDao;
import ru.otus.springhw05.model.Genre;

@Data
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final BookDao bookDao;
    @Override
    public Integer save(Genre genre) {
        return genreDao.save(genre);
    }

    @Override
    public Genre findById(String genreId) {
        return genreDao.findById(genreId);
    }

    @Override
    public Genre findByName(String genreName) {
        return genreDao.findByName(genreName);
    }

    @Override
    public boolean deleteById(String genreId) {
        return genreDao.deleteById(genreId);
    }

    @Override
    public boolean update(Genre genre) {
        return genreDao.update(genre);
    }
}
