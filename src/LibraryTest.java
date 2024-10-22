import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LibraryTest {

    // Create an object to be tested - a field to store it - declaration variable.
    Library library;
    Book newBook;

    // @BeforeEach ensures that every test method starts with a fresh new Library and Book.
    // This isolates the state of each test so that no test affects another.
    @BeforeEach
    void setUp() {          // where you create the Library and Book object.

        library = new Library();
        newBook = new Book(1, 000000000001, "Jesse JUnit Book", "Jesse Jittan", "Non-Fiction");

     }

    // @Test tells JUnit this is a test method
    // @DisplayName - Provides a descriptive name for your test
    // Where action happens, this is where the code interacts with the Library and Book Obj
    @Test
    @DisplayName("Add Single Book Test")
    void addBook_Test() {

        // Calls the addBook() method from the library class
        //      > attempts to add it (the newBook) to the library
        //              > and returns true if the book is successfully added, if false will print error message
        assertTrue(library.addBook(newBook), "addBook() should return true if the book is successfully added");

        // Verify that the library contains the added book
        //      library.getAllBooks() - calls the getAllBooks() to show every book in the library right now
        //          .contains(newBook) checks if the list includes the specific book (i.e. newBook).
        //              returns true if the book is in the list, if false will print error message
        assertTrue(library.getAllBooks().contains(newBook), "Library should contain the new book after adding it");

        // Declares a new variable (fetchBook) of type Book
        //      Returns the list of books, specifically index 0 and stores it in that variable (i.e. fetchBook)
        //          This variable is used to check if the added book's details are correct.
        Book fetchBook = library.getAllBooks().get(0);

        assertEquals(01, fetchBook.getId(), "Book ID should be 01");
        assertEquals(000000000001,fetchBook.getIsbn(), "Book ISBN should be 000000000001");
        assertEquals("Jesse JUnit Book", fetchBook.getTitle(), "Book Title should be Jesse JUnit Book");
        assertEquals("Jesse Jittan", fetchBook.getAuthor(), "Book Author should be Jesse Jittan");
        assertEquals("Non-Fiction", fetchBook.getGenre(), "Book Genre should be Non-Fiction");

    }

    @Test
    @DisplayName("Remove Book By ID Test")
    void removeBookById_Test() {

        // As before, adds the newBook to the library and verifies that the addBook() works
        assertTrue(library.addBook(newBook), "Should return true if book has been added to the library.");

        // As before, verifies that the library 'contains' the newBook
        assertTrue(library.getAllBooks().contains(newBook), "Should return true if the new book is in the Library");

        // Calls removeBooksById() method from the Library clas, which expects an ID to remove
        //      > Calls the getId() method on the newBook obj to pass the book's ID
        //          > Stores the result in the variable isRemoved - either True or False
        boolean isRemoved = library.removeBookById(newBook.getId());

        // Verifies that the book was removed
        assertTrue(isRemoved, "removeBookById() should return true when the book is removed");

        // Checks to see if the book is no longer in the library, returns false if the book is no longer in the library.
        assertFalse(library.getAllBooks().contains(newBook), "Library should not have this book after it was removed");

    }

    @Test
    @DisplayName("Check Out Book Test")
    void checkOutBook_Test() {

        // Add book
        assertTrue(library.addBook(newBook), "addBook() should return true if book has been added to the Library");
        assertTrue(library.getAllBooks().contains(newBook), "Library should contain the newBook in the library");

        boolean isCheckedOut = library.checkOutBook(newBook.getId());

        // This verifies that the checkOutBook() method itself returned true, that the book was successfully checked out,
        //      and that the checkOutBook() method works.
        assertTrue(isCheckedOut, "checkOutBook() should return true if successfully checked out");

        // Retrieve the first (and only) book in index 0 and stores it in the variable fetchedBook
        Book fetchedBook = library.getAllBooks().get(0);

        // Verifies that the Book object is checked out, and ensures that the state of teh book in the library has been
        //      correctly modified by the checkOutBook() method
        assertTrue(fetchedBook.isCheckedOut(), "Book with this ID should be marked as checked out after checkOutBook() is called");
    }

    @Test
    @DisplayName("Check In Book Test")
    void checkInBook_Test() {

        // Step 1. Add book to the library
        assertTrue(library.addBook(newBook), "addBook() should return true if book has been added to the Library");
        assertTrue(library.getAllBooks().contains(newBook), "Library should contain the newBook in the library");

        // Step 2. Check out the book
        //          & Verify that it isCheckedOut
        boolean isCheckedOut = library.checkOutBook(newBook.getId());
        assertTrue(isCheckedOut, "checkOutBook should return true if book has been successfully checked out");

        // Step 3. Check in the book (the only book in the library that was created)
        //          & Verify that it isCheckedIn
        boolean isCheckedIn = library.checkInBook(newBook.getId());
        assertTrue(isCheckedIn, "checkInBook() should return true if successfully checked out");

        // Retrieve the first (and only) book and stores it in the variable fetchedBook
        //          & Verify that it is currently in the library (isCheckedIn) and is no longer marked as checked out.
        Book fetchedBook = library.getAllBooks().get(0);
        assertFalse(fetchedBook.isCheckedOut(), "Book should be marked as checked in after checkInBook() is called");

    }

    @Test
    void loadBooksFromFile() {
    }
}