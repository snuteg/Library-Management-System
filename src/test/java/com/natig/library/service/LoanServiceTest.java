package com.natig.library.service;

import com.natig.library.entity.Book;
import com.natig.library.entity.Genre;
import com.natig.library.entity.Loan;
import com.natig.library.entity.LoanStatus;
import com.natig.library.entity.Reader;
import com.natig.library.repository.BookRepository;
import com.natig.library.repository.LoanRepository;
import com.natig.library.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

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

        book = new Book("Dune", "Frank Herbert", 1965, Genre.SCIENCE);
        reader = new Reader("Natig", "natig@example.com");

        bookService.addBook(book);
        readerService.addReader(reader);
    }

    @Test
    void shouldCreateLoan() {
        Loan loan = loanService.addLoan(
                book.getId(),
                reader.getId(),
                LocalDate.now().plusDays(7)
        );

        assertEquals(book.getId(), loan.getBookId());
        assertEquals(reader.getId(), loan.getReaderId());
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
        assertNull(loan.getActualReturnDate());
    }

    @Test
    void shouldNotLoanSameBookTwiceAtTheSameTime() {
        loanService.addLoan(book.getId(), reader.getId(), LocalDate.now().plusDays(7));

        assertThrows(IllegalStateException.class, () ->
                loanService.addLoan(book.getId(), reader.getId(), LocalDate.now().plusDays(10))
        );
    }

    @Test
    void shouldRejectPastOrCurrentDueDate() {
        assertThrows(IllegalArgumentException.class, () ->
                loanService.addLoan(book.getId(), reader.getId(), LocalDate.now())
        );

        assertThrows(IllegalArgumentException.class, () ->
                loanService.addLoan(book.getId(), reader.getId(), LocalDate.now().minusDays(1))
        );
    }

    @Test
    void shouldReturnBookWithoutDeletingLoanHistory() {
        Loan loan = loanService.addLoan(
                book.getId(),
                reader.getId(),
                LocalDate.now().plusDays(7)
        );

        loanService.returnLoan(loan.getId());

        Loan savedLoan = loanService.getLoanById(loan.getId());
        assertEquals(LoanStatus.RETURNED, savedLoan.getStatus());
        assertEquals(LocalDate.now(), savedLoan.getActualReturnDate());
        assertEquals(1, loanService.getAllLoans().size());
    }
}
