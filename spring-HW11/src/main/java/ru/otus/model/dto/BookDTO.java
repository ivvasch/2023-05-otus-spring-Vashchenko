package ru.otus.model.dto;

import ru.otus.model.Book;

public record BookDTO(String id, String title, String authorName, String genreName) {
    public Book dtoToBook() {
        return Book.builder().title(title)
                .author(authorName)
                .genre(genreName)
                .build();
    }

    public static BookDTO toDto(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre());
    }
}
