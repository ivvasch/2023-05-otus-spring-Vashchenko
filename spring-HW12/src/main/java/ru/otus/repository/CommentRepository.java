package ru.otus.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import ru.otus.model.Comment;

import java.util.List;
import java.util.Optional;

@Component
public interface CommentRepository extends MongoRepository<Comment, String> {

    Comment save(Comment comment);

    void deleteById(String commentId);

    Optional<Comment> findById(String commentId);

    @Query("{'book.$id': ObjectId(?0)}")
    List<Comment> findAllCommentsByBookId(String bookId);
}
