package ru.otus.springhw05.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.otus.springhw05.dao.AuthorDao;
import ru.otus.springhw05.dao.BookDao;
import ru.otus.springhw05.model.Author;
import ru.otus.springhw05.model.Book;

import java.util.List;

@Data
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    @Override
    public Integer save(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public Author findById(String authorId) {
        return authorDao.findById(authorId);
    }

    @Override
    public boolean deleteById(String authorId) {
            List<Book> allBookByAuthorId = bookDao.findAllBookByAuthorId(authorId);
            allBookByAuthorId.forEach(book ->
                bookDao.deleteBookById(book.getId())
            );
        return authorDao.deleteById(authorId);
    }

    @Override
    public boolean update(Author author) {
        return authorDao.update(author);
    }
}
