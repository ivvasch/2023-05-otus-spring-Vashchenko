package ru.otus.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.model.Genre;
import ru.otus.repository.GenreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("При обращении к genreRepository")
class GenreRepositoryImplTest {

    @Autowired
    private GenreRepository genreRepository;

    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre("1a409200-3c82-48a5-8be5-37a80bda4a99", "horror");
    }

    @Test
    @DisplayName("жанр сохраняется и обновляется корректно")
    void save() {
        Genre saved = genreRepository.save(genre);
        assertEquals(genre.getName(), saved.getName());
        saved.setName("comedy");
        Genre updated = genreRepository.save(saved);
        assertEquals(saved, updated);
    }

    @Test
    @DisplayName("жанр находится по id")
    void findById() {
        genreRepository.save(genre);
        Optional<Genre> byId = genreRepository.findById("1a409200-3c82-48a5-8be5-37a80bda4a99");
        Genre actual = byId.orElse(null);
        assertEquals(genre.getId(), actual != null ? actual.getId() : null);
        assertEquals(genre.getName(), actual != null ? actual.getName() : null);
    }

    @Test
    @DisplayName("жанр находится по своему имени")
    void findByGenreName() {
        genreRepository.save(genre);
        Genre horror = genreRepository.findByName("horror");
        assertEquals(genre.getName(), horror.getName());
    }

    @Test
    @DisplayName("жанр удаляется корректно")
    void deleteById() {
        Genre genreForDelete = new Genre("329248c0-6919-4a7f-90f8-caee823159a9", "lite comedy");
        genreRepository.save(genreForDelete);
        Optional<Genre> byId = genreRepository.findById("329248c0-6919-4a7f-90f8-caee823159a9");
        assertTrue(byId.isPresent());
        genreRepository.deleteById(genreForDelete.getId());
        Optional<Genre> byIdAfterDelete = genreRepository.findById("329248c0-6919-4a7f-90f8-caee823159a9");
        assertTrue(byIdAfterDelete.isEmpty());
    }
}
