package com.natig.library.ui;

import com.natig.library.entity.Genre;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {
    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }
        }
    }

    public long readLong(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }
        }
    }

    public String readString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (input.isBlank()) {
                System.out.println("The field must not be empty");
                continue;
            }
            return input.trim();
        }
    }

    public LocalDate readDate(String message) {
        while (true) {
            System.out.print(message);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Enter a valid date format [yyyy-MM-dd]");
            }
        }
    }

    public Genre readGenre() {
        while (true) {
            System.out.println("""
                    Genre types:
                    1. Fantasy.
                    2. Romance.
                    3. Horror.
                    4. Thriller.
                    5. Science.
                    6. History.
                    """);
            int choice = readInt("Select genre type: ");
            switch (choice) {
                case 1 -> { return Genre.FANTASY; }
                case 2 -> { return Genre.ROMANCE; }
                case 3 -> { return Genre.HORROR; }
                case 4 -> { return Genre.THRILLER; }
                case 5 -> { return Genre.SCIENCE; }
                case 6 -> { return Genre.HISTORY; }
                default -> System.out.println("You selected the wrong genre type.");
            }
        }
    }
}
