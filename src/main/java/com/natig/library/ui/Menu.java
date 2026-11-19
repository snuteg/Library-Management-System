package com.natig.library.ui;

import com.natig.library.entity.Book;
import com.natig.library.entity.Genre;
import com.natig.library.entity.Loan;
import com.natig.library.entity.Reader;
import com.natig.library.service.BookService;
import com.natig.library.service.LoanService;
import com.natig.library.service.ReaderService;
import com.natig.library.service.ReportService;
import com.natig.library.service.StorageService;
import com.natig.library.storage.DatabaseData;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final InputValidator validator = new InputValidator(scanner);

    private final BookService bookService;
    private final ReaderService readerService;
    private final LoanService loanService;
    private final ReportService reportService;
    private final String fileName;

    public Menu(BookService bookService,
                ReaderService readerService,
                LoanService loanService,
                ReportService reportService,
                String fileName) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.loanService = loanService;
        this.reportService = reportService;
        this.fileName = fileName;
    }

    public void start() {
        boolean runnable = true;

        while (runnable) {
            printMenu();
            int choice = validator.readInt("Select menu item: ");

            try {
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> removeBook();
                    case 3 -> addReader();
                    case 4 -> addLoan();
                    case 5 -> returnLoan();
                    case 6 -> showAllBooks();
                    case 7 -> showAvailableBooks();
                    case 8 -> showBooksLoanedByReaders();
                    case 9 -> showReaderBooks();
                    case 10 -> showPopularBooks();
                    case 11 -> showReadersLoanedTheMostBooks();
                    case 12 -> showAllLoans();
                    case 0 -> {
                        save();
                        runnable = false;
                    }
                    default -> System.out.println("You entered an invalid menu item.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void printMenu() {
        System.out.println("""
                =====LIBRARY MANAGEMENT SYSTEM=====
                1. Add a book.
                2. Remove a book.
                3. Register a reader.
                4. Issue a book to a reader.
                5. Return a book.
                6. Show all books.
                7. Show available books.
                8. Show books currently held by readers.
                9. Show books held by a specific reader.
                10. Show the most popular books.
                11. Show readers who have borrowed the most books.
                12. Show full loan history.
                0. Save and exit.
                """);
    }

    public void addBook() {
        String title = validator.readString("Enter a title: ");
        String author = validator.readString("Enter the author's name: ");
        int year = validator.readInt("Book publication year: ");
        Genre genre = validator.readGenre();

        Book book = new Book(title, author, year, genre);
        bookService.addBook(book);
        System.out.println("Book added.");
    }

    public void removeBook() {
        showAllBooks();
        long bookId = validator.readLong("Enter book ID: ");
        bookService.removeBook(bookId);
        System.out.println("Book deleted.");
    }

    public void addReader() {
        String name = validator.readString("Enter name: ");
        String email = validator.readString("Enter email: ");
        Reader reader = new Reader(name, email);
        readerService.addReader(reader);
        System.out.println("The reader is registered.");
    }

    public void addLoan() {
        showAllBooks();
        long bookId = validator.readLong("Enter book ID: ");

        showAllReaders();
        long readerId = validator.readLong("Enter reader ID: ");

        LocalDate dueDate = validator.readDate("Enter due date [yyyy-MM-dd]: ");
        loanService.addLoan(bookId, readerId, dueDate);
        System.out.println("Loan created.");
    }

    public void returnLoan() {
        showActiveLoans();
        long loanId = validator.readLong("Enter loan ID: ");
        loanService.returnLoan(loanId);
        System.out.println("Book returned.");
    }

    public void showAllBooks() {
        printList(reportService.getAllBooks(), "Book list is empty");
    }

    public void showAllReaders() {
        printList(reportService.getAllReaders(), "Reader list is empty");
    }

    public void showAllLoans() {
        printList(reportService.getAllLoans(), "Loan list is empty");
    }

    public void showActiveLoans() {
        List<Loan> activeLoans = reportService.getAllLoans().stream()
                .filter(Loan::isActive)
                .toList();
        printList(activeLoans, "There are no active loans");
    }

    public void showAvailableBooks() {
        printList(reportService.getAvailableBooks(), "Book list is empty");
    }

    public void showBooksLoanedByReaders() {
        printList(reportService.getBooksLoanedByReaders(), "Book list is empty");
    }

    public void showReaderBooks() {
        showAllReaders();
        long readerId = validator.readLong("Enter reader ID: ");
        List<Book> readerBooks = reportService.getReaderBooks(readerId);
        printList(readerBooks, "This reader has no active loans");
    }

    public void showPopularBooks() {
        printList(reportService.getPopularBooks(), "There is no loan history yet");
    }

    public void showReadersLoanedTheMostBooks() {
        printList(reportService.getReadersLoanedTheMostBooks(), "There is no loan history yet");
    }

    private void printList(List<?> values, String emptyMessage) {
        if (values.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        values.forEach(System.out::println);
    }

    public void save() {
        DatabaseData data = new DatabaseData();
        data.getBooks().putAll(bookService.getAllBooks());
        data.getReaders().putAll(readerService.getAllReaders());
        data.getLoans().putAll(loanService.getAllLoans());

        StorageService.save(data, fileName);
        System.out.println("Data saved, exiting...");
    }
}
