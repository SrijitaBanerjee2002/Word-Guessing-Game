import java.util.ArrayList;

public class GameLogic {
	// Character is the equivalent of char when declaring
	ArrayList<Character> chosenWord; // This will store the chosen word for a particular category
	ArrayList<Character> userGuesses; // This will store the user guesses
	
	// Note:
	// To add char(s) from a string to ArrayList<Character>, you can try using this:
	// for (int i = 0; i < string.length(); i++) {
	//		chosenWord.add( string.charAt(i) );
	// }
	
	GameLogic() {
		chosenWord = new ArrayList<Character>();
		userGuesses = new ArrayList<Character>();
	}
	
	public void word_to_be_guessed(String word_from_category) {
		// This is a "setter" function
		for (int i = 0; i < word_from_category.length(); i++) {
			chosenWord.add( word_from_category.charAt(i) );
		}
	}
	
	public Boolean user_attempt(Character letter_from_user) {
		// This is a "setter" function
		
		// This loop will fill the userGuesses with '_'
		// After the loop ends, userGuesses will have the same size as chosenWord.
		for (int i = 0; i < chosenWord.size(); i++) {
			userGuesses.add('_');
		}
		
		// TODO: This function is used to store the user's input in an array called userGuesses.
		// TODO: Add all occurrences of letter_from_user in the correct indexes in userGuesses.
		// TODO: If user guesses the right letter then return true, else return false.
		
		return false;
	}
	
	public Boolean word_has_been_guessed() {
		// This function will return true if user has guessed the word, else will return false
		
		for (int i = 0; i < chosenWord.size(); i++) {
			if (userGuesses.get(i) != chosenWord.get(i) ) {
				return false;
			}
		}
		
		return true;
	}
}
