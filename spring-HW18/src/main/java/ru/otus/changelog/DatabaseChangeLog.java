package ru.otus.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

@ChangeLog
public class DatabaseChangeLog {


    @ChangeSet(order = "001", id = "dropDB", author = "ivvasch", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertCollections", author = "ivvasch", runAlways = true)
    public void createCollection(MongoDatabase db) {
        db.createCollection("t_author");
        db.createCollection("t_genre");
        db.createCollection("t_comment");
        db.createCollection("t_book");
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "ivvasch", runAlways = true)
    public void insertManyAuthors(AuthorRepository authorRepository) {
        Author author = new Author();
        author.setId("c20c2f9b-89c7-449e-a78c-63fb1fdb26c3");
        author.setName("Mark Twain");
        authorRepository.save(author);
        author = new Author();
        author.setId("e07e6aab-d89f-4f85-b453-efccaaff0975");
        author.setName("Aleksandr Dumas");
        authorRepository.save(author);
        author = new Author();
        author.setId("fbddd6ac-f4dd-4a39-804d-03d45d30241b");
        author.setName("Jules Verne");
        authorRepository.save(author);
        author = new Author();
        author.setId("cc02c80f-de17-4d98-94b6-994670a08ce7");
        author.setName("Shakespeare");
        authorRepository.save(author);
    }

    @ChangeSet(order = "004", id = "insertGenres", author = "ivvasch", runAlways = true)
    public void insertGenres(GenreRepository genreRepository) {
        Genre genre = new Genre();
        genre.setId("defd5e0b-1af4-45c0-a68a-1ea5a21296cf");
        genre.setName("Adventure");
        genreRepository.save(genre);
        genre = new Genre();
        genre.setId("b67847da-c4fa-44f0-a4cf-9d53fbddfb96");
        genre.setName("Fairy");
        genreRepository.save(genre);
        genre = new Genre();
        genre.setId("6d91add4-e6c8-41e3-abbc-f3f615b06bae");
        genre.setName("Piece");
        genreRepository.save(genre);
        genre = new Genre();
        genre.setId("1bc88153-8458-4d9d-bc75-06f2241d3a42");
        genre.setName("Drama");
        genreRepository.save(genre);
    }

    @ChangeSet(order = "005", id = "insertBooks", author = "ivvasch", runAlways = true)
    public void insertBooks(MongoDatabase db, AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
        Book book = new Book();
        book.setTitle("The Adventures of Tom Sawyer");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("c20c2f9b-89c7-449e-a78c-63fb1fdb26c3").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Adventures of Huckleberry Finn");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("c20c2f9b-89c7-449e-a78c-63fb1fdb26c3").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Tom Sawyer Abroad");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("c20c2f9b-89c7-449e-a78c-63fb1fdb26c3").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Tom Sawyer, Detective");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("c20c2f9b-89c7-449e-a78c-63fb1fdb26c3").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Huck Finn and Tom Sawyer Among the Indians");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("c20c2f9b-89c7-449e-a78c-63fb1fdb26c3").get());
        bookRepository.save(book);
        //---
        book = new Book();
        book.setTitle("The Count of Monte Cristo");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("e07e6aab-d89f-4f85-b453-efccaaff0975").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("The Three Musketeers");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("e07e6aab-d89f-4f85-b453-efccaaff0975").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Twenty Years After");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("e07e6aab-d89f-4f85-b453-efccaaff0975").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("The Pale Lady");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("e07e6aab-d89f-4f85-b453-efccaaff0975").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("The Wolf Leader");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("e07e6aab-d89f-4f85-b453-efccaaff0975").get());
        bookRepository.save(book);
        //---
        book = new Book();
        book.setTitle("The Adventures of Captain Hatteras");
        book.setGenre(genreRepository.findById("b67847da-c4fa-44f0-a4cf-9d53fbddfb96").get());
        book.setAuthor(authorRepository.findById("fbddd6ac-f4dd-4a39-804d-03d45d30241b").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Journey to the Center of the Earth");
        book.setGenre(genreRepository.findById("b67847da-c4fa-44f0-a4cf-9d53fbddfb96").get());
        book.setAuthor(authorRepository.findById("fbddd6ac-f4dd-4a39-804d-03d45d30241b").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("A Floating City");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("fbddd6ac-f4dd-4a39-804d-03d45d30241b").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Michael Strogoff");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("fbddd6ac-f4dd-4a39-804d-03d45d30241b").get());
        bookRepository.save(book);
        book = new Book();
        book.setTitle("Off on a Comet");
        book.setGenre(genreRepository.findById("defd5e0b-1af4-45c0-a68a-1ea5a21296cf").get());
        book.setAuthor(authorRepository.findById("fbddd6ac-f4dd-4a39-804d-03d45d30241b").get());
        bookRepository.save(book);
        // --
        book = new Book();
        book.setTitle("Coriolanus");
        book.setGenre(genreRepository.findById("6d91add4-e6c8-41e3-abbc-f3f615b06bae").get());
        book.setAuthor(authorRepository.findById("cc02c80f-de17-4d98-94b6-994670a08ce7").get());
        bookRepository.save(book);
    }
}
