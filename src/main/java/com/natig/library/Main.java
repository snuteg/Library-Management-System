package com.natig.library;

import com.natig.library.repository.BookRepository;
import com.natig.library.repository.LoanRepository;
import com.natig.library.repository.ReaderRepository;
import com.natig.library.service.BookService;
import com.natig.library.service.LoanService;
import com.natig.library.service.ReaderService;
import com.natig.library.service.ReportService;
import com.natig.library.service.StorageService;
import com.natig.library.storage.DatabaseData;
import com.natig.library.ui.Menu;

public class Main {
    private static final String FILE_NAME = "library-management-system.ser";

    public static void main(String[] args) {
        DatabaseData data = StorageService.load(FILE_NAME);

        BookRepository bookRepository = new BookRepository(data.getBooks());
        ReaderRepository readerRepository = new ReaderRepository(data.getReaders());
        LoanRepository loanRepository = new LoanRepository(data.getLoans());

        bookRepository.setAllIds();
        readerRepository.setAllIds();
        loanRepository.setAllIds();

        ReaderService readerService = new ReaderService(readerRepository);
        BookService bookService = new BookService(bookRepository, loanRepository);
        LoanService loanService = new LoanService(loanRepository, bookService, readerService);
        ReportService reportService = new ReportService(bookService, readerService, loanService);

        Menu menu = new Menu(bookService, readerService, loanService, reportService, FILE_NAME);
        menu.start();
    }
}
