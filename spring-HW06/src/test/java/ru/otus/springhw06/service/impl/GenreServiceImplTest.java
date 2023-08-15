package ru.otus.springhw06.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.springhw06.model.Genre;
import ru.otus.springhw06.repository.impl.BookRepositoryImpl;
import ru.otus.springhw06.repository.impl.GenreRepositoryImpl;
import ru.otus.springhw06.service.GenreService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Import({GenreServiceImpl.class,
        GenreRepositoryImpl.class,
        BookRepositoryImpl.class})
@DataJpaTest
@DisplayName("При работе с genreService,")
class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;
    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "fantastic", new ArrayList<>());
    }

    @Test
    @DisplayName("новый жанр сохраняется корректно")
    void shouldSaveGenreCorrect() {
        Genre saveResult = genreService.save(genre);
        System.out.println("genreService ==> -------------------------------------");
        assertEquals(genre, saveResult);
        System.out.println("shouldSaveGenreCorrect ========================================");
    }

    @Test
    @DisplayName("новый жанр находится по id")
    void shouldFindGenreById() {
        genreService.save(genre);
        Genre genreById = genreService.findById("99379fb3-42d4-41f5-955f-c4f52e300234");
        System.out.println("genreService ==> -------------------------------------");
        assertEquals("fantastic", genreById.getName());
        System.out.println("shouldFindGenreById ========================================");
    }

    @Test
    @DisplayName("новый жанр находится по имени")
    void shouldFindGenreByName() {
        genreService.save(genre);
        System.out.println("genreService ==> -------------------------------------");
        Genre fantastic = genreService.findByName("fantastic");
        System.out.println("genreService ==> -------------------------------------");
        assertEquals("99379fb3-42d4-41f5-955f-c4f52e300234", fantastic.getId());
        System.out.println("shouldFindGenreByName ========================================");
    }

    @Test
    @DisplayName("новый жанр удаляется по id")
    void shouldDeleteGenreById() {
        genreService.save(genre);
        System.out.println("genreService ==> -------------------------------------");
        boolean deleteById = genreService.deleteById("99379fb3-42d4-41f5-955f-c4f52e300234");
        System.out.println("genreService ==> -------------------------------------");
        assertTrue(deleteById);
        Genre genreById = genreService.findById("99379fb3-42d4-41f5-955f-c4f52e300234");
        System.out.println("genreService ==> -------------------------------------");
        assertNull(genreById);
        System.out.println("shouldDeleteGenreById ========================================");
    }

    @Test
    @DisplayName("новый жанр обновляется по имени")
    void shouldUpdateGenreName() {
        genreService.save(genre);
        System.out.println("genreService ==> -------------------------------------");
        Genre newGenre = new Genre("99379fb3-42d4-41f5-955f-c4f52e300234", "newFantastic", new ArrayList<>());
        genreService.update(newGenre);
        System.out.println("genreService ==> -------------------------------------");
        Genre genreByIdyId = genreService.findById("99379fb3-42d4-41f5-955f-c4f52e300234");
        System.out.println("genreService ==> -------------------------------------");
        assertEquals("newFantastic", genreByIdyId.getName());
        System.out.println("shouldUpdateGenreName ========================================");
    }
}
