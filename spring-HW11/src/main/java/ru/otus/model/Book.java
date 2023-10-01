package ru.otus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String genre;

    private String author;


    public Book(String title, String author, String genre) {
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
                && Objects.equals(getGenre(), book.getGenre())
                && Objects.equals(getAuthor(), book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getGenre(), getAuthor());
    }
}
