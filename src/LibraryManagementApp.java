/* Name: Jesse Jittan
Course: 202510 Software Development I CEN-3024C-14320
Date: 9/8/2024
Program Objective:
The LibraryManagementApp class is the entry point for the application, houses the main method, and coordinates user
interaction. Providing the functionality to load books, display books, remove books based on user input, and the
ability to add additional functionality.
 */

import java.util.Scanner;

public class LibraryManagementApp {

    public static void main(String[] args) {

        // Create a Library instance to hold all books
        Library library = new Library();

        // Load books from a .txt file, and populates the Library instance with books from the file.
        library.loadBooksFromFile("src/resources/book_list.txt");

        // Iterates through the list of books and print each book using the override toString() method.
        System.out.println("Books in the library: ");
        for (Book book : library.getBooks()) {
            System.out.println(book);
        }

        // Prompts the user to remove a book by entering its unique ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the id of the book to remove: ");
        int idToRemove = scanner.nextInt();

        // Removes the book if found or returns a not found message.
        // Calls the removeBookById method
        boolean removed = library.removeBookById(idToRemove);
        if (removed) {
            System.out.println("Book with ID " + idToRemove + " removed.");
        } else {
            System.out.println("Book with ID " + idToRemove + " not found");
        }

        // Display remaining books in the library
        System.out.println("The remaining books in the collection after removal:");
        for (Book book : library.getBooks()) {
            System.out.println(book);
        }

        // Close the Scanner, this avoid resource leakage
        scanner.close();
    }
}