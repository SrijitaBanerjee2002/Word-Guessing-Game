import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server extends Thread {
	
	int numOfClients = 0; // Tracks the number of clients currently connected to the server
	private Consumer<Serializable> callback;
	TheServer server;
	ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
	int portNum;
	Category dataManager;
	Animal chosenAnimal;
	Country chosenCountry;
	US_States chosenState;
	
	ArrayList<Animal> animals;
	ArrayList<Country> countries;
	ArrayList<US_States> us_states;
	
	int num_animal_words;
	int num_country_words;
	int num_state_words;
	
	ArrayList<Character> animal_attempt = new ArrayList<Character>();
	ArrayList<Character> country_attempt = new ArrayList<Character>();
	ArrayList<Character> state_attempt = new ArrayList<Character>();
	
	int category_animal_attempts_left;
	int category_country_attempts_left;
	int category_state_attempts_left;
	
	String animal_clue;
	String country_clue;
	String state_clue;
	
	Server(Consumer<Serializable> call, int port) {
		
		callback = call;
		portNum = port;
		server = new TheServer();
		server.start();
		
		String welcome_message = "server: \\log> server started with port " + portNum + "."; // Fill in the first log of the server
		callback.accept(welcome_message);
		String welcome_message2 = "server: \\log> server is waiting for a client..."; // Fill in the second log of the server
		callback.accept(welcome_message2);
		
	}
	
	public Animal choose_animal() {
		// Before running this function, make sure that the size of the array being accessed is not 0
		// or else you will run into a Segmentation Fault
		// You will not need to check for duplication as this function already handles it.
		
		int randomIndex =  (int) ( Math.random() * animals.size() ); // This will return a random index
		Animal chosen_random_animal = animals.get(randomIndex); // Save the animal's values
		animals.remove(randomIndex); // Remove the animal from the ArrayList
		
		num_animal_words = chosen_random_animal.animal_name.length();
		
		int randomClueIdx = (int) (Math.random() * chosen_random_animal.animal_descriptions.size());
		animal_clue = chosen_random_animal.animal_descriptions.get(randomClueIdx);
		
		for (int i = 0; i < num_animal_words; i++) {
			animal_attempt.add('_');
		}
		
		String temp_log = "server: \\log> generated client" + (numOfClients - 1) + "'s animal";
		callback.accept(temp_log); // Places it back to the server's list view
		
		return chosen_random_animal;
	}
	
	public Country choose_country() {
		// Before running this function, make sure that the size of the array being accessed is not 0
		// or else you will run into a Segmentation Fault
		// You will not need to check for duplication as this function already handles it.
		
		int randomIndex = (int) ( Math.random() * countries.size() ); // This will return a random index
		Country chosen_random_country = countries.get(randomIndex); // Save the country's values;
		countries.remove(randomIndex); // Remove the country from the ArrayList
		
		num_country_words = chosen_random_country.country_name.length();
		
		int randomClueIdx = (int) (Math.random() * chosen_random_country.country_descriptions.size());
		country_clue = chosen_random_country.country_descriptions.get(randomClueIdx);
		
		for (int i = 0; i < num_country_words; i++) {
			country_attempt.add('_');
		}
		
		String temp_log = "server: \\log> generated client" + (numOfClients - 1) + "'s country";
		callback.accept(temp_log); // Places it back to the server's list view
		
		return chosen_random_country;
	}
	
	public US_States choose_state() {
		// Before running this function, make sure that the size of the array being accessed is not 0
		// or else you will run into a Segmentation Fault
		// You will not need to check for duplication as this function already handles it.
				
		int randomIndex = (int) ( Math.random() * us_states.size() ); // This will return a random index\
		US_States chosen_random_state = us_states.get(randomIndex); // Save the state's values
		us_states.remove(randomIndex); // Remove the state from the ArrayList
		
		num_state_words = chosen_random_state.state_name.length();
		
		int randomClueIdx = (int) (Math.random() * chosen_random_state.state_descriptions.size());
		state_clue = chosen_random_state.state_descriptions.get(randomClueIdx);
		
		for (int i = 0; i < num_state_words; i++) {
			state_attempt.add('_');
		}
		
		String temp_log = "server: \\log> generated client" + (numOfClients - 1) + "'s US State";
		callback.accept(temp_log); // Places it back to the server's list view
		
		return chosen_random_state;
	}
	
	public void generate_animals() {
		Animal zebra = new Animal();
		zebra.animal_descriptions = new ArrayList<String>();
		Animal monkey = new Animal();
		monkey.animal_descriptions = new ArrayList<String>();
		Animal lion = new Animal();
		lion.animal_descriptions = new ArrayList<String>();
		Animal dragon = new Animal();
		dragon.animal_descriptions = new ArrayList<String>();
		Animal unicorn = new Animal();
		unicorn.animal_descriptions = new ArrayList<String>();
		
		animals = new ArrayList<Animal>();
		
		zebra.animal_name = "Zebra";
		zebra.animal_descriptions.add("This animal is found in Africa");
		zebra.animal_descriptions.add("This animal has black and white stripes.");
		zebra.animal_descriptions.add("The pattern on its body is often used as a metaphor for opposites.");
		
		monkey.animal_name = "Monkey";
		monkey.animal_descriptions.add("This mammal is often seen swinging from trees and is known for its playful nature.");
		monkey.animal_descriptions.add("Some species of this animal have prehensile tails, allowing them to grasp objects.");
		monkey.animal_descriptions.add("Found in tropical forests, this animal is intelligent and known for its social behavior.");
		
		lion.animal_name = "Lion";
		lion.animal_descriptions.add("Often referred to as the king");
		lion.animal_descriptions.add("Found in grasslands and savannas, this social feline species is known for living in prides.");
		lion.animal_descriptions.add("This carnivorous big cat is a symbol of strength and courage, often associated with royalty.");
		
		dragon.animal_name = "Dragon";
		dragon.animal_descriptions.add("Mythical creatures often depicted with wings, scales, and the ability to breathe fire.");
		dragon.animal_descriptions.add("Legendary beings that appear in many cultures' folklore and are often associated with treasure hoards.");
		
		unicorn.animal_name = "Unicorn";
		unicorn.animal_descriptions.add("A mythical horse-like creature often depicted with a single, spiraled horn on its forehead");
		unicorn.animal_descriptions.add("Legendary beings known for their magical and gentle nature, often associated with purity and beauty.");
		
		animals.add(zebra);
		animals.add(monkey);
		animals.add(lion);
		animals.add(dragon);
		animals.add(unicorn);
	}
	
	public void generate_countries() {
		Country india = new Country();
		india.country_descriptions = new ArrayList<String>();
		Country indonesia = new Country();
		indonesia.country_descriptions = new ArrayList<String>();
		Country australia = new Country();
		australia.country_descriptions = new ArrayList<String>();
		Country singapore = new Country();
		singapore.country_descriptions = new ArrayList<String>();
		Country japan = new Country();
		japan.country_descriptions = new ArrayList<String>();
		
		countries = new ArrayList<Country>();
		
		india.country_name = "India";
		india.country_descriptions.add("This country is the seventh-largest in the world and is known for its diverse culture and traditions.");
		india.country_descriptions.add("The Taj Mahal is located in the northern part of this country.");
		india.country_descriptions.add("The most popular sport of this country is Cricket.");
		
		indonesia.country_name = "Indonesia";
		indonesia.country_descriptions.add("This is an archipelagic country.");
		indonesia.country_descriptions.add("The Komodo dragon is native to a few islands in this country.");
		indonesia.country_descriptions.add("The capital city of this Southeast Asian nation is located on the island of Java and is one of the most populous urban areas in the world.");
		
		australia.country_name = "Australia";
		australia.country_descriptions.add("This continent and country is known for its unique wildlife, including kangaroos and koalas.");
		australia.country_descriptions.add("The Great Barrier Reef, one of the world's largest coral reef systems, is located off the northeastern coast of this country.");
		australia.country_descriptions.add("The famous landmark known as Ayers Rock or Uluru is situated in the central part of this country.");
		
		singapore.country_name = "Singapore";
		singapore.country_descriptions.add("This city-state in Southeast Asia is known for its efficient and extensive public transportation system.");
		singapore.country_descriptions.add("Marina Bay Sands is located in the downtown area of this country.");
		singapore.country_descriptions.add("This nation is made up of one main island and 62 smaller islands, and it has a reputation for being a global financial hub.");
		
		japan.country_name = "Japan";
		japan.country_descriptions.add("Land of the rising sun, where cherry blossoms dance in the zephyr.");
		japan.country_descriptions.add("Archipelago adorned with sacred shrines and the art of origami.");
		japan.country_descriptions.add("Home to samurai echoes and bullet trains that whisper through neon-lit corridors.");
		
		countries.add(india);
		countries.add(indonesia);
		countries.add(australia);
		countries.add(singapore);
		countries.add(japan);
	}

	public void generate_states() {
		US_States illinois = new US_States();
		illinois.state_descriptions = new ArrayList<String>();
		US_States california = new US_States();
		california.state_descriptions = new ArrayList<String>();
		US_States texas = new US_States();
		texas.state_descriptions = new ArrayList<String>();
		US_States alabama = new US_States();
		alabama.state_descriptions = new ArrayList<String>();
		US_States michigan = new US_States();
		michigan.state_descriptions = new ArrayList<String>();
		
		us_states = new ArrayList<US_States>();
		
		illinois.state_name = "Illinois";
		illinois.state_descriptions.add("The Windy City state, where the skyline reflects on the waters of Lake Michigan.");
		illinois.state_descriptions.add("Abraham Lincoln's political roots run deep in this state, where the prairie blooms.");
		illinois.state_descriptions.add("Home to a university with an orange-and-blue legacy, nestled in the heart of the Midwest.");
		
		california.state_name = "California";
		california.state_descriptions.add("The Golden State, where Hollywood dreams shine under the Pacific sun.");
		california.state_descriptions.add("This state boasts the iconic redwood forests and the entertainment hub of Disneyland.");
		california.state_descriptions.add("Silicon Valley's tech heart pulsates in this diverse and geographically vast region.");
		
		texas.state_name = "Texas";
		texas.state_descriptions.add("The Lone Star State, where cowboy boots and BBQ are part of the cultural fabric.");
		texas.state_descriptions.add("Home to the Alamo and expansive landscapes, from the Gulf Coast to the Panhandle.");
		texas.state_descriptions.add("This state's capital, Austin, is known for live music scenes and a tech-savvy atmosphere.");
		
		alabama.state_name = "Alabama";
		alabama.state_descriptions.add("The Heart of Dixie, where the Civil Rights Movement took significant strides.");
		alabama.state_descriptions.add("Home to the rocket city, Huntsville, where NASA's Marshall Space Flight Center resides.");
		alabama.state_descriptions.add("This state, bordered by the Gulf of Mexico, is known for its southern hospitality.");
		
		michigan.state_name = "Michigan";
		michigan.state_descriptions.add("The Great Lakes State, where freshwater shores meet the automotive heartbeat.");
		michigan.state_descriptions.add("Home to the Motor City, where the hum of assembly lines once defined American industry.");
		michigan.state_descriptions.add("This state's upper and lower peninsulas are connected by the Mackinac Bridge.");
		
		us_states.add(illinois);
		us_states.add(california);
		us_states.add(texas);
		us_states.add(alabama);
		us_states.add(michigan);
	}
	
	public class TheServer extends Thread {
		
		public void run() {
			// run() is needed for TheServer.start()
			
			try ( ServerSocket mysocket = new ServerSocket(portNum); ) {
				System.out.println("Server is waiting for a client...");
			
				while ( true ) { // This will run the Server Application
					// Create a clientThread when accept() receives a connection
					
					numOfClients++; // Set number of clients to 1, anticipating a client connection
					ClientThread client = new ClientThread(mysocket.accept(), numOfClients);
				
					// Once code reaches this line, we have established a connection with a client
					// We can store the connected client (ClientThread) in an array
					clientList.add(client);
					
					callback.accept("Client has connected to the Server: " + "Client #" + numOfClients);
					System.out.println("Client has connected to the server! Yay!");
					
				
					client.start(); // This will trigger ClientThread.run()
				}
			
			} catch ( Exception e ) {
				callback.accept("Server Socket Did Not Launch");
			}
			
		}
		  
	}
	
	
	public class ClientThread extends Thread {
		
		// This sub-class will create the profile of the client
		
		Socket connection;
		int clientNumber;
		ObjectInputStream in;
		ObjectOutputStream out;
		
		ClientThread(Socket s, int count) {
			this.connection = s;
			this.clientNumber = count;	
		}
		
		public void starterData(int clientNumber) {
			// This function is used to send the first ever instance of Category to the client
			ClientThread temp = clientList.get(clientNumber - 1);
			
			generate_animals();
			generate_countries();
			generate_states();
			
			chosenAnimal = choose_animal();
			chosenCountry = choose_country();
			chosenState = choose_state();
			
			dataManager.num_animal_words = chosenAnimal.animal_name.length();
			dataManager.num_country_words = chosenCountry.country_name.length();
			dataManager.num_state_words = chosenState.state_name.length();
			dataManager.animal_clue = animal_clue;
			dataManager.country_clue = country_clue;
			dataManager.state_clue = state_clue;
			dataManager.num_animal_words = num_animal_words;
			dataManager.num_country_words = num_country_words;
			dataManager.num_state_words = num_state_words;
			dataManager.category_animal_attempts_left = 3;
			dataManager.category_country_attempts_left = 3;
			dataManager.category_state_attempts_left = 3;
			dataManager.inside_animal_attempts_left = 6;
			dataManager.inside_country_attempts_left = 6;
			dataManager.inside_state_attempts_left = 6;
			dataManager.animal_finished = 0;
			dataManager.country_finished = 0;
			dataManager.state_finished = 0;
			dataManager.animal_failed = 0;
			dataManager.country_failed = 0;
			dataManager.state_failed = 0;
			dataManager.animal_guessed_pressed = 0;
			dataManager.state_guessed_pressed = 0;
			dataManager.country_guessed_pressed = 0;
			
			
			for (int i = 0; i < num_animal_words; i++) {
				dataManager.animal_attempt.add(animal_attempt.get(i));
			}
			
			for (int i = 0; i < num_country_words; i++) {
				dataManager.country_attempt.add(country_attempt.get(i));
			}
			
			for (int i = 0; i < num_state_words; i++) {
				dataManager.state_attempt.add(state_attempt.get(i));
			}
			
			
			try {
				temp.out.writeObject(dataManager);
			}
			catch(Exception e) {}
			
			String temp_log = "server: \\log> delivered starter_information to client" + (numOfClients - 1);
			callback.accept(temp_log); // Places it back to the server's list view
		}
		
		public void checkStatus(int clientNumber) {
			// This function is used to check on the Client's status
			try {
//				for (int i = 0; i < dataManager.num_animal_words; i++) {
//					System.out.println("Server output: " + dataManager.animal_attempt.get(i));
//				}
				clientList.get(clientNumber - 1).out.writeObject(dataManager);
			}
			catch(Exception e) {}
		}
		
		public void animal_logic() {
			int match = 0;
			int finished = 1;
			
			for (int i = 0; i < dataManager.num_animal_words; i++) {
				if ( Character.toLowerCase(dataManager.user_animal_input) == Character.toLowerCase(chosenAnimal.animal_name.charAt(i)) ) {
					dataManager.animal_attempt.set(i, Character.toLowerCase(dataManager.user_animal_input) );
					match = 1;
					dataManager.animal_mistake = 0;
				}
			}
			String temp_log = "server: \\log> updated client " + clientNumber + "'s animal_attempt";
			callback.accept(temp_log); // Places it back to the server's list view
			
			for (int i = 0; i < dataManager.num_animal_words; i++) {
				if ( dataManager.animal_attempt.get(i) == '_') {
					finished = 0;
				}
			}
			
			if ( match == 0 ) {
				dataManager.inside_animal_attempts_left--;
				dataManager.animal_mistake = 1;
			}
			
			if (finished == 1) {
				dataManager.animal_finished = 1;
			}
			
			temp_log = "server: \\log> ran client " + clientNumber + "'s animal_logic";
			callback.accept(temp_log); // Places it back to the server's list view
		}
		
		public void animal_failed() {
			chosenAnimal = choose_animal();
			dataManager.num_animal_words = num_animal_words;
			
			String temp_log = "server: \\log> performed animal_failed";
			callback.accept(temp_log); // Places it back to the server's list view
			
			dataManager.animal_attempt.clear();
			for (int i = 0; i < num_animal_words; i++) {
				dataManager.animal_attempt.add('_');
			}
			
			dataManager.user_animal_input = 0;
			dataManager.animal_clue = animal_clue;
			dataManager.category_animal_attempts_left--;
			dataManager.inside_animal_attempts_left = 6;
			dataManager.animal_finished = 0;
			dataManager.animal_failed = 0;
		}
		
		public void state_logic() {
			int match = 0;
			int finished = 1;
			
			for (int i = 0; i < dataManager.num_state_words; i++) {
				if ( Character.toLowerCase(dataManager.user_state_input) == Character.toLowerCase(chosenState.state_name.charAt(i)) ) {
					dataManager.state_attempt.set(i, Character.toLowerCase(dataManager.user_state_input) );
					match = 1;
					dataManager.animal_mistake = 0;
				}
			}
			String temp_log = "server: \\log> updated client " + clientNumber + "'s state_input";
			callback.accept(temp_log); // Places it back to the server's list view
			
			for (int i = 0; i < dataManager.num_state_words; i++) {
				if ( dataManager.state_attempt.get(i) == '_') {
					finished = 0;
				}
			}
			
			if ( match == 0 ) {
				dataManager.inside_state_attempts_left--;
				dataManager.state_mistake = 1;
			}
			
			if (finished == 1) {
				dataManager.state_finished = 1;
			}
			
			temp_log = "server: \\log> ran client " + clientNumber + "'s state_logic";
			callback.accept(temp_log); // Places it back to the server's list view
		}
		
		public void state_failed() {
			chosenState = choose_state();
			dataManager.num_state_words = num_state_words;
			
			String temp_log = "server: \\log> performed state_failed";
			callback.accept(temp_log); // Places it back to the server's list view
			
			dataManager.state_attempt.clear();
			for (int i = 0; i < num_state_words; i++) {
				dataManager.state_attempt.add('_');
			}
			
			dataManager.user_state_input = 0;
			dataManager.state_clue = state_clue;
			dataManager.category_state_attempts_left--;
			dataManager.inside_state_attempts_left = 6;
			dataManager.state_finished = 0;
			dataManager.state_failed = 0;
		}
		
		public void country_logic() {
			int match = 0;
			int finished = 1;
			
			for (int i = 0; i < dataManager.num_country_words; i++) {
				if ( Character.toLowerCase(dataManager.user_country_input) == Character.toLowerCase(chosenCountry.country_name.charAt(i)) ) {
					dataManager.country_attempt.set(i, Character.toLowerCase(dataManager.user_country_input) );
					match = 1;
					dataManager.country_mistake = 0;
				}
			}
			String temp_log = "server: \\log> updated client " + clientNumber + "'s country_logic";
			callback.accept(temp_log); // Places it back to the server's list view
			
			for (int i = 0; i < dataManager.num_country_words; i++) {
				if ( dataManager.country_attempt.get(i) == '_') {
					finished = 0;
				}
			}
			
			if ( match == 0 ) {
				dataManager.inside_country_attempts_left--;
				dataManager.country_mistake = 1;
			}
			
			if (finished == 1) {
				System.out.println("Server: Reaching here");
				dataManager.country_finished = 1;
			}
			
			temp_log = "server: \\log> ran client " + clientNumber + "'s country_logic";
			callback.accept(temp_log); // Places it back to the server's list view
		}
		
		public void country_failed() {
			chosenCountry = choose_country();
			dataManager.num_country_words = num_country_words;
			
			String temp_log = "server: \\log> performed country_failed";
			callback.accept(temp_log); // Places it back to the server's list view
			
			dataManager.country_attempt.clear();
			for (int i = 0; i < num_country_words; i++) {
				dataManager.country_attempt.add('_');
			}
			
			dataManager.user_country_input = 0;
			dataManager.country_clue = country_clue;
			dataManager.category_country_attempts_left--;
			dataManager.inside_country_attempts_left = 6;
			dataManager.country_finished = 0;
			dataManager.country_failed = 0;
		}
		
		public void run() {
			
			try {
				// When Client Thread is running, initialize the stream variables
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				
				// This line just ensures that there isn't any delay when sending information between client and server
				connection.setTcpNoDelay(true);
			} catch (Exception e) {
				System.out.println("Failed on the streams");
			}
			
			dataManager = new Category();
			
			// Send clients the Category
			starterData(clientNumber); // This sends information to the server's output stream
			
			while ( true ) {
				// This will have to exist to keep checking if client sends anything to the server
				try {
					Category temp = (Category) in.readObject(); // Store the string sent from the client
					dataManager = temp;
					
					String temp_log = "server: \\log> client" + clientNumber + " sent: " + dataManager.clientActivity;
					callback.accept(temp_log); // Places it back to the server's list view
					
					temp_log = "server: \\log> received client" + clientNumber + "'s message";
					callback.accept(temp_log); // Places it back to the server's list view
					
					dataManager.clientActivity = "";
					
					if (dataManager.animal_guessed_pressed == 1) {
						if (dataManager.animal_failed != 1) {
							animal_logic();
						} else {
							animal_failed();
						}
						
						dataManager.animal_guessed_pressed = 0;
					}
					
					if ( dataManager.state_guessed_pressed == 1 ) {
						if (dataManager.state_failed != 1) {
							state_logic();
						} else {
							state_failed();
						}
						
						dataManager.state_guessed_pressed = 0;
					}
					
					if ( dataManager.country_guessed_pressed == 1 ) {
						if (dataManager.country_failed != 1) {
							country_logic();
						} else {
							country_failed();
						}
						
						dataManager.country_guessed_pressed = 0;
					}
					
					if ( (dataManager.animal_finished == 1) && (dataManager.country_finished == 1) && (dataManager.state_finished == 1) ) {
						dataManager.winGame = 1;
					}
					
					if ( (dataManager.category_animal_attempts_left == 0) && (dataManager.category_country_attempts_left == 0) && (dataManager.category_state_attempts_left == 0) ) {
						dataManager.loseGame = 1;
					}
					
					if (dataManager.restartGame == 1) {
						animals.clear();
						countries.clear();
						us_states.clear();
						
						generate_animals();
						generate_countries();
						generate_states();
						
						chosenAnimal = choose_animal();
						chosenCountry = choose_country();
						chosenState = choose_state();
						
						dataManager.animal_attempt.clear();
						dataManager.country_attempt.clear();
						dataManager.state_attempt.clear();
						
						dataManager.num_animal_words = chosenAnimal.animal_name.length();
						dataManager.num_country_words = chosenCountry.country_name.length();
						dataManager.num_state_words = chosenState.state_name.length();
						
						for (int i = 0; i < num_animal_words; i++) {
							dataManager.animal_attempt.add(animal_attempt.get(i));
						}
						
						for (int i = 0; i < num_country_words; i++) {
							dataManager.country_attempt.add(country_attempt.get(i));
						}
						
						for (int i = 0; i < num_state_words; i++) {
							dataManager.state_attempt.add(state_attempt.get(i));
						}
						
						dataManager.animal_clue = animal_clue;
						dataManager.country_clue = country_clue;
						dataManager.state_clue = state_clue;
						
						dataManager.num_animal_words = num_animal_words;
						dataManager.num_country_words = num_country_words;
						dataManager.num_state_words = num_state_words;
						
						dataManager.category_animal_attempts_left = 3;
						dataManager.category_country_attempts_left = 3;
						dataManager.category_state_attempts_left = 3;
						
						dataManager.inside_animal_attempts_left = 6;
						dataManager.inside_country_attempts_left = 6;
						dataManager.inside_state_attempts_left = 6;
						
						dataManager.animal_finished = 0;
						dataManager.country_finished = 0;
						dataManager.state_finished = 0;
						
						dataManager.animal_failed = 0;
						dataManager.country_failed = 0;
						dataManager.state_failed = 0;
						
						dataManager.animal_guessed_pressed = 0;
						dataManager.state_guessed_pressed = 0;
						dataManager.country_guessed_pressed = 0;
						
						dataManager.clientActivity = "";
						
						dataManager.winGame = 0;
						dataManager.loseGame = 0;
						
						dataManager.restartGame = 0;
						
						temp_log = "server: \\log> restarted client" + clientNumber;
						callback.accept(temp_log); // Places it back to the server's list view
					}
					
					checkStatus(clientNumber); // Checks to see if client is still connected
				} catch (Exception e) {
					// If this block is entered, a Client has disconnected from the server
					callback.accept("server: \\log> lost client#" + clientNumber);
					clientList.remove(this);
					System.out.println("Client#" + clientNumber + " has disconnected");
					numOfClients--;
			    	break;
				}
			}
			
		}
		
	}
	
		
}