package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
// Text area for displaying book list
import java.awt.event.ActionListener;
import java.io.IOException;

import library.Book;
import library.Library;

public class LibraryManagementGUI extends JFrame {

    private JLabel jlFileName;              // Label for file name
    private JTextField tfFileName;          // Text field for file name input
    private JButton loadFileButton;         // Button to load file
    private JPanel MainPanel;               // Main container panel for the GUI, holding all other components
    private JTextArea displayArea;          // Text area for displaying book list
    private JButton displayBooksButton;     // Button to display all books
    private JButton deleteBookById;         // Button to delete book
    private JTextField tfBookId;            // Text field for book id input
    private JLabel jlRemoveBookByID;        // Label for deleting book
    private JLabel jlCheckInBookByTitle;
    private JTextField tfCheckInBookTitle;
    private JButton checkInButton;
    private JLabel jlCheckOutBookByTitle;
    private JTextField tfCheckOutBookTitle;
    private JButton checkOutButton;
    private JButton exitButton;

    private Library library;                // Library instance for managing books

    // Constructor
    public LibraryManagementGUI () {
        library = new Library();            // Initialize the Library instance
        setContentPane(MainPanel);
        setTitle("Jesse's Online Library");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        // Action listener for loading file
        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = tfFileName.getText();         // Get the file name from the text field, and stores it in variable fileName

                try {
                    library.loadBooksFromFile(fileName);        // Calls the loadBooksFromFile method with fileName as the argument

                    // Shows a pop-up dialog box using JOptionPane to inform the user that the books were loaded successfully
                    // null as the first parameter centers the dialog box on the screen
                    JOptionPane.showMessageDialog(null, "Books loaded successfully.");

                    // Show the list of books in the GUI's display area as soon as the file is successfully loaded, not necessary but
                    //      wanted to add it for user experience.
                    updateDisplayArea();

                    // Handles file-related errors such as: File Not Found, Incorrect File Path, Permission Issues, File is Corrupt
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "File could not be loaded: " + ex.getMessage());

                    // Handles errors happen when the program can open the file but finds that the content inside is not formatted
                    //      or organized the way the program expects (i.e. Missing Info, Wrong Data Type, Field Inconsistencies)
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while trying to read the file: " + ex.getMessage());
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
                    if (library.removeBookById(idToRemove)) {
                        JOptionPane.showMessageDialog(null, "Book deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found.");
                    }

                // If the try block fails due to an invalid integer, a NumberFormatException is thrown, and jumps to the catch block.
                // REMEMBER: the catch block is outside the try block thus } closing curly brackets should end the if-else statement and begin the catch block.
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = tfCheckInBookTitle.getText().trim();     // Get the book title from text field and trim() whitespace

                // Attempting to check in book by title
                if (library.checkInBookByTitle(title)) {
                    JOptionPane.showMessageDialog(null, "Book checked in.");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found or already checked in.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = tfCheckOutBookTitle.getText().trim();    // Get the book title from text field and trim() whitespace

                // Attempting to check in book by title
                if (library.checkOutBookByTitle(title)) {
                    JOptionPane.showMessageDialog(null, "Book checked out successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found or already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Button to display all books in the GUI
        // Action listener for displaying all books button
        displayBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Check if there are any books to display
                if (library.getAllBooks().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No books available to display.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    updateDisplayArea();
                    JOptionPane.showMessageDialog(null, "Displaying all books.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);             // Closes and exits the app
                } else {
                    JOptionPane.showMessageDialog(null, "Exit canceled. You can continue.");
                }
            }
        });
    }

    /* New Method: Update Display Area
        - Retrieve the list of all books in the Library
        - Formats that list as a single string (StringBuilder named bookList) w each book on a new line
        - Displays this formatted string in a text area (displayArea)
     */
    private void updateDisplayArea() {
        StringBuilder bookList = new StringBuilder();
        for (Book book : library.getAllBooks()) {
            bookList.append(book.toString()).append("\n");      // toString() called on each Book obj, \n adds a newline after each book's details
                                                                //      and appends (adds) that to the bookList StringBuilder.
        }
        displayArea.setText(bookList.toString());
    }

    public static void main(String[] args) {
        new LibraryManagementGUI();
    }
}
