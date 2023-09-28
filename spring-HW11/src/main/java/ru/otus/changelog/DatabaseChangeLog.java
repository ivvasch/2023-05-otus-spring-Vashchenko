package ru.otus.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.model.Book;
import ru.otus.repository.BookRepository;

@ChangeLog
public class DatabaseChangeLog {


    @ChangeSet(order = "001", id = "dropDB", author = "ivvasch", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertCollections", author = "ivvasch", runAlways = true)
    public void createCollection(MongoDatabase db) {
        db.createCollection("t_book");
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "ivvasch", runAlways = true)
    public void insertBooks(MongoDatabase db, BookRepository bookRepository) {
        Book book = new Book();
        book.setTitle("The Adventures of Tom Sawyer");
        book.setGenre("Adventure");
        book.setAuthor("Mark Twain");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("Adventures of Huckleberry Finn");
        book.setGenre("Adventure");
        book.setAuthor("Mark Twain");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("Tom Sawyer Abroad");
        book.setGenre("Adventure");
        book.setAuthor("Mark Twain");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("Tom Sawyer, Detective");
        book.setGenre("Adventure");
        book.setAuthor("Mark Twain");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("Huck Finn and Tom Sawyer Among the Indians");
        book.setGenre("Adventure");
        book.setAuthor("Mark Twain");
        bookRepository.save(book).block();
        //---
        book = new Book();
        book.setTitle("The Count of Monte Cristo");
        book.setGenre("Adventure");
        book.setAuthor("Aleksandr Dumas");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("The Three Musketeers");
        book.setGenre("Adventure");
        book.setAuthor("Aleksandr Dumas");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("Twenty Years After");
        book.setGenre("Adventure");
        book.setAuthor("Aleksandr Dumas");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("The Pale Lady");
        book.setGenre("Adventure");
        book.setAuthor("Aleksandr Dumas");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("The Wolf Leader");
        book.setGenre("Adventure");
        book.setAuthor("Aleksandr Dumas");
        bookRepository.save(book).block();
        //---
        book = new Book();
        book.setTitle("The Adventures of Captain Hatteras");
        book.setGenre("Adventure");
        book.setAuthor("Jules Verne");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("Journey to the Center of the Earth");
        book.setGenre("Adventure");
        book.setAuthor("Jules Verne");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("A Floating City");
        book.setGenre("Adventure");
        book.setAuthor("Jules Verne");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("Michael Strogoff");
        book.setGenre("Adventure");
        book.setAuthor("Jules Verne");
        bookRepository.save(book).block();
        book = new Book();
        book.setTitle("Off on a Comet");
        book.setGenre("Adventure");
        book.setAuthor("Jules Verne");
        bookRepository.save(book).block();
        // --
        book = new Book();
        book.setTitle("Coriolanus");
        book.setGenre("Drama");
        book.setAuthor("Shakespeare");
        bookRepository.save(book).block();
    }
}
