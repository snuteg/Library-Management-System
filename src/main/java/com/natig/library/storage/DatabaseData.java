package com.natig.library.storage;

import com.natig.library.entity.Book;
import com.natig.library.entity.Loan;
import com.natig.library.entity.Reader;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DatabaseData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<Long, Book> books = new HashMap<>();
    private final Map<Long, Reader> readers = new HashMap<>();
    private final Map<Long, Loan> loans = new HashMap<>();

    public Map<Long, Book> getBooks() {
        return books;
    }

    public Map<Long, Reader> getReaders() {
        return readers;
    }

    public Map<Long, Loan> getLoans() {
        return loans;
    }
}
