package library;/* Name: Jesse Jittan
Course: 202510 Software Development I CEN-3024C-14320
Date: 10/6/2024
Program Objective:
The library.Book class is blueprint that represents a single book in the library.Library Management System. It stores all the info
    related to a book such as ID, ISBN, Title, Author, Barcode, genre, and availability status.
 */

public class Book {
    private int id;
    private long isbn;
    private String title;
    private String author;
    private String genre;
    private String status;              // Added this line: Reflects the database status i.e. Available or Checked Out
    private boolean isCheckedOut;       // Tracks the status of books checked out or checked in

    // Constructor
    public Book(int id, long isbn, String title, String author, String genre, String status) {

        this.id = id;

        // Converts the isbn to a string to verify its length before accepting it as an input.
        // ISBN is represented as a barcode of numbers, and cannot exceed 13 digits.
        String isbnCheck = String.valueOf(isbn);
        if (isbnCheck.length() > 13) {
            throw new IllegalArgumentException("ISBN cannot exceed 13 digits");
        }

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;                                           // Updated

        this.isCheckedOut = "Checked Out".equalsIgnoreCase(status);     // Updated: Set isCheckedOut based on the initial status
    }

    // Getter methods are accessors that allow other classes to 'access' the private field variables
    // Provides access but prevents direct modification of the variables
    public int getId() {
        return id;
    }

    public long getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    // Added in module 9 for Database: Getter and Setter for Status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.isCheckedOut = "Checked Out".equalsIgnoreCase(status);
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
   }

   // Methods to modify the state of the isCheckedOut variable, needed for updating the book's status
   //   within the LMS
   public void checkOut() {
        isCheckedOut = true;
   }

   public void checkIn() {
        isCheckedOut = false;
   }

    // Updated toString
    @Override
    public String toString() {
        return "ID: " + id + ", ISBN: " + isbn + ", Title: " + title + ", Author: " + author +
                ", Genre: " + genre + ", Status: " + status;
    }
}
