# Library Management System

This is a console-based library management system I built using Java Core, Maven, and JUnit 5.

I made this project to practice working with object-oriented programming, collections, and unit testing.

## Features

The application allows users to manage books, readers, and book loans through a console menu.

Here's what it can do:

- Manage books and library readers.
- Lend books to readers and track their return dates.
- Keep a full history of book loans, even after books are returned.
- Track loan statuses, due dates, and actual return dates.
- Prevent the same book from being borrowed by two readers at once.
- Prevent books from being deleted while they are borrowed.
- Validate due dates when creating loans.
- Show the most popular books and most active readers based on borrowing history.

## Testing

I added JUnit 5 tests to check how the main parts of the application work.

To run the tests:

```bash
mvn test
```

## Data Storage

For now, the project uses Java Serialization to save and load data.

I decided not to use JSON yet because I wanted to focus on Java Core and unit testing first. I plan to add Jackson later as a separate improvement.