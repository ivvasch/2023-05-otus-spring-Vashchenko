package ru.otus.service.impl;

import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.service.AuthorService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;


    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author findById(String authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    @Override
    public boolean deleteById(String authorId) {
            List<Book> allBookByAuthorId = bookRepository.findBooksByAuthorId(authorId);
        if (!allBookByAuthorId.isEmpty()) {
            return false;
        } else {
            authorRepository.deleteById(authorId);
        }
        try {
            return authorRepository.findById(authorId).isPresent();
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean update(Author author) {
        Optional<Author> byIdBeforeUpdate = authorRepository.findById(author.getId());
        authorRepository.save(author);
        return author.equals(byIdBeforeUpdate.orElse(null));
    }
}
