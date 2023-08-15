package ru.otus.springhw06.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_comments")
@NamedEntityGraph(name = "comments")
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String commentId;
    @Column(name = "comment")
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Override
    public String toString() {
        return commentId + " : " + comment;
    }
}
