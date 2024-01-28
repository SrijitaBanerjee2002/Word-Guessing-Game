import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class clientTests {

    @Test
    public void testAnimalCreation() {
        Animal animal = new Animal();

        assertNotNull(animal);
        assertNotNull(animal.animal_descriptions);
        assertTrue(animal.animal_descriptions.isEmpty());
    }
    
    @Test
    public void testEmptyAnimalDescriptions() {
        Animal animal = new Animal();

        assertTrue(animal.animal_descriptions.isEmpty());
    }
    
    @Test
    public void testCategoryInitialization() {
        Category category = new Category();
        assertEquals(0, category.num_animal_words);
        assertEquals(0, category.num_country_words);
        assertEquals(0, category.num_state_words);
        assertNotNull(category.animal_attempt);
        assertNotNull(category.country_attempt);
        assertNotNull(category.state_attempt);
        assertTrue(category.animal_attempt.isEmpty());
        assertTrue(category.country_attempt.isEmpty());
        assertTrue(category.state_attempt.isEmpty());
        assertEquals('\0', category.user_animal_input);
        assertEquals('\0', category.user_country_input);
        assertEquals('\0', category.user_state_input);
        assertNull(category.animal_clue);
        assertNull(category.country_clue);
        assertNull(category.state_clue);
        assertEquals(0, category.category_animal_attempts_left);
        assertEquals(0, category.category_country_attempts_left);
        assertEquals(0, category.category_state_attempts_left);
        assertEquals(0, category.inside_animal_attempts_left);
        assertEquals(0, category.inside_country_attempts_left);
        assertEquals(0, category.inside_state_attempts_left);
        assertEquals(0, category.animal_mistake);
        assertEquals(0, category.country_mistake);
        assertEquals(0, category.state_mistake);
        assertEquals(0, category.animal_finished);
        assertEquals(0, category.country_finished);
        assertEquals(0, category.state_finished);
        assertEquals(0, category.animal_failed);
        assertEquals(0, category.country_failed);
        assertEquals(0, category.state_failed);
        assertNull(category.clientActivity);
        assertEquals(0, category.animal_guessed_pressed);
        assertEquals(0, category.state_guessed_pressed);
        assertEquals(0, category.country_guessed_pressed);
        assertEquals(0, category.winGame);
        assertEquals(0, category.loseGame);
        assertEquals(0, category.restartGame);
    }
    
    @Test
    public void testClientInitialization() {
        Client client = new Client(data -> {}, 8080);
        assertNotNull(client.client);
        assertNull(client.socketClient);
        assertNull(client.out);
        assertNull(client.in);
        assertEquals(8080, client.port_number);
    }
    
    @Test
    public void testGameLogicInitialization() {
        GameLogic gameLogic = new GameLogic();
        assertNotNull(gameLogic.chosenWord);
        assertNotNull(gameLogic.userGuesses);
        assertTrue(gameLogic.chosenWord.isEmpty());
        assertTrue(gameLogic.userGuesses.isEmpty());
    }
    
    /*
    @Test
    public void testTheClientRunMethod() {
        
        Client client = new Client(data -> {}, 8080);
        client.client.run();

        
    }
    */
    
}
