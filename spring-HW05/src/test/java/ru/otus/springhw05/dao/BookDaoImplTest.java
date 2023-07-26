package ru.otus.springhw05.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.springhw05.model.Author;
import ru.otus.springhw05.model.Book;
import ru.otus.springhw05.model.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("При работе с bookDao, ")
class BookDaoImplTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;
    private String genreId;
    private String bookId;
    private String authorId;
    private Book book;
    private List<Book> listBook;


    @BeforeEach
    void setUp() {
        listBook = new ArrayList<>();

        Author author = new Author("e07e6aab-d89f-4f85-b453-efccaaff0975", "Aleksandr Dumas");
        Genre genre = new Genre("b67847da-c4fa-44f0-a4cf-9d53fbddfb96", "Fairy");
        book = new Book("05c3dd0b-7bb8-4947-bf0e-538a6a1f52b9", "The Pale Lady", genre, author);

        Author nextAuthor = new Author("e07e6aab-d89f-4f85-b453-efccaaff0975", "Aleksandr Dumas");
        Genre nextGenre = new Genre("b67847da-c4fa-44f0-a4cf-9d53fbddfb96", "Fairy");
        Book nextBook = new Book("bbe76b81-63d0-4328-a342-73b38febd6f6", "The Wolf Leader", nextGenre, nextAuthor);
        listBook.add(book);
        listBook.add(nextBook);

        genreId = "b67847da-c4fa-44f0-a4cf-9d53fbddfb96";
        bookId = book.getId();
        authorId = book.getAuthor().getId();

    }

    @Test
    @DisplayName(" получаем список книг по id жанра")
    void shouldFindAllBookDaoByGenreId() {
        List<Book> allBookByGenreId = bookDao.findAllBookByGenreId(genreId);
        assertArrayEquals(listBook.toArray(), allBookByGenreId.toArray());
    }

    @Test
    @DisplayName(" получаем корректно заполненную книгу по id книги")
    void shouldFindBookDaoById() {
        Book bookById = bookDao.findBookById(bookId);
        assertEquals(book, bookById);

    }

    @Test
    @DisplayName("получаем корректно заполненную книгу по id автора")
    void shouldFindAllBookDaoByAuthorId() {
        List<Book> allBookByAuthorId = bookDao.findAllBookByAuthorId(authorId);
        assertEquals(5, allBookByAuthorId.size());
    }

    @Test
    @DisplayName("книга удаляется")
    void shouldDeleteBookDaoById() {
        Author shakespeare = new Author("cc02c80f-de17-4d98-94b6-994670a08ce7", "Shakespeare");
        Genre pieceGenre = new Genre("6d91add4-e6c8-41e3-abbc-f3f615b06bae", "Piece");
        Book bookForDelete = new Book("4123f3ef-31c6-43ba-ab82-6882d22f7998", "Antony and Cleopatra", pieceGenre, shakespeare);

        assertEquals(bookDao.findBookById("4123f3ef-31c6-43ba-ab82-6882d22f7998"), bookForDelete);
        boolean isDeleted = bookDao.deleteBookById("4123f3ef-31c6-43ba-ab82-6882d22f7998");
        assertNotEquals(bookDao.findBookById("4123f3ef-31c6-43ba-ab82-6882d22f7998"), bookForDelete);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("книга успешно обновлена")
    void shouldUpdatedBookDao() {
        Author shakespeare = new Author("cc02c80f-de17-4d98-94b6-994670a08ce7", "Shakespeare");
        Genre pieceGenre = new Genre("6d91add4-e6c8-41e3-abbc-f3f615b06bae", "Piece");
        Book bookForUpdate = new Book("de3cab24-ac4b-4fc8-a5ed-76a489814120", "Coriolanus", pieceGenre, shakespeare);

        Book bookById = bookDao.findBookById("de3cab24-ac4b-4fc8-a5ed-76a489814120");
        assertEquals(bookForUpdate, bookById);
        Genre updateGenre = new Genre("1bc88153-8458-4d9d-bc75-06f2241d3a42", "Drama");
        bookForUpdate.setGenre(updateGenre);

        boolean isUpdatedBook = bookDao.updateBook(bookForUpdate);
        assertTrue(isUpdatedBook);
        assertNotEquals(bookById, bookForUpdate);
    }

    @Test
    @DisplayName("книга успешно сохранена")
    void shouldSaveBookDao() {
        Author tolkien = new Author("5a30ae50-fea6-4070-b360-a6d6dfb057c2", "J. R. R. Tolkien");
        authorDao.insert(tolkien);
        Genre fantasy = new Genre("55d13b2d-eb93-4225-af96-0a63bcdd51cf", "Fantasy");
        genreDao.save(fantasy);
        Book bookForSave = new Book("0ad5d328-7da6-41f5-ab85-33c4f82d6a78", "The Martian Chronicles ", fantasy, tolkien);
        Integer savedBookResult = bookDao.saveBook(bookForSave);
        assertEquals(1, savedBookResult);
        Book bookById = bookDao.findBookById("0ad5d328-7da6-41f5-ab85-33c4f82d6a78");
        assertEquals(bookForSave, bookById);
    }
}
