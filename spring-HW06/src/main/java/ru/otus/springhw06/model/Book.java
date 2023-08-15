package ru.otus.springhw06.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_book")
@NamedEntityGraph(name = "comments-entity-graph",
        attributeNodes = {@NamedAttributeNode("comments")})
public class Book {
    @Id
    private String id;
    @Column(name = "title")
    private String title;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "genreid")
    private Genre genre;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "authorid")
    private Author author;
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<Comment> comments = new ArrayList<>();


    public Book(String title, Genre genre, Author author, List<Comment> comments) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.comments = comments;
    }
    public Book(String title, Genre genre, Author author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
    }
}
