package ru.otus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "t_book")
public class Book {

    @Id
    private String id;

    private String title;

    @DBRef
    private Genre genre;

    @DBRef
    private Author author;

    @DBRef
    private List<Comment> comments = new ArrayList<>();

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId())
                && Objects.equals(getTitle(), book.getTitle())
                && Objects.equals(getGenre().getName(), book.getGenre().getName())
                && Objects.equals(getGenre().getId(), book.getGenre().getId())
                && Objects.equals(getAuthor().getName(), book.getAuthor().getName())
                && Objects.equals(getAuthor().getId(), book.getAuthor().getId())
                && Objects.equals(getComments().size(), book.getComments().size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getGenre(), getAuthor(), getComments());
    }
}
