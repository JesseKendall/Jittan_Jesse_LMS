package library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryDatabaseHandler {

    // Connection object for interacting w/ the DB
    private Connection connection;

    // Constructor to establish a connection to the DB, it initializes the connection once an instance of the LibraryDatabaseHandler is created.
    // The constructor ensures that whenever an object of LibraryDatabaseHandler is created, the connection to the database is automatically established.
    public LibraryDatabaseHandler(String dbPath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);       // Establish a connection to my SQLite DB - w/ parameter of the path.
            System.out.println("Successfully connected to the database.");                          // Print success message

        } catch (
                SQLException e) {                                                                  // Handles any exception thrown during connection
            System.err.println("Failed to connect to the database: " + e.getMessage());             // Print error message if the connection fails
        }
    }

    public boolean addBook(Book book) {

        // An INSERT statement to add a new row to the books table. With each column name and the VALUES with placeholders (?)
        String query = "INSERT INTO books (id, isbn, title, author, genre, status, due_date) " +
                "VALUES (?, ?, ?, ?, ?, 'Available', NULL)";

        /* PreparedStatement allows the use of placeholders(?) in the query
                - Try with resource - resource declared within parenthesis, closes after use.
                - When the query is passed to connection.prepareStatement(query), the database gets involved for the first time,
                    without values assigned to the placeholders - this happens in the pstmt.set...
                - Once all placeholders are replaced with actual values (i.e. 1 placeholder to id, 2 placeholder to Isbn...
                - pstmt.executeUpdate(): the query is executed
        */
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, book.getId());                 // Sets the book ID
            pstmt.setLong(2, book.getIsbn());              // Sets the ISBN
            pstmt.setString(3, book.getTitle());
            pstmt.setString(4, book.getAuthor());
            pstmt.setString(5, book.getGenre());
            pstmt.executeUpdate();                                      // Executes the INSERT query

            return true;                                                // Indicates that the book was added successfully (returns to GUI class)

        } catch (SQLException e) {
            System.err.println("Adding book failed: " + e.getMessage());

            return false;                                               // Explicitly indicates the operation failed (returns to the GUI class)
        }
    }

    /*  Removes a book from the database by its ID.
            Returns true if the row was deleted
            Returns false if the deletion was unsuccessful or the ID does not exist (no matches found)
     */
    public boolean removeBookById(int id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {      // Just as the previous method - try w resource, and prepares the SQL query defined earlier for execution.
            pstmt.setInt(1, id);                                   // Replaces the first placeholder(?) in the query with the variable id - Set the book ID to be deleted

            // Execute the query - in this case the DELETE query.
            // executeUpdate() Returns an integer representing the number of rows affected
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;                                              // Returns true if at least one row was deleted (> 0)
        } catch (SQLException e) {
            System.err.println("Failed to remove book: " + e.getMessage());
            return false;
        }
    }

    // Retrieves a list of books from the database as a list of book objects List<book>
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();                                   // Creates an empty list to hold books, initializing it as soon as the getAllBooks() is called - ensuring that there is an empty list to hold books.
        String query = "SELECT * FROM books";                                   // SQL query to retrieve all books.

        // The semicolon (;) separates multiple resource declarations within the parentheses of the try block.
        // There are two separate resources, and the ; acts as a delimiter between them.
        try (Statement stmt = connection.createStatement();                     // First Res: Prepares the SQL query - Creates a Statement object to execute SQL queries.
             ResultSet rs = stmt.executeQuery(query)) {                          // Second Res: Executes the SQL query (SELECT * FROM books) and returns the results as a ResultSet.

            //      The ResultSet object (rs) contains the rows returned by the query.
            // Iterates through the result set and add books to the list (Retrieves the value of a specific column in the current row (i.e. rs.getX(columnName))
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getLong("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve books: " + e.getMessage());
        }

        return books;                                                              // Returns the list of books
    }

    // isCheckedOut() checks whether a specific book is checked out by looking at its status in the database - using bookId as input
    // If book isCheckedOut - method moves to checkInBook() and updates status in DB
    public boolean isCheckedOut(int id) {
        String query = "SELECT status FROM books WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {       // db gets involved for the first time, using connection.prepareStatement(query) w placeholder
            pstmt.setInt(1, id);                                    //  Replaces the ? placeholder in the SQL query with the actual bookId value provided as the method's parameter (sets placeholder 1 to id)

            ResultSet rs = pstmt.executeQuery();                                  // Sends the query to the database and retrieves the results in a ResultSet.

            return rs.next() && "Checked Out".equalsIgnoreCase(rs.getString("status"));         // If a row exists (rs.next() is true) and the status is "Checked Out", the method returns true

        } catch (SQLException e) {
            System.err.println("There is an error, checking out status: " + e.getMessage());
        }

        return false;
    }

    // Checks if a book is available based on its ID.
    // If book is Available - method moves to checkOutBook() and updates status in DB
    public boolean isCheckedIn(int id) {
        String query = "Select status FROM books WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);                    // Set's the id to be checked
            ResultSet rs = pstmt.executeQuery();                 // Executes the query
            return rs.next() && "Available".equalsIgnoreCase(rs.getString("status"));
        } catch (SQLException e) {
            System.err.println("There is an error, checking out status: " + e.getMessage());
        }

        return false;
    }

    // Checks in a book by updating its status and resetting due date
    public boolean checkInBook(int id) {
        String query = "UPDATE books SET status = 'Available', due_date = NULL " + "WHERE id = ? AND status = 'Checked Out'";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, id);                    // Sets the id (?)

            int rowsUpdated = pstmt.executeUpdate();             // Sends the SQL query to the db for execution (executes the query) updates all rows that match the 'WHERE' condition.

            return rowsUpdated > 0;                              // Checks if rowsUpdated is greater than 0 returns true (at least one row was updated) false if otherwise.

        } catch (SQLException e) {
            System.err.println("Failed check-in: " + e.getMessage());
        }

        return false;                                            // Returns false to indicate the operation failed.

    }

    // Checks out a book by updating its status and due date.
    public boolean checkOutBook(int id) {
        String query = "UPDATE books SET status = 'Checked Out', due_date = date('now', '+14 days') " + "WHERE id = ? AND status = 'Available'";        // Specifies the SQL query to update the book's status and due_date in the database.
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, id);                    // Replaces the 1 (?) placeholder with the actual value passed in.

            // Executes the SQL UPDATE query and returns the number of rows affected stored in the variable rowsUpdated - its expecting an int datatype
            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0;                               // Return true if at least one row was updated
        } catch (SQLException e) {
            System.err.println("Failed to check out book: " + e.getMessage());
        }

        return false;                                            // Returns false to indicate the operation failed.
    }

    // Closes the DB connection when not in use or no longer needed
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
        }
    }
}