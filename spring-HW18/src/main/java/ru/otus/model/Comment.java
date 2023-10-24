package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "t_comment")
public class Comment {

    @Id
    private String commentId;

    private String comment;

    @DBRef(db = "t_book")
    private Book book;

    @Override
    public String toString() {
        return commentId + " : " + comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comm)) return false;
        return Objects.equals(getCommentId(), comm.getCommentId())
                && Objects.equals(getComment(), comm.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommentId(), getComment());
    }
}
