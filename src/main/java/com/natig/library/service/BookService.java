package com.natig.library.service;

import com.natig.library.entity.Book;
import com.natig.library.entity.Loan;
import com.natig.library.repository.BookRepository;
import com.natig.library.repository.LoanRepository;

import java.util.Map;

public class BookService {
    private final BookRepository repository;
    private final LoanRepository loanRepository;

    public BookService(BookRepository repository, LoanRepository loanRepository) {
        this.repository = repository;
        this.loanRepository = loanRepository;
    }

    public void addBook(Book book) {
        repository.save(book);
    }

    public void removeBook(long bookId) {
        Book book = getById(bookId);

        boolean currentlyLoaned = loanRepository.findAll().values().stream()
                .anyMatch(loan -> loan.getBookId() == bookId && loan.isActive());

        if (currentlyLoaned) {
            throw new IllegalStateException("You cannot delete a book that is currently loaned!");
        }

        repository.delete(book);
    }

    public Book getById(long bookId) {
        return repository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("A book with this ID does not exist!")
        );
    }

    public Map<Long, Book> getAllBooks() {
        return repository.findAll();
    }
}
