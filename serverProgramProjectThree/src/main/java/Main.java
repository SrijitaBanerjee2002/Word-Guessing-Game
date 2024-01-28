import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class Main extends Application {
	// Declaring the general variables
	HashMap<String, Scene> sceneMap;
	String currentScene = "server_lobby";
	int port_number;
	Server server;
	
	// Declaring server_lobby variables
	TextField input_port;
	Button port_button;
	Text port_additional_text;
	
	// Declaring server_logs variables
	ListView<String> list_of_logs = new ListView<String>();
	
	@Override
	public void start(Stage primaryStage) {
		// Initializing and setting up the variables
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("server_lobby", server_lobby());
		sceneMap.put("server_logs", server_logs());
	     
	    primaryStage.setTitle("Hangman | Server Control");
        primaryStage.setScene(sceneMap.get("server_lobby"));
        primaryStage.show();
        
        // --- Server Lobby Event Handlers ---
        
        port_button.setOnAction(e -> {
        	// If port_button is pressed,
        	// Save the port value
        	// Change scene to 'server_logs'
        	// Add a start message to list_of_logs
        	// Start the server
        	
        	port_number = Integer.parseInt(input_port.getText()); // Saves the port number
        	
        	primaryStage.setScene(sceneMap.get("server_logs")); // Move the scene
        	
        	// Starting the server with port_number
        	// When updating UI with JavaFX, use Platform.runLater() to remove potential warnings.
        	// This is because the user interface cannot be directly updated from a non-application thread.
        	server = new Server(data -> {
        		Platform.runLater(
        			() -> {
        				list_of_logs.getItems().add(data.toString());
        			}
        		);
        	}, port_number);
        });
        
        // --- End Of Server Lobby Event Handlers ---
		
        // Do not remove this setOnCloseRequest!
        // It's purpose is to "close" the port when the server is closed.
        // Deleting this will leave the port "open" when the server is closed.
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				 Platform.exit();
				 System.exit(0);
			 }
		 });
	}
	
	public Scene server_lobby() {
		Text server_home_title = new Text("Enter The Server Port");
		input_port = new TextField();
		port_button = new Button("Run");
		port_additional_text = new Text("Please enter a port number to run the server");
		
		HBox temp1 = new HBox(10, input_port, port_button);
		VBox temp2 = new VBox(10, server_home_title, temp1, port_additional_text);
		
		BorderPane root = new BorderPane();
		
		temp1.setAlignment(Pos.CENTER);
		temp2.setAlignment(Pos.CENTER);
		root.setCenter(temp2);
		
		return new Scene(root, 500, 400);
	}
	
	public Scene server_logs() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(70));
		root.setStyle("-fx-background-color: #FEFEFE");
		
		root.setCenter(list_of_logs);
	
		return new Scene(root, 500, 400);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
