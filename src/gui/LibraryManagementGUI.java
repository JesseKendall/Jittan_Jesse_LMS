package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
// Text area for displaying book list
import java.awt.event.ActionListener;
import java.util.List;

import library.Book;
// import library.Library;
import library.LibraryDatabaseHandler;

public class LibraryManagementGUI extends JFrame {

    private JPanel MainPanel;               // Main container panel for the GUI, holding all other components
    private JTextArea displayArea;          // Text area for displaying book list
    private JButton displayBooksButton;     // Button to display all books
    private JButton deleteBookById;         // Button to delete book
    private JTextField tfBookId;            // Text field for book id input
    private JLabel jlRemoveBookByID;        // Label for deleting book
    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton exitButton;
    private JLabel jlAddNewBook;
    private JTextField tfId;
    private JTextField tfIsbn;
    private JTextField tfBookTitle;
    private JTextField tfBookAuthor;
    private JTextField tfBookGenre;
    private JButton addBookButton;
    private JLabel jlId;
    private JLabel jlIsbn;
    private JLabel jlTitle;
    private JLabel jlAuthor;
    private JLabel jlGenre;
    private JTextField tfCheckOutId;
    private JTextField tfCheckInId;
    private JLabel jlCheckOutBookById;
    private JLabel jlCheckInBookById;

    private LibraryDatabaseHandler libraryDatabaseHandler;                // LibraryDatabaseHandler instance for managing books

    // Constructor
    public LibraryManagementGUI () {

        // creates a new instance of the LibraryDatabaseHandler class, initializes it with the specified database path
        //      (C:/sqlite/library.db), and assigns it to the variable libraryDatabaseHandler.
        libraryDatabaseHandler = new LibraryDatabaseHandler("C:/sqlite/library.db");
        setContentPane(MainPanel);
        setTitle("Jesse's Online Library");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);


        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 1. Read input from text field
                    int id = Integer.parseInt(tfId.getText().trim());  // Parse ID from the input text field
                    long isbn = Long.parseLong(tfIsbn.getText().trim()); // Parse ISBN from input
                    String title = tfBookTitle.getText().trim();                 // Get book title, trimmed of whitespace
                    String author = tfBookAuthor.getText().trim();               // Get book author entered in the text field
                    String genre = tfBookGenre.getText().trim();                 // Get book genre
                    String status = "Available";

                    // 2. Create a new Book object with the inputs from the text field
                    Book newBook = new Book(id, isbn, title, author, genre, status);

                    // 3. Adding the book to the DB using the LibraryDatabaseHandler - passing the newly created newBook obj as the argument - and attempts to add the new book to the DB
                    if (libraryDatabaseHandler.addBook(newBook)) {
                        // If DB returns true, show success message
                        JOptionPane.showMessageDialog(null, "Book added successfully.");

                        // Update the display area
                        updateDisplayArea(libraryDatabaseHandler.getAllBooks());

                        // Clear text field by calling the helper method clearTextFields()

                        // Failure to add book, message to inform user.
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add book. May already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Invalid ID or ISBN. Please enter a valid number to try again.", "Error.", JOptionPane.ERROR_MESSAGE);

                } catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteBookById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Try to parse the book ID that was entered in the text field (JTextField tfBookId)
                    int idToRemove = Integer.parseInt(tfBookId.getText().trim());

                    // Attempt to remove the book by the ID provided
                    if (libraryDatabaseHandler.removeBookById(idToRemove)) {
                        JOptionPane.showMessageDialog(null, "Book deleted successfully.");
                        updateDisplayArea((libraryDatabaseHandler.getAllBooks()));
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found.");
                    }

                // If the try block fails due to an invalid integer, a NumberFormatException is thrown, and jumps to the catch block.
                // REMEMBER: the catch block is outside the try block thus } closing curly brackets should end the if-else statement and begin the catch block.
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(tfCheckInId.getText().trim());

                    // Attempting to check in book by ID
                    if (libraryDatabaseHandler.checkInBook(id)) {
                        JOptionPane.showMessageDialog(null, "Book checked in successfully.");
                        updateDisplayArea(libraryDatabaseHandler.getAllBooks());

                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found or already checked in.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid ID, Please enter a valid number.", "Error: ", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(tfCheckOutId.getText().trim());    // Get the book ID from text field and trim() whitespace

                    // Attempting to check in book by title
                    if (libraryDatabaseHandler.checkOutBook(id)) {
                        JOptionPane.showMessageDialog(null, "Book checked out successfully.");

                        // Refresh the display area with the current information
                        updateDisplayArea(libraryDatabaseHandler.getAllBooks());

                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found or already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " +ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Button to display all books in the GUI
        // Action listener for displaying all books button
        displayBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (libraryDatabaseHandler.getAllBooks().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No books available to display.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        updateDisplayArea(libraryDatabaseHandler.getAllBooks());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Failed to retrieve books: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {                  // if the if statement evaluates to false, the program continues to run.

                    libraryDatabaseHandler.closeConnection();              // Close the database connection

                    System.exit(0);                                 // Exits the application
                }
            }
        });
    }

    // ***VERY IMPORTANT REMINDER: The updateDisplayArea() method is a separate method that belongs to the LibraryManagementGUI class, not the constructor.
    /* New Method: Update Display Area
        - Retrieve the list of all books in the Database (List<Book> as the argument
        - Formats that list as a single string (StringBuilder named bookList) w each book on a new line
        - Displays this formatted string in a text area (displayArea)
     */
    private void updateDisplayArea(List<Book> books) {
        StringBuilder bookList = new StringBuilder();
        for (Book book : books) {
            bookList.append(book.toString()).append("\n");      // toString() called on each Book obj, \n adds a newline after each book's details
                                                                //      and appends (adds) that to the bookList StringBuilder.
        }
        displayArea.setText(bookList.toString());
    }

    // A parameterized clear field method that allows specific buttons to clear - unique to each button's text field.
    // Allows each button to only clear the fields it interacts with.
    private void clearTextFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");                                  // Clear each field passed as an argument
        }
    }

    public static void main(String[] args) {
        new LibraryManagementGUI();
    }
}



