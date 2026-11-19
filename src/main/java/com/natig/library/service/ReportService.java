package com.natig.library.service;

import com.natig.library.entity.Book;
import com.natig.library.entity.Loan;
import com.natig.library.entity.Reader;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportService {
    private final BookService bookService;
    private final ReaderService readerService;
    private final LoanService loanService;

    public ReportService(BookService bookService, ReaderService readerService, LoanService loanService) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.loanService = loanService;
    }

    public List<Book> getAllBooks() {
        return bookService.getAllBooks().values().stream().toList();
    }

    public List<Reader> getAllReaders() {
        return readerService.getAllReaders().values().stream().toList();
    }

    public List<Loan> getAllLoans() {
        return loanService.getAllLoans().values().stream().toList();
    }

    public List<Book> getAvailableBooks() {
        Set<Long> activeLoanedBookIds = activeLoans().stream()
                .map(Loan::getBookId)
                .collect(Collectors.toSet());

        return getAllBooks().stream()
                .filter(book -> !activeLoanedBookIds.contains(book.getId()))
                .toList();
    }

    public List<Book> getBooksLoanedByReaders() {
        Set<Long> activeLoanedBookIds = activeLoans().stream()
                .map(Loan::getBookId)
                .collect(Collectors.toSet());

        return getAllBooks().stream()
                .filter(book -> activeLoanedBookIds.contains(book.getId()))
                .toList();
    }

    public List<Book> getReaderBooks(long readerId) {
        readerService.getById(readerId);

        Set<Long> readerBookIds = activeLoans().stream()
                .filter(loan -> loan.getReaderId() == readerId)
                .map(Loan::getBookId)
                .collect(Collectors.toSet());

        return getAllBooks().stream()
                .filter(book -> readerBookIds.contains(book.getId()))
                .toList();
    }

    public List<Book> getPopularBooks() {
        Map<Long, Long> bookLoanCounts = getAllLoans().stream()
                .collect(Collectors.groupingBy(Loan::getBookId, Collectors.counting()));

        return getAllBooks().stream()
                .filter(book -> bookLoanCounts.containsKey(book.getId()))
                .sorted(Comparator.comparingLong((Book book) -> bookLoanCounts.get(book.getId())).reversed())
                .limit(3)
                .toList();
    }

    public List<Reader> getReadersLoanedTheMostBooks() {
        Map<Long, Long> readerLoanCounts = getAllLoans().stream()
                .collect(Collectors.groupingBy(Loan::getReaderId, Collectors.counting()));

        return getAllReaders().stream()
                .filter(reader -> readerLoanCounts.containsKey(reader.getId()))
                .sorted(Comparator.comparingLong((Reader reader) -> readerLoanCounts.get(reader.getId())).reversed())
                .limit(3)
                .toList();
    }

    private List<Loan> activeLoans() {
        return getAllLoans().stream()
                .filter(Loan::isActive)
                .toList();
    }
}
