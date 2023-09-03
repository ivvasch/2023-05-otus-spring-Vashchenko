package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

    Author save(Author author);

    Optional<Author> findById(String authorId);

    Author findByName(String authorName);

    void deleteById(String authorId);
}
