package com.natig.library.service;

import com.natig.library.entity.Book;
import com.natig.library.entity.Genre;
import com.natig.library.entity.Reader;
import com.natig.library.repository.BookRepository;
import com.natig.library.repository.LoanRepository;
import com.natig.library.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;
    private ReaderService readerService;
    private LoanService loanService;

    private Book book;
    private Reader reader;

    @BeforeEach
    void setUp() {
        BookRepository bookRepository = new BookRepository(new HashMap<>());
        ReaderRepository readerRepository = new ReaderRepository(new HashMap<>());
        LoanRepository loanRepository = new LoanRepository(new HashMap<>());

        bookService = new BookService(bookRepository, loanRepository);
        readerService = new ReaderService(readerRepository);
        loanService = new LoanService(loanRepository, bookService, readerService);

        book = new Book("1984", "George Orwell", 1949, Genre.HISTORY);
        reader = new Reader("Alex", "alex@example.com");

        bookService.addBook(book);
        readerService.addReader(reader);
    }

    @Test
    void shouldRemoveAvailableBook() {
        bookService.removeBook(book.getId());

        assertTrue(bookService.getAllBooks().isEmpty());
    }

    @Test
    void shouldNotRemoveBookWhileItIsLoaned() {
        loanService.addLoan(book.getId(), reader.getId(), LocalDate.now().plusDays(7));

        assertThrows(IllegalStateException.class, () ->
                bookService.removeBook(book.getId())
        );
    }

    @Test
    void shouldAllowRemovingBookAfterItWasReturned() {
        var loan = loanService.addLoan(book.getId(), reader.getId(), LocalDate.now().plusDays(7));
        loanService.returnLoan(loan.getId());

        bookService.removeBook(book.getId());

        assertTrue(bookService.getAllBooks().isEmpty());
    }
}
