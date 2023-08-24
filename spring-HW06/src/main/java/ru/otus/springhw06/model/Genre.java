package ru.otus.springhw06.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_genre")
public class Genre {
    @Id
    private String id;

    private String name;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre")
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        book.setGenre(this);
        books.add(book);
    }
}
