/* Name: Jesse Jittan
Course: 202510 Software Development I CEN-3024C-14320
Date: 10/6/2024
Program Objective:
The Book class is blueprint that represents a single book in the Library Management System. It stores all the info
    related to a book such as ID, ISBN, Title, Author, Barcode, genre, and availability status.
 */

public class Book {
    private int id;
    private long isbn;
    private String title;
    private String author;
    private String genre;
    private boolean isCheckedOut; // Tracks the status of books checked out or checked in

    // Constructor
    public Book(int id, long isbn, String title, String author, String genre) {

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
        this.isCheckedOut = false; // Initialized as available (not checked out/checked In = true)
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

    @Override
    // Overriding the toString() method
    public String toString() {
        return "ID: " + id + "ISBN: " + isbn + ", Title: " + title + ", Author: " + author +
                "Genre: " + genre + "Status: " + (isCheckedOut ? "Checked Out" : "Available");

        /* I wanted to notate for myself the acutally turnary operator:(isCheckedOut ? "Checked Out" : "Available")
        and how it works.
        if (isCheckedOut) {
            return "Checked Out';
        } else {
            return "Available";

        If isCheckedOut is true - the statement in the curly braces will execute - since if statement conditions have
        to be true in order to execute. If isCheckedOut is false, the else block executes.
         */
    }
}