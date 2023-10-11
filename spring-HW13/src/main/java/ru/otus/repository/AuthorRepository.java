package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.model.Author;

import java.util.Optional;

@Component
public interface AuthorRepository extends MongoRepository<Author, String> {

    Author save(Author author);

    Optional<Author> findById(String authorId);

    Author findByName(String authorName);

    void deleteById(String authorId);
}
