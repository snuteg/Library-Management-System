package com.natig.library.repository;

import com.natig.library.entity.Reader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReaderRepository {
    private long nextId = 1;
    private final Map<Long, Reader> readers;

    public ReaderRepository(Map<Long, Reader> readers) {
        this.readers = readers;
    }

    public void save(Reader reader) {
        reader.setId(nextId++);
        readers.put(reader.getId(), reader);
    }

    public void setAllIds() {
        long maxId = readers.keySet().stream().mapToLong(Long::longValue).max().orElse(0L);
        nextId = maxId + 1;
    }

    public Map<Long, Reader> findAll() {
        return new HashMap<>(readers);
    }

    public Optional<Reader> findById(long id) {
        return Optional.ofNullable(readers.get(id));
    }
}
