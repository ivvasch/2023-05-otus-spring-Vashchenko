package ru.otus.springhw06.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import ru.otus.springhw06.model.Genre;
import ru.otus.springhw06.repository.GenreRepository;

import java.util.Optional;

@Component
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Genre save(Genre genre) {
        Genre findGenre = entityManager.find(Genre.class, genre.getId());
        if (findGenre != null && findGenre.equals(genre)) {
            return genre;
        }
        if (findGenre == null) {
            entityManager.persist(genre);
            return genre;
        } else {
            return entityManager.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(String genreId) {
        Genre genre = entityManager.find(Genre.class, genreId);
        return Optional.ofNullable(genre);
    }

    @Override
    public Genre findByGenreName(String genreName) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", genreName);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(String genreId) {
        Genre genre = entityManager.find(Genre.class, genreId);
        entityManager.remove(genre);
    }
}
