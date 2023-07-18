package ru.otus.springhw05.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.springhw05.model.Genre;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@DisplayName("При работе с genreService,")
class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;
    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "fantastic");
    }

    @Test
    @DisplayName("новый жанр сохраняется корректно")
    void shouldSaveGenreCorrect() {
        Integer saveResult = genreService.save(genre);
        assertEquals(1, saveResult);
    }

    @Test
    @DisplayName("новый жанр находится по id")
    void shouldFindGenreById() {
        genreService.save(genre);
        Genre genreById = genreService.findById("99379fb3-42d4-41f5-955f-c4f52e300234");
        assertEquals("fantastic", genreById.getGenreName());
    }

    @Test
    @DisplayName("новый жанр находится по имени")
    void shouldFindGenreByName() {
        genreService.save(genre);
        Genre fantastic = genreService.findByName("fantastic");
        assertEquals("99379fb3-42d4-41f5-955f-c4f52e300234", fantastic.getId());
    }

    @Test
    @DisplayName("новый жанр удаляется по id")
    void shouldDeleteGenreById() {
        genreService.save(genre);
        boolean deleteById = genreService.deleteById("99379fb3-42d4-41f5-955f-c4f52e300234");
        assertTrue(deleteById);
        Genre genreById = genreService.findById("99379fb3-42d4-41f5-955f-c4f52e300234");
        assertNull(genreById);
    }

    @Test
    @DisplayName("новый жанр обновляется по имени")
    void shouldUpdateGenreName() {
        genreService.save(genre);
        Genre newGenre = new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "newFantastic");
        genreService.update(newGenre);
        Genre genreByIdyId = genreService.findById("99379fb3-42d4-41f5-955f-c4f52e300234");
        assertEquals("newFantastic", genreByIdyId.getGenreName());
    }
}
