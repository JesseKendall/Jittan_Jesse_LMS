/* Name: Jesse Jittan
Course: 202510 Software Development I CEN-3024C-14320
Date: 10/6/2024
Program Objective:
The Library class is responsible for managing the collection of books in the LMS. It will handle functions like loading
books, adding books, removing books, and checking out or checking in books.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Library {

    // private field variable (called books) that holds Book objects in the form of a list.
    // It is a collection (list) that stores multiple Book objects in the variable 'books'

    private List<Book> books;

    // Constructor that initializes the Library obj. It sets up the books list as an empty ArrayList so you can later
    //      add, remove, or retrieve books from it.
    // Sets every new Library instance to an empty list

    public Library() {
        this.books = new ArrayList<>();
    }

    // Method: Loads books from a text file and adds the books to the library's collection.
    // It takes a String argument that specifies the path to the text file containing the book data.

    public void loadBooksFromFile(String fileName) {
        // Try with resources statement - allows the BR to close automatically without using a finally block.
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;    // variable named line that would hold the text data that will be read from a file (of type String)

            /* br.readLine() reads one line at a time from the file and assigns it to the variable line. Splitting that line
                    into and array of strings using a comma. Separating into id, title, and author
               The while loop continues as long as the line is not null. Once it becomes null, the loop stops.
               Once each line is verified - that it has exactly 3 elements, and converted to the appropriate type -
                    it assigns it to the corresponding variable.
               A new Book obj is instantiated by calling the constructor of the book class and passing in the values as
                    arguments (id, title, author) which is then added to the 'books' list
            */
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(",");
                if (bookData.length == 3) {     // Ensure the line has exactly 3 parts
                    int id = Integer.parseInt(bookData[0]);     // Convert id from String to int
                    String title = bookData[1];
                    String author = bookData[2];

                    Book book = new Book(id, title, author); // create a new Book object and add it to the books list
                    books.add(book);
                }
            }
        // Handle IOException specifically (file not found, file read error)
        } catch (IOException e) {
            System.out.println("An error has occurred while reading teh file: " + e.getMessage());

        // Handle any other unexpected exceptions
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
            e.printStackTrace(); // Prints the stack trace to help with debugging.
        }
    }

    // Method to remove a book from the library by its id and return a current list of Book objects
    public boolean removeBookById(int id) {

        // the for each loop iterates over each Book obj in the books list, and check one by one to see if it matches the
        //      id provided
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                return true;
            }
        }
        // book not found
        return false;
    }

    // Method to return/display the list of books in the library
    public List<Book> getBooks() {
        return books;
    }
}

