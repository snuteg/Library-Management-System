package com.natig.library.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Loan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;
    private final long bookId;
    private final long readerId;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LoanStatus status;
    private LocalDate actualReturnDate;

    public Loan(long bookId, long readerId, LocalDate dueDate) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.borrowDate = LocalDate.now();
        this.dueDate = Objects.requireNonNull(dueDate, "Due date must not be null!");
        this.status = LoanStatus.ACTIVE;
    }

    public long getId() {
        return id;
    }

    public long getBookId() {
        return bookId;
    }

    public long getReaderId() {
        return readerId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return status == LoanStatus.ACTIVE;
    }

    public void markReturned(LocalDate actualReturnDate) {
        if (!isActive()) {
            throw new IllegalStateException("This loan has already been returned!");
        }
        this.status = LoanStatus.RETURNED;
        this.actualReturnDate = Objects.requireNonNull(actualReturnDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan loan)) return false;
        return id == loan.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ID: " + id
                + ". Book ID: " + bookId
                + " | Reader ID: " + readerId
                + " | Borrow date: " + borrowDate
                + " | Due date: " + dueDate
                + " | Status: " + status
                + (actualReturnDate == null ? "" : " | Returned: " + actualReturnDate);
    }
}
