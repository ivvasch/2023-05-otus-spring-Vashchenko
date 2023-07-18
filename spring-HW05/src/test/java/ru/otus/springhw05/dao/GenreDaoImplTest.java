package ru.otus.springhw05.dao;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.springhw05.model.Book;
import ru.otus.springhw05.model.Genre;

import static org.junit.jupiter.api.Assertions.*;

@Data
@SpringBootTest
@DisplayName("При работе с genreDao,")
class GenreDaoImplTest {

    @Autowired
    private GenreDao genreDao;
    private Book book;
    private Genre genre;

    @BeforeEach
    void setup() {
        genre = new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "fantastic");
    }
    @Test
    @DisplayName("жанр успешно удаляется")
    void shouldDeleteById() {
        genreDao.save(genre);
        boolean deleteById = genreDao.deleteById("99379fb3-42d4-41f5-955f-c4f52e300234");
        assertTrue(deleteById);
    }

    @Test
    @DisplayName("жанр успешно обновляется")
    void shouldUpdateGenre() {
        genreDao.save(genre);
        Genre newGenre = new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "newFantastic");
        genreDao.update(newGenre);
        Genre byId = genreDao.findById("99379fb3-42d4-41f5-955f-c4f52e300234");
        assertEquals("newFantastic", byId.getGenreName());
    }

    @Test
    @DisplayName("жанр успешно сохраняется")
    void shouldToSaveGenre() {
        Integer saved = genreDao.save(genre);
        assertEquals(1, saved);
    }

    @Test
    @DisplayName("жанр успешно найден по id")
    void shouldFindGenreById() {
        genreDao.save(genre);
        Genre byId = genreDao.findById("99379fb3-42d4-41f5-955f-c4f52e300234");
        assertEquals("99379fb3-42d4-41f5-955f-c4f52e300234", byId.getId());
    }

    @Test
    @DisplayName("жанр успешно найден по имени")
    void shouldFindGenreByName() {
        genreDao.save(genre);
        Genre fantastic = genreDao.findByName("fantastic");
        assertEquals("fantastic", fantastic.getGenreName());
    }
}