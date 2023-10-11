package ru.otus.model.mongomodel;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "t_comment")
public class MongoComment {
    @Id
    private String commentId;
    private String comment;

    @DBRef(db = "t_book")
    private MongoBook mongoBook;

    public MongoComment(String comment, MongoBook mongoBook) {
        this.comment = comment;
        this.mongoBook = mongoBook;
    }
}
