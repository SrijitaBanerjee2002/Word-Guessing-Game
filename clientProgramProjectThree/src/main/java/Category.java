import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; // DO NOT REMOVE THIS LINE
	
	int num_animal_words;
	int num_country_words;
	int num_state_words;
	
	ArrayList<Character> animal_attempt = new ArrayList<Character>();
	ArrayList<Character> country_attempt = new ArrayList<Character>();
	ArrayList<Character> state_attempt = new ArrayList<Character>();
	
	char user_animal_input;
	char user_country_input;
	char user_state_input;
	
	String animal_clue;
	String country_clue;
	String state_clue;
	
	int category_animal_attempts_left;
	int category_country_attempts_left;
	int category_state_attempts_left;
	
	int inside_animal_attempts_left;
	int inside_country_attempts_left;
	int inside_state_attempts_left;
	
	int animal_mistake;
	int country_mistake;
	int state_mistake;
	
	int animal_finished;
	int country_finished;
	int state_finished;
	
	int animal_failed;
	int country_failed;
	int state_failed;
	
	String clientActivity;
	
	int animal_guessed_pressed;
	int state_guessed_pressed;
	int country_guessed_pressed;
	
	int winGame;
	int loseGame;
	
	int restartGame;
}