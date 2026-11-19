package com.natig.library.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Year;
import java.util.Objects;

public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;
    private final String title;
    private final String author;
    private final int year;
    private final Genre genre;

    public Book(String title, String author, int year, Genre genre) {
        this.title = checkTitle(title);
        this.author = checkAuthor(author);
        this.year = checkYear(year);
        this.genre = Objects.requireNonNull(genre, "Genre must not be null!");
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String checkTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be empty!");
        }
        return title.trim();
    }

    private String checkAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author name must not be empty!");
        }
        return author.trim();
    }

    private int checkYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < 0 || year > currentYear) {
            throw new IllegalArgumentException("Invalid publication year!");
        }
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ID: " + id + ". " + title
                + " | Author: " + author
                + " | Year of publication: " + year
                + " | Genre: " + genre;
    }
}
