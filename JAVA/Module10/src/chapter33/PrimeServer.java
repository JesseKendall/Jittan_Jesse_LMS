package chapter33;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PrimeServer extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
  
        while (true) {
          // Read the number sent by the client
          int number = inputFromClient.readInt();

          // Check if the number is prime
          String result = isPrime(number) ? "YES" : "NO";
  
          // Send area back to the client
          outputToClient.writeUTF(result);

          // Display activity on the server GUI
          Platform.runLater(() -> {
            ta.appendText("Number received from client: " + number + "\n");
            ta.appendText("Is Prime: " + result + "\n");
          });
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  // Method to check if a number is prime or not
  private boolean isPrime(int number) {
    if (number <= 1) return false;                      // Rule 1: Numbers <= 1 are NOT prime
    for (int i = 2; i <= Math.sqrt(number); i++) {      // Rule 2: Check divisors up to √number
      if (number % i == 0) return false;                // Rule 3: If divisible, it's NOT prime
    }
    return true;                                        // Rule 4: Otherwise, it's prime
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
