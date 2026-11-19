package com.natig.library.service;

import com.natig.library.entity.Loan;
import com.natig.library.repository.LoanRepository;

import java.time.LocalDate;
import java.util.Map;

public class LoanService {
    private final LoanRepository repository;
    private final BookService bookService;
    private final ReaderService readerService;

    public LoanService(LoanRepository repository, BookService bookService, ReaderService readerService) {
        this.repository = repository;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    public Loan addLoan(long bookId, long readerId, LocalDate dueDate) {
        bookService.getById(bookId);
        readerService.getById(readerId);

        if (dueDate == null || !dueDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be after today!");
        }

        boolean alreadyLoaned = repository.findAll().values().stream()
                .anyMatch(loan -> loan.getBookId() == bookId && loan.isActive());

        if (alreadyLoaned) {
            throw new IllegalStateException("The book has already been loaned to another reader!");
        }

        Loan loan = new Loan(bookId, readerId, dueDate);
        repository.save(loan);
        return loan;
    }

    public void returnLoan(long loanId) {
        Loan loan = getLoanById(loanId);
        loan.markReturned(LocalDate.now());
    }

    public Loan getLoanById(long loanId) {
        return repository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("A loan with this ID does not exist!")
        );
    }

    public Map<Long, Loan> getAllLoans() {
        return repository.findAll();
    }
}
