package com.natig.library.service;

import com.natig.library.entity.Reader;
import com.natig.library.repository.ReaderRepository;

import java.util.Map;

public class ReaderService {
    private final ReaderRepository repository;

    public ReaderService(ReaderRepository repository) {
        this.repository = repository;
    }

    public void addReader(Reader reader) {
        repository.save(reader);
    }

    public Reader getById(long readerId) {
        return repository.findById(readerId).orElseThrow(
                () -> new IllegalArgumentException("A reader with this ID does not exist!")
        );
    }

    public Map<Long, Reader> getAllReaders() {
        return repository.findAll();
    }
}
