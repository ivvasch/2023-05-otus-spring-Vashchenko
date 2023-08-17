package ru.otus.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.repository.AuthorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("При работе с authorRepository")
@SpringBootTest
class AuthorRepositoryImplTest {

    @Autowired
    private AuthorRepository authorRepository;
    private Author author;
    @BeforeEach
    void setUp() {
        author = new Author("5fd26a91-bf9b-4a0b-97c5-01dc258d89bc", "Luis Karol");
    }

    @Test
    @DisplayName("автор сохраняется и обновляется в базе корректно")
    @Transactional
    void save() {
        Author saved = authorRepository.save(author);
        assertEquals(author, saved);
        saved.setAuthorName("Lyis Karol");
        Author savedNew = authorRepository.save(saved);
        assertEquals("Lyis Karol", savedNew.getAuthorName());
    }

    @Test
    @DisplayName("автор находится по id корректно")
    @Transactional
    void findById() {
        authorRepository.save(author);
        Optional<Author> byId = authorRepository.findById("5fd26a91-bf9b-4a0b-97c5-01dc258d89bc");
        assertEquals(author, byId.orElse(null));
    }

    @Test
    @DisplayName("автор находится по его имени")
    @Transactional
    void findByAuthorName() {
        authorRepository.save(author);
        Author luisKarol = authorRepository.findByAuthorName("Luis Karol");
        assertEquals(author, luisKarol);
    }

    @Test
    @DisplayName("автор удаляется корректно")
    @Transactional
    void deleteById() {
        Author authorForDelete = new Author("0f6641de-e3dc-4385-85c9-77d11c4da3c5", "Hamilton");
        authorRepository.save(authorForDelete);
        authorRepository.deleteById("0f6641de-e3dc-4385-85c9-77d11c4da3c5");
        Optional<Author> deletedAuthor = authorRepository.findById("0f6641de-e3dc-4385-85c9-77d11c4da3c5");
        assertTrue(deletedAuthor.isEmpty());
    }
}
