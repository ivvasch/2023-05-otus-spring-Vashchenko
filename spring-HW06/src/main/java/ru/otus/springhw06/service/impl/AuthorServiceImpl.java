package ru.otus.springhw06.service.impl;

import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springhw06.model.Author;
import ru.otus.springhw06.model.Book;
import ru.otus.springhw06.repository.AuthorRepository;
import ru.otus.springhw06.repository.BookRepository;
import ru.otus.springhw06.service.AuthorService;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;


    @Override
    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author findById(String authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    @Override
    @Transactional
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
    @Transactional
    public boolean update(Author author) {
        return authorRepository.save(author) != null;
    }
}
