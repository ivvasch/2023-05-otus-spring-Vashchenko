package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.model.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    @NonNull
    Author save(@NonNull Author author);
    @NonNull
    Optional<Author> findById(@NonNull String authorId);
    Author findByAuthorName(String authorName);
    @NonNull
    void deleteById(@NonNull String authorId);
}
