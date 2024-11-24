/*
package library;*/
/* Name: Jesse Jittan
Course: 202510 Software Development I CEN-3024C-14320
Date: 10/6/2024
Program Objective:
The library.Library class is responsible for managing the collection of books in the LMS. It will handle functions like loading
books, adding books, removing books, and checking out or checking in books.

I've update the library.Library to the business logic layer - handles the core functionality of the application and have removed
all feedback (i.e. print statements) and user interaction from this layer. The library.Library class doesn’t print anything
because its job is to manage books, not interact with users. Therefore I have placed the UI Layer in the main class.
 *//*


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Library {

    // private field variable (called books) that holds library.Book objects in the form of a list.
    // It is a collection (list) that stores multiple library.Book objects in the variable 'books'

    private List<Book> books;

    // Constructor that initializes the library.Library obj. It sets up the books list as an empty ArrayList, so that you can
    //      later add, remove, or retrieve books from it.
    // Sets every new library.Library instance to an empty list, and initializes it to make sure the obj is in a valid and usable
    //      state from the moment it is created.

    public Library() {
        this.books = new ArrayList<>();
    }

    // Method 1: Adding a single 'library.Book'
    public boolean addBook(Book book) {
        return books.add(book);
    }

    // Method 2: Method to remove a book from the library by its ID
    // The for each loop iterates over each library.Book obj in the books list, and check one by one to see if it matches the
    //      id provided

    public boolean removeBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                return true;
            }
        }
        return false; // library.Book ID not found.
    }

    // Method 3: Method to remove book from the library by its ISBN
    // Similar to the removeBookById method

    public boolean removeBookByIsbn(long isbn) {
        for (Book book : books) {
            if (book.getIsbn() == isbn)
                return true;
        }
        return false; // library.Book ISBN not found.
    }

    // Method 4: Checking out a library.Book

    public boolean checkOutBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {           // Check if the current ID matches the incoming ID and if it does...
                if (!book.isCheckedOut()) {     // Will return true if the book is available (i.e., not checked out)
                    book.checkOut();            // Moves to this method to call the checkOut() which sets isCheckedOut = true
                    return true;
                }
                return false;                   // library.Book is already checked out
            }
        }
        return false;                           // library.Book with given ID not found
    }

    // Method 5: Checking in a library.Book

    public boolean checkInBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                if (book.isCheckedOut()) {      // Check if the book is checked out - if isCheckedOut = True ...
                    book.checkIn();             // ...then proceeds to checkIn() book - updating the library.Book state isCheckedOut = false
                    return true;
                }
                return false;                   // library.Book already in stock (checked in)
            }
        }
        return false;                           // library.Book with given ID not found
    }

    // Method 6: Display all Books

    public List<Book> getAllBooks() {
        return books;                           // returns a list of all books
    }

    // Method 7: Loading Books from a File
    // This tells the caller that the method can throw IOException, but doesn't throw anything directly.
    //      A promise to the caller that the exception needs to be handled if they arise.

    public void loadBooksFromFile(String fileName) throws IOException {

        // FileReader - opens the file and reads the raw characters from it.
        // BufferedReader - wraps around the raw characters and efficiently reads the file line by line.
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;        // variable named line that would hold the text data that will be read from a file (of type String)


            while ((line = br.readLine()) != null) {                    // Reads each line until the end of the file (null), processes entire file, and stores it in the variable line
                String[] bookData = line.split(",");              // Splits ea ln into a String array, separated by a comma and stores it into the variable bookData
                if (bookData.length != 5) {                             // Validates the data - verifies that the line contains 5 fields, and if not (!=)

                    throw new IllegalArgumentException("Invalid, each line must contain 5 fields.");
                }

                int id = Integer.parseInt(bookData[0]);
                long isbn = Long.parseLong(bookData[1]);
                String title = bookData[2];
                String author = bookData[3];
                String genre = bookData[4];
                books.add(new Book(id, isbn, title, author, genre));    // A new Book obj is created using the parsed data and added to the books collection (an ArrayList<Book>).
                                                                        // Each line of the file represents one book.
            }
            // If there is a file related issue (file cannot be found, accessed, or opened) an IOException will be thrown and
            //     caught here 'catch IOException e' and rethrown (passed to the LibraryManagementApp to handle).
        } catch (IOException e) {
            throw e;
        }
    }

    // Method 8: Check Out Book By Title
    // Added later in the module to accommodate GUI project 11/3/24

    public boolean checkOutBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {      // Match the book by title (makes the entry case-insensitive).
                if (!book.isCheckedOut()) {                     // Check if the book is available isCheckedOut = false not operator changes it to true to execute the if statement.
                    book.checkOut();                            // Mark the book as checked out
                    return true;
                } else {
                    return false;                               // Book already checked out
                }
            }
        }
        return false;                                           // Book title not found
    }

    public boolean checkInBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {      // Matches each book title with the book title entered
                if (book.isCheckedOut()) {                      // Verifies if that book is checked out
                    book.checkIn();                             // If it is checked out, call the checkIn() and mark it as checked in
                    return true;
                } else {
                    return false;                               // Book already checked in
                }
            }
        }
        return false;                                           // Book title not found
    }
}










*/
