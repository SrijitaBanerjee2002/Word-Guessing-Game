import java.util.ArrayList;
import java.util.HashMap;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application {
	// Declaring general variables
	HashMap<String, Scene> sceneMap;
	String currentScene = "game_port_insert";
	int port_number;
	Client client;
	Category dataManager = new Category();
	
	
	// Declaring game_port_insert() variables
	TextField input_port;
	Button port_button;
	Text port_additional_text;
	
	// Declaring game_start_screen() variables
	Button play_button;
	Button how_to_play_button;
	Button exit_button;
	
	// Declaring how_to_play_screen() variables
	Button back_button;
	
	// Declaring choose_category_screen() variables
	Button animals;
	Button states;
	Button country;
	int num_animal_attempts;
	int num_state_attempts;
	int num_food_attempts;
	Text animal_attempts;
	Text state_attempts;
	Text country_attempts;
	Button proceed;
	
	// Declaring animal_guessing_scene() variables
	Text animal_hint;
	int animal_attempts_remaining;
	Text animal_attempts_remaining_text;
	ArrayList<Character> letters_guessed = new ArrayList<Character>();
	TextField animal_input;
	Button animal_guess_button;
	Text animal_feedback;
	Button animal_back_button;
	Text animal_guessed_display;
	
	// Declaring states_guessing_scene() variables
	Text states_hint;
	int states_attempts_remaining;
	Text states_attempts_remaining_text;
	ArrayList<Character> states_guessed = new ArrayList<Character>();
	TextField states_input;
	Button states_guess_button;
	Text states_feedback;
	Button states_back_button;
	Text states_guessed_display;
	
	// Declaring country_guessing_scene() variables
	Text country_hint;
	int country_attempts_remaining;
	Text country_attempts_remaining_text;
	ArrayList<Character> country_guessed = new ArrayList<Character>();
	TextField country_input;
	Button country_guess_button;
	Text country_feedback;
	Button country_back_button;
	Text country_guessed_display;
	
	// Declaring game_over_scene() variables
	Button end_play_again_button;
	Button end_quit_button;
	Text game_over_text;
	
	@Override
		public void start(Stage primaryStage) {
			// Initializing and setting up the variables
			sceneMap = new HashMap<String, Scene>();
			sceneMap.put("game_port_insert", game_port_insert());
			sceneMap.put("game_start_screen", game_start_screen());
			sceneMap.put("how_to_play_screen", how_to_play_screen());
			sceneMap.put("choose_category_screen", choose_category_screen());
			sceneMap.put("animal_guessing_scene", animal_guessing_scene());
			sceneMap.put("states_guessing_scene", states_guessing_scene());
			sceneMap.put("country_guessing_scene", country_guessing_scene());
			sceneMap.put("game_over_scene", game_over_scene());
			     
			primaryStage.setTitle("Hangman | Java Application");
			primaryStage.setScene(sceneMap.get("game_port_insert"));
			primaryStage.show();
			
			// --- Start of game_port_insert() Event Handlers ---
			
			port_button.setOnAction(e -> {
				// When the port_button is pressed
				// Save the port
				// Create a client
				// Connect to the server
				port_number = Integer.parseInt(input_port.getText());
				
				client = new Client(data-> {
					// In this implementation that I am doing, data will represent the Category class.
					// Category class is made as a Serializable class
					dataManager = (Category) data;
				}, port_number);
				
				primaryStage.setScene(sceneMap.get("game_start_screen"));
			});
			
			// --- End of game_port_insert() Event Handlers ---
			
			
			
			
			
			// --- Start of game_start_screen() Event Handlers ---
			
			exit_button.setOnAction(e -> {
				dataManager.clientActivity = "exiting";
				client.sendMessage(dataManager);
				
				Stage stage = (Stage) play_button.getScene().getWindow();
			    stage.close();
			});
			
			play_button.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("choose_category_screen"));
				animal_attempts.setText(dataManager.category_animal_attempts_left +" attempts");
				state_attempts.setText(dataManager.category_state_attempts_left +" attempts");
				country_attempts.setText(dataManager.category_country_attempts_left +" attempts");
			});
			
			how_to_play_button.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("how_to_play_screen"));
			});
			
			// --- End of game_start_screen() Event Handlers ---
			
			
			
			
			// --- Start of how_to_play_screen() Event Handlers ---
			
			back_button.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("game_start_screen"));
			});
			
			// --- End of how_to_play_screen() Event Handlers ---
			
			
			
			
			// --- Start of choose_category_screen() Event Handlers ---
			
			animals.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("animal_guessing_scene"));
				
				// Start updating the GUI with the appropriate variables to be used
				System.out.println(dataManager.animal_clue);
				animal_hint.setText(dataManager.animal_clue);
				
				animal_attempts_remaining_text.setText("You have " + dataManager.inside_animal_attempts_left + " attempts remaining.");
				animal_feedback.setText("Please type in a single character");
				
				String tempString = "Letters you have guessed correctly: ";
				for (int i = 0; i < dataManager.num_animal_words; i++) {
					tempString = tempString + dataManager.animal_attempt.get(i) + " ";
				}
				animal_guessed_display.setText(tempString);
				
				// Send message to the server of client's activity
				dataManager.clientActivity = "entered Animal scene";
				client.sendMessage(dataManager);
			});
			
			states.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("states_guessing_scene"));
				
				// Start updating the GUI with the appropriate variables to be used
				System.out.println(dataManager.state_clue);
				states_hint.setText(dataManager.state_clue);
				
				states_attempts_remaining_text.setText("You have " + dataManager.inside_state_attempts_left + " attempts remaining.");
				states_feedback.setText("Please type in a single character");
				
				String tempString = "Letters you have guessed correctly: ";
				for (int i = 0; i < dataManager.num_state_words; i++) {
					tempString = tempString + dataManager.state_attempt.get(i) + " ";
				}
				states_guessed_display.setText(tempString);
				
				// Send message to the server of client's activity
				dataManager.clientActivity = "entered US_State scene";
				client.sendMessage(dataManager);
			});
			
			country.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("country_guessing_scene"));
				
				// Start updating the GUI with the appropriate variables to be used
				System.out.println(dataManager.country_clue);
				country_hint.setText(dataManager.country_clue);
				
				country_attempts_remaining_text.setText("You have " + dataManager.inside_country_attempts_left + " attempts remaining.");
				country_feedback.setText("Please type in a single character");
				
				String tempString = "Letters you have guessed correctly: ";
				for (int i = 0; i < dataManager.num_country_words; i++) {
					tempString = tempString + dataManager.country_attempt.get(i) + " ";
				}
				country_guessed_display.setText(tempString);
				
				// Send message to the server of client's activity
				dataManager.clientActivity = "entered Country scene";
				client.sendMessage(dataManager);
			});
			
			proceed.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("game_over_scene"));
				
				if (dataManager.winGame == 1) {
					game_over_text.setText("Congratulations! You Win!");
				} else if (dataManager.loseGame == 1) {
					game_over_text.setText("Sorry! You Lose!");
				}
				
				dataManager.clientActivity = "Game Over!";
				client.sendMessage(dataManager);
			});
			
			// --- End of choose_category_screen() Event Handlers ---
			
			
			
			
			// --- Start of animal_guessing_scene() Event Handlers ---
			
			animal_back_button.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("choose_category_screen"));
				dataManager.clientActivity = "left Animal scene";
				client.sendMessage(dataManager);
				
				if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
					proceed.setDisable(false);
				}
			});
			
			animal_guess_button.setOnAction(e -> {
				
				int checkLength = animal_input.getText().length();
				dataManager.animal_guessed_pressed = 1;
				if ( checkLength == 1 ) {
					if (dataManager.inside_animal_attempts_left == 1) {
						// This is when the user has given a valid char, but only 1 attempt left
						// This condition will check whether the player makes another mistake and declare "failed"
						dataManager.user_animal_input = animal_input.getText().charAt(0);	
						dataManager.clientActivity = "" + animal_input.getText().charAt(0); // This is the equivalent of a Character.toString()
						
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("animal_guessing_scene")); // Helps update the dataManager
						
						// At this point, dataManager (Serializable class) should be up-to-date
						
						animal_attempts_remaining_text.setText("You have " + dataManager.inside_animal_attempts_left + " attempts remaining.");
						String tempString = "Letters you have guessed correctly: ";
						for (int i = 0; i < dataManager.num_animal_words; i++) {
							tempString = tempString + dataManager.animal_attempt.get(i) + " ";
						}
						animal_guessed_display.setText(tempString);
						animal_input.setText("");
						
						System.out.println("From GUI: " + dataManager.animal_finished);
						
						if (dataManager.animal_finished == 1) {
							animal_input.setEditable(false);
							animal_guess_button.setDisable(true);
							animal_feedback.setText("Congratulations detective, you have guessed the word! Please proceed to the other categories, I insist...");
							animal_attempts.setText("Finished");
							
							dataManager.clientActivity = "completed Animal category";
							client.sendMessage(dataManager);
						}
						
						if ( dataManager.inside_animal_attempts_left == 0 ) {
							dataManager.animal_failed = 1; // This will trigger animal_failed() function in the server
							dataManager.clientActivity = "failed Animal category";
							client.sendMessage(dataManager);
							
							primaryStage.setScene(sceneMap.get("choose_category_screen"));
							primaryStage.setScene(sceneMap.get("animal_guessing_scene"));
							primaryStage.setScene(sceneMap.get("choose_category_screen"));  // Changing scenes help update the dataManager
							
							animal_attempts.setText(dataManager.category_animal_attempts_left +" attempts");
							animal_input.setText("");
							
							if ( dataManager.category_animal_attempts_left == 0 ) {
								animals.setDisable(true);
								animal_attempts.setText("Failed");
								
								if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
									proceed.setDisable(false);
								}
								
								primaryStage.setScene(sceneMap.get("game_over_scene"));
								dataManager.loseGame = 1;
								
								if (dataManager.winGame == 1) {
									game_over_text.setText("Congratulations! You Win!");
								} else if (dataManager.loseGame == 1) {
									game_over_text.setText("Sorry! You Lose!");
								}
								
								dataManager.clientActivity = "Game Over!";
								client.sendMessage(dataManager);
							}
						}
						
					} else {
						// This is when the user inputs a valid char, and has many lives left
						
						dataManager.user_animal_input = animal_input.getText().charAt(0);
						dataManager.clientActivity = "" + animal_input.getText().charAt(0);
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("animal_guessing_scene"));
						
						// At this point, dataManager (Serializable class) should be up-to-date
						
						animal_attempts_remaining_text.setText("You have " + dataManager.inside_animal_attempts_left + " attempts remaining.");
						String tempString = "Letters you have guessed correctly: ";
						for (int i = 0; i < dataManager.num_animal_words; i++) {
							tempString = tempString + dataManager.animal_attempt.get(i) + " ";
						}
						animal_guessed_display.setText(tempString);
						animal_input.setText("");
						
						if (dataManager.animal_mistake == 1) {
							animal_feedback.setText("Letter is not in the word. Please try using another letter.");
							dataManager.animal_mistake = 0;
						} else {
							animal_feedback.setText("Please type in a single character.");
						}
						
						if (dataManager.animal_finished == 1) {
							animal_input.setEditable(false);
							animal_guess_button.setDisable(true);
							animal_feedback.setText("Congratulations detective, you have guessed the word! Please proceed to the other categories, I insist...");
							animal_attempts.setText("Finished");
							
							dataManager.clientActivity = "completed Animal category";
							client.sendMessage(dataManager);
						}
					}
				} else {
					if ( dataManager.inside_animal_attempts_left == 1 ) {
						// If this is entered, then user has entered an invalid char
						// and therefore create the "failed" scenario

						dataManager.animal_failed = 1; // Set the flag for animal_failed()
						dataManager.clientActivity = "failed Animal category";
						client.sendMessage(dataManager); // Triggers the animal_failed() in the server
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("animal_guessing_scene")); 
						primaryStage.setScene(sceneMap.get("choose_category_screen")); // Changing scenes help update the dataManager
						
						animal_attempts.setText(dataManager.category_animal_attempts_left +" attempts");
						animal_input.setText("");
						
						// By now, a new and fresh animal category should have been produced
						// with an updated category_animal_attempts_left ( where it has been reduced by 1)
						if ( dataManager.category_animal_attempts_left == 0 ) {
							animals.setDisable(true);
							animal_attempts.setText("Failed");
							
							if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
								proceed.setDisable(false);
							}
							
							primaryStage.setScene(sceneMap.get("game_over_scene"));
							dataManager.loseGame = 1;
							
							if (dataManager.winGame == 1) {
								game_over_text.setText("Congratulations! You Win!");
							} else if (dataManager.loseGame == 1) {
								game_over_text.setText("Sorry! You Lose!");
							}
							
							dataManager.clientActivity = "Game Over!";
							client.sendMessage(dataManager);
						}
					} else {
						// Invalid char but still many attempts
						dataManager.user_animal_input = '1';
						dataManager.clientActivity = "invalid answer format";
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("animal_guessing_scene"));
						
						if (dataManager.animal_mistake == 1) {
							animal_feedback.setText("Invalid answer format. Please type in only a single character.");
							dataManager.animal_mistake = 0;
						} else {
							animal_feedback.setText("Please type in a single character.");
						}
						
						animal_attempts_remaining_text.setText("You have " + dataManager.inside_animal_attempts_left + " attempts remaining.");
						animal_input.setText("");						
					}
				}
			});
			
			// --- End of animal_guessing_scene() Event Handlers ---
			
			
			
			
			// --- Start of country_guessing_scene() Event Handlers ---
			
			country_back_button.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("choose_category_screen"));
				dataManager.clientActivity = "left Category scene";
				client.sendMessage(dataManager);
				
				if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
					proceed.setDisable(false);
				}
			});
			
			country_guess_button.setOnAction(e -> {
				int checkLength = country_input.getText().length();
				dataManager.country_guessed_pressed = 1;
				if ( checkLength == 1 ) {
					if (dataManager.inside_country_attempts_left == 1) {
						// This is when the user has given a valid char, but only 1 attempt left
						// This condition will check whether the player makes another mistake and declare "failed"
						dataManager.user_country_input = country_input.getText().charAt(0);	
						dataManager.clientActivity = "" + country_input.getText().charAt(0); // This is the equivalent of a Character.toString()
						
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("country_guessing_scene")); // Helps update the dataManager
						
						// At this point, dataManager (Serializable class) should be up-to-date
						
						country_attempts_remaining_text.setText("You have " + dataManager.inside_country_attempts_left + " attempts remaining.");
						String tempString = "Letters you have guessed correctly: ";
						for (int i = 0; i < dataManager.num_country_words; i++) {
							tempString = tempString + dataManager.country_attempt.get(i) + " ";
						}
						country_guessed_display.setText(tempString);
						country_input.setText("");
						
						System.out.println("From GUI: " + dataManager.country_finished);
						
						if (dataManager.country_finished == 1) {
							country_input.setEditable(false);
							country_guess_button.setDisable(true);
							country_feedback.setText("Congratulations detective, you have guessed the word! Please proceed to the other categories, I insist...");
							country_attempts.setText("Finished");
							
							dataManager.clientActivity = "completed Country category";
							client.sendMessage(dataManager);
						}
						
						if ( dataManager.inside_country_attempts_left == 0 ) {
							dataManager.country_failed = 1; // This will trigger animal_failed() function in the server
							dataManager.clientActivity = "failed Country category";
							client.sendMessage(dataManager);
							
							primaryStage.setScene(sceneMap.get("choose_category_screen"));
							primaryStage.setScene(sceneMap.get("country_guessing_scene"));
							primaryStage.setScene(sceneMap.get("choose_category_screen"));  // Changing scenes help update the dataManager
							
							country_attempts.setText(dataManager.category_country_attempts_left +" attempts");
							country_input.setText("");
							
							if ( dataManager.category_country_attempts_left == 0 ) {
								country.setDisable(true);
								country_attempts.setText("Failed");
								
								if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
									proceed.setDisable(false);
								}
								
								primaryStage.setScene(sceneMap.get("game_over_scene"));
								dataManager.loseGame = 1;
								
								if (dataManager.winGame == 1) {
									game_over_text.setText("Congratulations! You Win!");
								} else if (dataManager.loseGame == 1) {
									game_over_text.setText("Sorry! You Lose!");
								}
								
								dataManager.clientActivity = "Game Over!";
								client.sendMessage(dataManager);
							}
						}
						
					} else {
						// This is when the user inputs a valid char, and has many lives left
						
						dataManager.user_country_input = country_input.getText().charAt(0);
						dataManager.clientActivity = "" + country_input.getText().charAt(0);
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("country_guessing_scene"));
						
						// At this point, dataManager (Serializable class) should be up-to-date
						
						country_attempts_remaining_text.setText("You have " + dataManager.inside_country_attempts_left + " attempts remaining.");
						String tempString = "Letters you have guessed correctly: ";
						for (int i = 0; i < dataManager.num_country_words; i++) {
							tempString = tempString + dataManager.country_attempt.get(i) + " ";
						}
						country_guessed_display.setText(tempString);
						country_input.setText("");
						
						if (dataManager.country_mistake == 1) {
							country_feedback.setText("Letter is not in the word. Please try using another letter.");
							dataManager.country_mistake = 0;
						} else {
							animal_feedback.setText("Please type in a single character.");
						}
						
						if (dataManager.country_finished == 1) {
							country_input.setEditable(false);
							country_guess_button.setDisable(true);
							country_feedback.setText("Congratulations detective, you have guessed the word! Please proceed to the other categories, I insist...");
							country_attempts.setText("Finished");
							
							dataManager.clientActivity = "completed Country category";
							client.sendMessage(dataManager);
						}
					}
				} else {
					if ( dataManager.inside_country_attempts_left == 1 ) {
						// If this is entered, then user has entered an invalid char
						// and therefore create the "failed" scenario

						dataManager.country_failed = 1; // Set the flag for animal_failed()
						dataManager.clientActivity = "failed Country category";
						client.sendMessage(dataManager); // Triggers the animal_failed() in the server
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("country_guessing_scene")); 
						primaryStage.setScene(sceneMap.get("choose_category_screen")); // Changing scenes help update the dataManager
						
						country_attempts.setText(dataManager.category_country_attempts_left +" attempts");
						country_input.setText("");
						
						// By now, a new and fresh animal category should have been produced
						// with an updated category_animal_attempts_left ( where it has been reduced by 1)
						if ( dataManager.category_country_attempts_left == 0 ) {
							country.setDisable(true);
							country_attempts.setText("Failed");
							
							if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
								proceed.setDisable(false);
							}
							
							primaryStage.setScene(sceneMap.get("game_over_scene"));
							dataManager.loseGame = 1;
							
							if (dataManager.winGame == 1) {
								game_over_text.setText("Congratulations! You Win!");
							} else if (dataManager.loseGame == 1) {
								game_over_text.setText("Sorry! You Lose!");
							}
							
							dataManager.clientActivity = "Game Over!";
							client.sendMessage(dataManager);
						}
					} else {
						// Invalid char but still many attempts
						dataManager.user_country_input = '1';
						dataManager.clientActivity = "invalid answer format";
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("country_guessing_scene"));
						
						if (dataManager.country_mistake == 1) {
							country_feedback.setText("Invalid answer format. Please type in a single character.");
							dataManager.country_mistake = 0;
						} else {
							country_feedback.setText("Please type in a single character.");
						}
						
						country_attempts_remaining_text.setText("You have " + dataManager.inside_country_attempts_left + " attempts remaining.");
						country_input.setText("");						
					}
				}
			});
			
			// --- End of country_guessing_scene() Event Handlers ---
			
			
			
			// --- Start of states_guessing_scene() Event Handlers ---
			
			states_back_button.setOnAction(e -> {
				primaryStage.setScene(sceneMap.get("choose_category_screen"));
				dataManager.clientActivity = "left State scene";
				client.sendMessage(dataManager);
				
				if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
					proceed.setDisable(false);
				}
			});
			
			states_guess_button.setOnAction(e -> {
				int checkLength = states_input.getText().length();
				dataManager.state_guessed_pressed = 1;
				if ( checkLength == 1 ) {
					if (dataManager.inside_state_attempts_left == 1) {
						// This is when the user has given a valid char, but only 1 attempt left
						// This condition will check whether the player makes another mistake and declare "failed"
						dataManager.user_state_input = states_input.getText().charAt(0);	
						dataManager.clientActivity = "" + states_input.getText().charAt(0); // This is the equivalent of a Character.toString()
						
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("states_guessing_scene")); // Helps update the dataManager
						
						// At this point, dataManager (Serializable class) should be up-to-date
						
						states_attempts_remaining_text.setText("You have " + dataManager.inside_state_attempts_left + " attempts remaining.");
						String tempString = "Letters you have guessed correctly: ";
						for (int i = 0; i < dataManager.num_state_words; i++) {
							tempString = tempString + dataManager.state_attempt.get(i) + " ";
						}
						states_guessed_display.setText(tempString);
						states_input.setText("");
						
						System.out.println("From GUI: " + dataManager.state_finished);
						
						if (dataManager.animal_finished == 1) {
							states_input.setEditable(false);
							states_guess_button.setDisable(true);
							states_feedback.setText("Congratulations detective, you have guessed the word! Please proceed to the other categories, I insist...");
							state_attempts.setText("Finished");
							
							dataManager.clientActivity = "completed US_States category";
							client.sendMessage(dataManager);
						}
						
						if ( dataManager.inside_animal_attempts_left == 0 ) {
							dataManager.state_failed = 1; // This will trigger animal_failed() function in the server
							dataManager.clientActivity = "failed US_States category";
							client.sendMessage(dataManager);
							
							primaryStage.setScene(sceneMap.get("choose_category_screen"));
							primaryStage.setScene(sceneMap.get("states_guessing_scene"));
							primaryStage.setScene(sceneMap.get("choose_category_screen"));  // Changing scenes help update the dataManager
							
							state_attempts.setText(dataManager.category_state_attempts_left +" attempts");
							states_input.setText("");
							
							if ( dataManager.category_state_attempts_left == 0 ) {
								states.setDisable(true);
								state_attempts.setText("Failed");
								
								if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
									proceed.setDisable(false);
								}
								
								primaryStage.setScene(sceneMap.get("game_over_scene"));
								dataManager.loseGame = 1;
								
								if (dataManager.winGame == 1) {
									game_over_text.setText("Congratulations! You Win!");
								} else if (dataManager.loseGame == 1) {
									game_over_text.setText("Sorry! You Lose!");
								}
								
								dataManager.clientActivity = "Game Over!";
								client.sendMessage(dataManager);
							}
						}
						
					} else {
						// This is when the user inputs a valid char, and has many lives left
						
						dataManager.user_state_input = states_input.getText().charAt(0);
						dataManager.clientActivity = "" + states_input.getText().charAt(0);
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_state_screen"));
						primaryStage.setScene(sceneMap.get("states_guessing_scene"));
						
						// At this point, dataManager (Serializable class) should be up-to-date
						
						states_attempts_remaining_text.setText("You have " + dataManager.inside_state_attempts_left + " attempts remaining.");
						String tempString = "Letters you have guessed correctly: ";
						for (int i = 0; i < dataManager.num_state_words; i++) {
							tempString = tempString + dataManager.state_attempt.get(i) + " ";
						}
						states_guessed_display.setText(tempString);
						states_input.setText("");
						
						if (dataManager.state_mistake == 1) {
							states_feedback.setText("Letter is not in the word. Please try using another letter.");
							dataManager.state_mistake = 0;
						} else {
							states_feedback.setText("Please type in a single character.");
						}
						
						if (dataManager.state_finished == 1) {
							states_input.setEditable(false);
							states_guess_button.setDisable(true);
							states_feedback.setText("Congratulations detective, you have guessed the word! Please proceed to the other categories, I insist...");
							state_attempts.setText("Finished");
							
							dataManager.clientActivity = "completed US_States category";
							client.sendMessage(dataManager);
						}
					}
				} else {
					if ( dataManager.inside_state_attempts_left == 1 ) {
						// If this is entered, then user has entered an invalid char
						// and therefore create the "failed" scenario
						System.out.println("Game entering here");

						dataManager.state_failed = 1; // Set the flag for animal_failed()
						dataManager.clientActivity = "failed US_States category";
						client.sendMessage(dataManager); // Triggers the animal_failed() in the server
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("states_guessing_scene")); 
						primaryStage.setScene(sceneMap.get("choose_category_screen")); // Changing scenes help update the dataManager
						
						state_attempts.setText(dataManager.category_state_attempts_left +" attempts");
						states_input.setText("");
						
						// By now, a new and fresh animal category should have been produced
						// with an updated category_animal_attempts_left ( where it has been reduced by 1)
						if ( dataManager.category_state_attempts_left == 0 ) {
							states.setDisable(true);
							state_attempts.setText("Failed");
							
							if ( (dataManager.winGame == 1) || (dataManager.loseGame == 1) ) {
								proceed.setDisable(false);
							}
							
							primaryStage.setScene(sceneMap.get("game_over_scene"));
							dataManager.loseGame = 1;
							
							if (dataManager.winGame == 1) {
								game_over_text.setText("Congratulations! You Win!");
							} else if (dataManager.loseGame == 1) {
								game_over_text.setText("Sorry! You Lose!");
							}
							
							dataManager.clientActivity = "Game Over!";
							client.sendMessage(dataManager);
						}
					} else {
						// Invalid char but still many attempts
						dataManager.user_state_input = '1';
						dataManager.clientActivity = "invalid answer format";
						client.sendMessage(dataManager);
						
						primaryStage.setScene(sceneMap.get("choose_category_screen"));
						primaryStage.setScene(sceneMap.get("states_guessing_scene"));
						
						if (dataManager.state_mistake == 1) {
							states_feedback.setText("Invalid answer format. Please type in a single character.");
							dataManager.state_mistake = 0;
						} else {
							states_feedback.setText("Please type in a single character.");
						}
						
						states_attempts_remaining_text.setText("You have " + dataManager.inside_state_attempts_left + " attempts remaining.");
						states_input.setText("");
					}
				}
			});
			
			// --- End of states_guessing_scene() Event Handlers ---
			
			
			
			// --- Start of game_over_scene() Event Handlers ---
			
			end_quit_button.setOnAction(e -> {
				dataManager.clientActivity = "exiting";
				client.sendMessage(dataManager);
				
				Stage stage = (Stage) end_quit_button.getScene().getWindow();
			    stage.close();
			});
			
			end_play_again_button.setOnAction(e -> {
				dataManager.restartGame = 1;
				dataManager.clientActivity = "restarting game";
				client.sendMessage(dataManager);
				
				primaryStage.setScene(sceneMap.get("game_start_screen"));
				primaryStage.setScene(sceneMap.get("game_over_scene"));
				primaryStage.setScene(sceneMap.get("game_start_screen"));
				
				animals.setDisable(false);
				animal_feedback.setText("Please type in a single character");
				animal_input.setEditable(true);
				animal_guess_button.setDisable(false);
				
				states.setDisable(false);
				states_feedback.setText("Please type in a single character");
				states_input.setEditable(true);
				states_guess_button.setDisable(false);
				
				country.setDisable(false);
				country_feedback.setText("Please type in a single character");
				country_input.setEditable(true);
				country_guess_button.setDisable(false);
				
				proceed.setDisable(true);
			});
			
			// --- End of game_over_scene() Event Handlers ---
		 
		 primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			 @Override
	         public void handle(WindowEvent t) {
				 Platform.exit();
				 System.exit(0);
			 }
		 });
	}
	
	public Scene game_port_insert() {
		Text server_home_title = new Text("Enter Server Port");
		input_port = new TextField();
		port_button = new Button("Run");
		port_additional_text = new Text("Please enter a port number to connect to a server");
		
		HBox temp1 = new HBox(10, input_port, port_button);
		VBox temp2 = new VBox(10, server_home_title, temp1, port_additional_text);
		
		BorderPane root = new BorderPane();
		
		temp1.setAlignment(Pos.CENTER);
		temp2.setAlignment(Pos.CENTER);
		root.setCenter(temp2);
		
		return new Scene(root, 700, 400);
	}
	
	public Scene game_start_screen() {
		Text welcome_title = new Text("Welcome to Hangman");
		play_button = new Button("Play");
		how_to_play_button = new Button("How To Play");
		exit_button = new Button("Exit");
		
		VBox temp1 = new VBox(10, welcome_title, play_button, how_to_play_button, exit_button); 
		BorderPane root = new BorderPane();
		
		temp1.setAlignment(Pos.CENTER);
		root.setCenter(temp1);
		
		return new Scene(root, 700, 400);
	}
	
	public Scene how_to_play_screen() {
		Text title = new Text("How To Play");
		back_button = new Button("Back");
		Text information = new Text();
		information.setText("Welcome to Hangman!\n"
				+ "\n"
				+ "The objective of this game is to guess the word correctly from all 3 categories with only 6 wrong letter\n"
				+ "guesses. You will have 3 attempts to guess the words for each category, while having a different word be\n"
				+ "given to you for each attempt.\n"
				+ "\n"
				+ "When you press \"Play\", you will be given 3 different categories to choose from.\n"
				+ "\n"
				+ "When you choose a category, the game will display the following information: the number of letters in your\n"
				+ "word, the number of guesses that you have left, and the positions of the correct letters guessed.\n"
				+ "\n"
				+ "This will be all the information you will receive to complete this game. May the smartest detective wins!");
		
		HBox temp1 = new HBox(400, title, back_button);
		VBox temp2 = new VBox(10, temp1, information);
		
		temp1.setAlignment(Pos.CENTER);
		temp2.setAlignment(Pos.CENTER);
		
		BorderPane root = new BorderPane();
		root.setCenter(temp2);
		
		return new Scene(root, 700, 400);
	}
	
	public Scene choose_category_screen() {
		Text title = new Text("Please choose a category");
		
		animals = new Button("Animals");
		animal_attempts = new Text();
		
		VBox temp3 = new VBox(10, animals, animal_attempts);
		
		states = new Button("US States");
		state_attempts = new Text();
		
		VBox temp4 = new VBox(10, states, state_attempts);
		
		country = new Button("Country");
		country_attempts = new Text();
		
		proceed = new Button("Proceed");
		proceed.setDisable(true);
		
		VBox temp5 = new VBox(10, country, country_attempts);
		
		HBox temp1 = new HBox(10, temp3, temp4, temp5);
		VBox temp2 = new VBox(10, title, temp1, proceed);
		
		
		
		BorderPane root = new BorderPane();
		root.setCenter(temp2);
		
		temp1.setAlignment(Pos.CENTER);
		temp2.setAlignment(Pos.CENTER);
		temp3.setAlignment(Pos.CENTER);
		temp4.setAlignment(Pos.CENTER);
		temp5.setAlignment(Pos.CENTER);
		
		return new Scene(root, 700, 400);
	}
	
	public Scene animal_guessing_scene() {
		Text topic_text = new Text();
		topic_text.setText("Given that you have chosen the Animal cateogry, I chose a word that would prove much difficulty for you to solve.\nTo not make this boring for me, I shall provide you with a clue:");
		
		animal_hint = new Text();
		animal_attempts_remaining_text = new Text();
		
		animal_guessed_display = new Text();
		
		animal_input = new TextField();
		animal_guess_button = new Button("Guess");
		animal_feedback = new Text("Please type in a single character");
		animal_back_button = new Button("Back");
		
		HBox temp1 = new HBox(10, animal_input, animal_guess_button);
		VBox temp2 = new VBox(10, topic_text, animal_hint, animal_attempts_remaining_text, animal_guessed_display, temp1, animal_feedback, animal_back_button);
		
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(45));
		root.setCenter(temp2);
		
		return new Scene(root, 700, 400);
	}
	
	public Scene states_guessing_scene() {
		Text topic_text = new Text();
		topic_text.setText("Given that you have chosen the US States cateogry, I chose a word that would prove much difficulty for you to solve.\nTo not make this boring for me, I shall provide you with a clue:");
		
		states_hint = new Text();
		states_attempts_remaining_text = new Text();
		states_attempts_remaining_text.setText("You have " + states_attempts_remaining + " attempts remaining.");
		
		states_guessed_display = new Text();
		states_guessed_display.setText("Letters you have guessed correctly: ");
		
		states_input = new TextField();
		states_guess_button = new Button("Guess");
		states_feedback = new Text("Please type in a single character");
		states_back_button = new Button("Back");
		
		HBox temp1 = new HBox(10, states_input, states_guess_button);
		VBox temp2 = new VBox(10, topic_text, states_hint, states_attempts_remaining_text, states_guessed_display, temp1, states_feedback, states_back_button);
		
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(45));
		root.setCenter(temp2);
		
		return new Scene(root, 700, 400);
	}
	
	public Scene country_guessing_scene() {
		Text topic_text = new Text();
		topic_text.setText("Given that you have chosen the Country cateogry, I chose a word that would prove much difficulty for you to solve.\nTo not make this boring for me, I shall provide you with a clue:");
		
		country_hint = new Text();
		country_attempts_remaining_text = new Text();
		country_attempts_remaining_text.setText("You have " + country_attempts_remaining + " attempts remaining.");
		
		country_guessed_display = new Text();
		country_guessed_display.setText("Letters you have guessed correctly: ");
		
		country_input = new TextField();
		country_guess_button = new Button("Guess");
		country_feedback = new Text("Please type in a single character");
		country_back_button = new Button("Back");
		
		HBox temp1 = new HBox(10, country_input, country_guess_button);
		VBox temp2 = new VBox(10, topic_text, country_hint, country_attempts_remaining_text, country_guessed_display, temp1, country_feedback, country_back_button);
		
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(45));
		root.setCenter(temp2);
		
		return new Scene(root, 700, 400);
	}
	
	public Scene game_over_scene() {
		game_over_text = new Text("Congratulations! You have finished the game");
		end_play_again_button = new Button("Play Again");
		end_quit_button = new Button("Quit");
		
		HBox temp1 = new HBox(10, end_play_again_button, end_quit_button);
		VBox temp2 = new VBox(10, game_over_text, temp1);
		
		BorderPane root = new BorderPane();
		root.setCenter(temp2);
		
		temp2.setAlignment(Pos.CENTER);
		temp1.setAlignment(Pos.CENTER);
		
		return new Scene(root, 700, 400);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
