package com.natig.library.service;

import com.natig.library.entity.Book;
import com.natig.library.entity.Genre;
import com.natig.library.repository.BookRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryBehaviorTest {

    @Test
    void findByIdShouldUseMapKeyCorrectly() {
        BookRepository repository = new BookRepository(new HashMap<>());
        Book book = new Book("The Hobbit", "J. R. R. Tolkien", 1937, Genre.FANTASY);

        repository.save(book);

        assertTrue(repository.findById(book.getId()).isPresent());
        assertEquals(book, repository.findById(book.getId()).orElseThrow());
        assertTrue(repository.findById(999L).isEmpty());
    }
}
