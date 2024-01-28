import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class serverTests {

    private Server server;

    @BeforeEach
    public void setUp() {
        
        server = new Server(callback -> {
        }, 8080);
    }

    @Test
    public void testServerInitialization() {
        assertNotNull(server);
        
    }
    
    @Test
    public void testChooseAnimal() {
        Server server = new Server(callback -> {}, 8080);
        server.generate_animals();

        // Choose an animal
        Animal chosenAnimal = server.choose_animal();

        assertNotNull(chosenAnimal);
        assertTrue(chosenAnimal.animal_name.length() > 0);
        assertFalse(server.animals.contains(chosenAnimal)); // Make sure it's removed from the list
    }
    
    @Test
    public void testChooseCountry() {
        Server server = new Server(callback -> {}, 8080);
        server.generate_countries();

        // Choose a country
        Country chosenCountry = server.choose_country();

        assertNotNull(chosenCountry);
        assertTrue(chosenCountry.country_name.length() > 0);
        assertFalse(server.countries.contains(chosenCountry)); // Make sure it's removed from the list
    }
    
    @Test
    public void testChooseState() {
        Server server = new Server(callback -> {}, 8080);
        server.generate_states();

        // Choose a state
        US_States chosenState = server.choose_state();

        assertNotNull(chosenState);
        assertTrue(chosenState.state_name.length() > 0);
        assertFalse(server.us_states.contains(chosenState)); // Make sure it's removed from the list
    }
    
    @Test
    public void testGenerateAnimals() {
        Server server = new Server(callback -> {}, 8080);

        // Generate animals
        server.generate_animals();

        // Check if the animals list is generated
        assertNotNull(server.animals);
        assertFalse(server.animals.isEmpty());
    }
    
    @Test
    public void testGenerateCountries() {
        Server server = new Server(callback -> {}, 8080);

        // Generate countries
        server.generate_countries();

        // Check if the countries list is generated
        assertNotNull(server.countries);
        assertFalse(server.countries.isEmpty());
    }
    
    @Test
    public void testGenerateStates() {
        Server server = new Server(callback -> {}, 8080);

        // Generate states
        server.generate_states();

        // Check if the states list is generated
        assertNotNull(server.us_states);
        assertFalse(server.us_states.isEmpty());
    }

}