package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.model.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
    Genre save(@NonNull Genre genre);
    Optional<Genre> findById(@NonNull String genreId);
    Genre findByGenreName(String genreName);
    void deleteById(@NonNull String genreId);
}
