package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.model.mongomodel.MongoAuthor;
import ru.otus.model.mongomodel.MongoBook;
import ru.otus.model.mongomodel.MongoComment;
import ru.otus.model.mongomodel.MongoGenre;

import java.util.List;

@Service
public class TransferService {
    public MongoBook transfer(Book book) {
        MongoBook mongoBook = new MongoBook();
        mongoBook.setId(book.getId());
        mongoBook.setTitle(book.getTitle());
        MongoAuthor mongoAuthor = new MongoAuthor();
        mongoAuthor.setId(book.getAuthor().getId());
        mongoAuthor.setName(book.getAuthor().getName());
        mongoBook.setMongoAuthor(mongoAuthor);
        MongoGenre mongoGenre = new MongoGenre();
        mongoGenre.setId(book.getGenre().getId());
        mongoGenre.setName(book.getGenre().getName());
        mongoBook.setMongoGenre(mongoGenre);
        List<MongoComment> mongoComments = getListMongoCommentsFromComments(book.getComments(), mongoBook);
        mongoBook.setMongoComments(mongoComments);
        return mongoBook;
    }

    private List<MongoComment> getListMongoCommentsFromComments(List<Comment> comments, MongoBook mongoBook) {
        return comments.stream()
                .map(Comment::getComment)
                .map(comm -> new MongoComment(comm, mongoBook))
                .toList();

    }

    public MongoAuthor transferAuthor(Author author) {
        return new MongoAuthor(author.getId(), author.getName());
    }

    public MongoGenre transferGenre(Genre genre) {
        return new MongoGenre(genre.getId(), genre.getName());
    }

}
