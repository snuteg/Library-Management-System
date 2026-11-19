package com.natig.library.repository;

import com.natig.library.entity.Book;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookRepository {
    private long nextId = 1;
    private final Map<Long, Book> books;

    public BookRepository(Map<Long, Book> books) {
        this.books = books;
    }

    public void save(Book book) {
        book.setId(nextId++);
        books.put(book.getId(), book);
    }

    public void delete(Book book) {
        books.remove(book.getId());
    }

    public void setAllIds() {
        long maxId = books.keySet().stream().mapToLong(Long::longValue).max().orElse(0L);
        nextId = maxId + 1;
    }

    public Map<Long, Book> findAll() {
        return new HashMap<>(books);
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(books.get(id));
    }
}
