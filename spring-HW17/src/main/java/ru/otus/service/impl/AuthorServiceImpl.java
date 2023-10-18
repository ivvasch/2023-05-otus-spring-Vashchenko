package ru.otus.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private static final Logger log = LogManager.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;


    @Override
    @Transactional
    public Author save(Author author) {
        log.info("Method save got author with name {} to save.", author.getName());
        return authorRepository.save(author);
    }

    @Override
    public Author findById(String authorId) {
        log.info("Method findById got author with id {} for search.", authorId);
        return authorRepository.findById(authorId).orElse(null);
    }

    @Override
    @Transactional
    public boolean deleteById(String authorId) {
        log.info("Method deleteById got author with id {} for delete.", authorId);
        List<Book> allBookByAuthorId = bookRepository.findBooksByAuthorId(authorId);
        if (!allBookByAuthorId.isEmpty()) {
            return false;
        } else {
            authorRepository.deleteById(authorId);
        }
        return authorRepository.findById(authorId).isPresent();

    }

    @Override
    @Transactional
    public boolean update(Author author) {
        log.info("Method update got author with id {} for update.", author.getId());
        Optional<Author> byIdBeforeUpdate = authorRepository.findById(author.getId());
        authorRepository.save(author);
        return author.equals(byIdBeforeUpdate.orElse(null));
    }
}
