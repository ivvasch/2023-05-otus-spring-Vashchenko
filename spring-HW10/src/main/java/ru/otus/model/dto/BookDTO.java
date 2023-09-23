package ru.otus.model.dto;

import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;

import java.util.ArrayList;
import java.util.List;

public record BookDTO(String id, String title, String authorName, String genreName, List<String> comments) {
    public Book dtoToBook() {
        return Book.builder().title(title)
                .author(new Author(null, authorName))
                .genre(new Genre(null, genreName))
                .comments(new ArrayList<>())
                .build();
    }

    public static BookDTO toDto(Book book) {
        List<String> list = book.getComments().stream().map(Comment::getComment).toList();
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getName(), list);
    }
}
