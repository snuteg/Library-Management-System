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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportServiceTest {

    private BookService bookService;
    private ReaderService readerService;
    private LoanService loanService;
    private ReportService reportService;

    private Book firstBook;
    private Book secondBook;
    private Reader reader;

    @BeforeEach
    void setUp() {
        BookRepository bookRepository = new BookRepository(new HashMap<>());
        ReaderRepository readerRepository = new ReaderRepository(new HashMap<>());
        LoanRepository loanRepository = new LoanRepository(new HashMap<>());

        bookService = new BookService(bookRepository, loanRepository);
        readerService = new ReaderService(readerRepository);
        loanService = new LoanService(loanRepository, bookService, readerService);
        reportService = new ReportService(bookService, readerService, loanService);

        firstBook = new Book("Dune", "Frank Herbert", 1965, Genre.SCIENCE);
        secondBook = new Book("Dracula", "Bram Stoker", 1897, Genre.HORROR);
        reader = new Reader("Natig", "natig@example.com");

        bookService.addBook(firstBook);
        bookService.addBook(secondBook);
        readerService.addReader(reader);
    }

    @Test
    void returnedBookShouldBecomeAvailableAgain() {
        var loan = loanService.addLoan(firstBook.getId(), reader.getId(), LocalDate.now().plusDays(7));
        loanService.returnLoan(loan.getId());

        assertTrue(reportService.getAvailableBooks().contains(firstBook));
        assertTrue(reportService.getBooksLoanedByReaders().isEmpty());
    }

    @Test
    void popularBooksShouldUseFullLoanHistory() {
        var firstLoan = loanService.addLoan(firstBook.getId(), reader.getId(), LocalDate.now().plusDays(7));
        loanService.returnLoan(firstLoan.getId());

        var secondLoan = loanService.addLoan(firstBook.getId(), reader.getId(), LocalDate.now().plusDays(7));
        loanService.returnLoan(secondLoan.getId());

        loanService.addLoan(secondBook.getId(), reader.getId(), LocalDate.now().plusDays(7));

        assertEquals(firstBook, reportService.getPopularBooks().get(0));
    }
}
