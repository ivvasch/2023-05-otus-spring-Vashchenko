package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {

    Author save(Author author);
    
    Optional<Author> findById( String authorId);

    Author findByName(String authorName);
    
    void deleteById( String authorId);
}
