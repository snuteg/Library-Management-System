package com.natig.library.repository;

import com.natig.library.entity.Loan;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoanRepository {
    private long nextId = 1;
    private final Map<Long, Loan> loans;

    public LoanRepository(Map<Long, Loan> loans) {
        this.loans = loans;
    }

    public void save(Loan loan) {
        loan.setId(nextId++);
        loans.put(loan.getId(), loan);
    }

    public void setAllIds() {
        long maxId = loans.keySet().stream().mapToLong(Long::longValue).max().orElse(0L);
        nextId = maxId + 1;
    }

    public Map<Long, Loan> findAll() {
        return new HashMap<>(loans);
    }

    public Optional<Loan> findById(long id) {
        return Optional.ofNullable(loans.get(id));
    }
}
