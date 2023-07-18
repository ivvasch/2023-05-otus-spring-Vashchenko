package ru.otus.springhw05.dao;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.springhw05.model.Author;

import static org.junit.jupiter.api.Assertions.*;

@Data
@SpringBootTest
@DisplayName("При работе с authorDao,")
class AuthorDaoImplTest {
    @Autowired
    private AuthorDao authorDao;
    private Author author;

    @BeforeEach
    void setup() {
        author = new Author("d0fd5b38-96a6-475f-97f3-7ff680f2be54", "Ernest Hemingway");
    }

    @Test
    @DisplayName("получаем корректно заполненного автора")
    void shouldFindAuthorByName() {
        Author julesVerne = authorDao.findByName("Jules Verne");
        assertEquals("Jules Verne", julesVerne.getAuthorName());
    }

    @Test
    @DisplayName("автор сохраняется корректно")
    void shouldSaveAuthor() {
        Integer saved = authorDao.save(author);
        assertEquals(1, saved);

    }

    @Test
    @DisplayName("находим автора по его id")
    void shouldfindAuthorById() {
        Author authorForSave = new Author("196718e7-742f-4542-9314-c95f62666ef7", "Antoine de Saint-Exupéry");
        authorDao.save(authorForSave);
        Author authorById = authorDao.findById("196718e7-742f-4542-9314-c95f62666ef7");
        assertEquals("Antoine de Saint-Exupéry", authorById.getAuthorName());
    }

    @Test
    @DisplayName("автор удаляется из базы")
    void shouldDeleteAuthorById() {
        authorDao.save(author);
        boolean deleteById = authorDao.deleteById("d0fd5b38-96a6-475f-97f3-7ff680f2be54");
        assertTrue(deleteById);
    }

    @Test
    @DisplayName("имя автора корректно изменяется в базе")
    void shouldUpdateAuthorName() {
        authorDao.save(author);
        String newName = "Ernest Miller Hemingway";
        author = new Author("d0fd5b38-96a6-475f-97f3-7ff680f2be54", newName);
        authorDao.update(author);
        Author authorById = authorDao.findById("d0fd5b38-96a6-475f-97f3-7ff680f2be54");
        assertEquals(newName, authorById.getAuthorName());
    }
}
