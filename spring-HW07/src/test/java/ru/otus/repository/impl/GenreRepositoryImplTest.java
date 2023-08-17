package ru.otus.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    void save() {
        Genre saved = genreRepository.save(genre);
        assertEquals(genre, saved);
        saved.setGenreName("comedy");
        Genre updated = genreRepository.save(saved);
        assertEquals(saved, updated);
    }

    @Test
    @DisplayName("жанр находится по id")
    @Transactional
    void findById() {
        genreRepository.save(genre);
        Optional<Genre> byId = genreRepository.findById("1a409200-3c82-48a5-8be5-37a80bda4a99");
        assertEquals(genre, byId.orElse(null));
    }

    @Test
    @DisplayName("жанр находится по своему имени")
    @Transactional
    void findByGenreName() {
        genreRepository.save(genre);
        Genre horror = genreRepository.findByGenreName("horror");
        assertEquals(genre, horror);
    }

    @Test
    @DisplayName("жанр удаляется корректно")
    @Transactional
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
