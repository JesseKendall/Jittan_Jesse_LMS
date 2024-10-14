/* Name: Jesse Jittan
Course: 202510 Software Development I CEN-3024C-14320
Date: 10/13/2024
Program Objective:
The LibraryManagementApp class is the entry point for the application, houses the main method, and coordinates user
interaction. Providing the functionality to load books from a file then provides 6 user-input options:
            1. Add a new book
            2. Remove a book by ID
            3. Check out a book by ID
            4. Check in a book by ID
            5. Display all books
            6. Quit
for the user to interact with the application.

User Interaction Layer (e.g., main Class, UI Controllers): This layer is responsible for interacting with the
user—taking input and presenting output. This is where print statements (or any user feedback mechanism) typically
belong, as this is the part of the program designed to communicate with the user.
 */

import java.io.IOException;
import java.util.Scanner;

public class LibraryManagementApp {

    public static void main(String[] args) {

        // Create a Library instance to hold all books
        Library library = new Library();

        // Creates a Scanner obj to read input from the user console (learned, but just wanted it here for my purpose)
        Scanner scanner = new Scanner(System.in);

        // Prompts the user to enter a file name that contains the book data (starts the process)
        // I've predetermined that files should always be stored in the src/resources/ directory hence adding "src/resources/" path
        System.out.println("Enter the file name to load books: ");
        String fileName = "src/resources/" + scanner.nextLine();

        // Try-catch block for loadBooksFromFile method
        try {
            // Calls the loadBooksFromFile method with the user entered fileName provided above.
            library.loadBooksFromFile(fileName);

            // If no exceptions are thrown and the file loads successfully, continue...
            System.out.println("Books loaded successfully from " + fileName + " .");

            // Handles file-related errors such as: File Not Found, Incorrect File Path, Permission Issues, File is Corrupt
        } catch (IOException e) {
            System.out.println("File could not be loaded: " + e.getMessage());

            // Handles errors happen when the program can open the file but finds that the content inside is not formatted
            //      or organized the way the program expects (i.e. Missing Info, Wrong Data Type, Field Inconsistencies)
        } catch (IllegalArgumentException e) {
            System.out.println("Error occurred while trying to read the file: " + e.getMessage());
        }

        // Controls the main menu loop, so that it will keep displaying unless the user selects the 'Quit' option.
        // Gives the user 6 Options to choose how to proceed.
        boolean quit = false;
        while (!quit) {
            System.out.println("\nMenu:");
            System.out.println("1. Add a new book");
            System.out.println("2. Remove a book by ID");
            System.out.println("3. Check out a book by ID");
            System.out.println("4. Check in a book by ID");
            System.out.println("5. Display all books");
            System.out.println("6. Quit");
        }

        // Prompts the user to select an option
        System.out.println("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();             // Consumes the newline that was generated from hitting enter: /n

        switch (option) {
            case 1:
                // Add a new book
                System.out.print("Enter book ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                System.out.print("Enter book ISBN: ");
                long isbn = scanner.nextLong();
                scanner.nextLine();  // Consume the newline

                System.out.print("Enter book title: ");
                String title = scanner.nextLine();

                System.out.print("Enter book author: ");
                String author = scanner.nextLine();

                System.out.print("Enter book genre: ");
                String genre = scanner.nextLine();

                // Create the new book and add it to the library
                Book newBook = new Book(id, isbn, title, author, genre);
                if (library.addBook(newBook)) {
                    System.out.println("Book added successfully.");
                } else {
                    System.out.println("Failed to add the book.");
                }
                break;

            case 2:
                // Remove a book by ID
                System.out.print("Enter the ID of the book to remove: ");
                int idToRemove = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                if (library.removeBookById(idToRemove)) {
                    System.out.println("Book removed successfully.");
                } else {
                    System.out.println("Book not found.");
                }
                break;

            case 3:
                // Check out a book by ID
                System.out.print("Enter the ID of the book to check out: ");
                int idToCheckOut = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                if (library.checkOutBook(idToCheckOut)) {
                    System.out.println("Book checked out successfully.");
                } else {
                    System.out.println("Unable to check out the book.");
                }
                break;

            case 4:
                // Check in a book by ID
                System.out.print("Enter the ID of the book to check in: ");
                int idToCheckIn = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                if (library.checkInBook(idToCheckIn)) {
                    System.out.println("Book checked in successfully.");
                } else {
                    System.out.println("Unable to check in the book.");
                }
                break;

            case 5:
                // Display all books
                System.out.println("\nBooks currently in the library:");
                for (Book book : library.getAllBooks()) {
                    System.out.println(book);  // Calls Book's toString method
                }
                break;

            case 6:
                // Quit the program
                quit = true;
                System.out.println("Exiting the Library Management System.");
                break;

            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
        // Close the scanner
        scanner.close();
    }
}

