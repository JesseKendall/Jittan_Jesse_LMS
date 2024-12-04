package chapter33;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrimeClient extends Application {
  // IO streams
  DataOutputStream toServer = null;
  DataInputStream fromServer = null;

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Panel p to hold the label and text field
    BorderPane paneForTextField = new BorderPane();
    paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
    paneForTextField.setStyle("-fx-border-color: green");
    paneForTextField.setLeft(new Label("Enter a number to test: "));
    
    TextField tf = new TextField();
    tf.setAlignment(Pos.BOTTOM_RIGHT);
    paneForTextField.setCenter(tf);
    
    BorderPane mainPane = new BorderPane();
    // Text area to display contents
    TextArea ta = new TextArea();
    mainPane.setCenter(new ScrollPane(ta));
    mainPane.setTop(paneForTextField);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(mainPane, 450, 200);
    primaryStage.setTitle("Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    tf.setOnAction(e -> {
      try {
        // Get number from the text field
        int number = Integer.parseInt(tf.getText().trim());

        // Send the number to the server
        toServer.writeInt(number);
        toServer.flush();

        // Get the response from the server
        String response = fromServer.readUTF();

        // Display input and response
        ta.appendText("Number: " + number + "\n");
        ta.appendText("Is Prime: " + (response.equals("YES") ? "Yes" : "No") + "\n");
      } catch (IOException ex) {
        ta.appendText("Error: " + ex.getMessage() + "\n");
      } catch (NumberFormatException ex) {
        ta.appendText("Invalid input. Please enter an integer.\n");
      }
    });

    try {
      // Create a socket to connect to the server
      Socket socket = new Socket("localhost", 8000);

      // Create input and output streams
      fromServer = new DataInputStream(socket.getInputStream());
      toServer = new DataOutputStream(socket.getOutputStream());
    } catch (IOException ex) {
      ta.appendText("Error connecting to server: " + ex.getMessage() + "\n");
    }
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
