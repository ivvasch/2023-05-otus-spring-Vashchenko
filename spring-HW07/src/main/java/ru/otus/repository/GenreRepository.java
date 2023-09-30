package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {

    Genre save( Genre genre);

    Optional<Genre> findById( String genreId);

    Genre findByName(String genreName);

    void deleteById( String genreId);
}
