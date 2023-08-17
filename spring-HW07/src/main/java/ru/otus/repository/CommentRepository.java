package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    void deleteById(@NonNull String commentId);
    @NonNull
    Optional<Comment> findById(@NonNull String commentId);
    List<Comment> findAllCommentsByBookId(String bookId);
    Comment merge(Comment comment);
}
