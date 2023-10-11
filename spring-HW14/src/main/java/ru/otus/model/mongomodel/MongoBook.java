package ru.otus.model.mongomodel;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "t_book")
public class MongoBook {
    @Id
    private String id;

    private String title;

    @DBRef
    private MongoGenre mongoGenre;

    @DBRef
    private MongoAuthor mongoAuthor;

    @DBRef
    private List<MongoComment> mongoComments = new ArrayList<>();

}
