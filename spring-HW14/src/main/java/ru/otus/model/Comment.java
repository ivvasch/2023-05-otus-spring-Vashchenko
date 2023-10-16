package ru.otus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_comments")
@NamedEntityGraph(name = "comments")
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String commentId;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Override
    public String toString() {
        return commentId + " : " + comment;
    }
}
