package com.natig.library.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Reader implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;
    private final String name;
    private final String email;

    public Reader(String name, String email) {
        this.name = checkName(name);
        this.email = checkEmail(email);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty!");
        }
        return name.trim();
    }

    private String checkEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be empty!");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format!");
        }
        return email.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader reader)) return false;
        return id == reader.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ID: " + id + ". " + name + " | Email: " + email;
    }
}
