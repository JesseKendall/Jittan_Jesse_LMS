/* Name: Jesse Jittan
Course: 202510 Software Development I CEN-3024C-14320
Date: 9/8/2024
Program Objective:
The Book class is a simple representation of a book object. It holds data related to the book, and provides methods
to access that book's information. It manages the books in the library.
 */

public class Book {
    private int id;
    private String title;
    private String author;

    // Constructor
    public Book (int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    // Getter method for id - specifically included because it is needed to access each book's id to compare
    //  it with the id provided, and because the id field is private, we can't access it directly from outside
    //  the Book class.
    public int getID() {
        return id;
    }

    @Override
    // Overriding the toString() method
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author;
    }
}