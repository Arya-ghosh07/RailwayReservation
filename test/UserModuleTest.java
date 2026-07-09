import model.Role;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FilePersistenceService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserModuleTest {

    private UserService userService;
    private FilePersistenceService persistenceService;

    @BeforeEach
    public void setUp() {
        // Initialize services before each test
        persistenceService = new FilePersistenceService();
        userService = new UserService(persistenceService);
    }

    @Test
    public void testUserRegistration() {
        int initialSize = userService.getAllUsers().size();
        
        // Use a unique username for testing to avoid conflicts in file persistence
        String testUser = "testuser_" + System.currentTimeMillis();
        boolean registered = userService.register(testUser, "password123", "Test User", Role.USER);
        
        assertTrue(registered, "User should be registered successfully");
        assertEquals(initialSize + 1, userService.getAllUsers().size(), "User count should increase by 1");
        
        // Attempt duplicate registration with the same username
        boolean registeredAgain = userService.register(testUser, "password123", "Test User", Role.USER);
        assertFalse(registeredAgain, "Duplicate username registration should fail and return false");
    }

    @Test
    public void testUserLogin() {
        // First register a unique user for this test
        String testUser = "loginuser_" + System.currentTimeMillis();
        userService.register(testUser, "loginpass", "Login User", Role.USER);
        
        // Attempt login with valid credentials
        User loggedInUser = userService.login(testUser, "loginpass");
        assertNotNull(loggedInUser, "Login should be successful for valid credentials");
        assertEquals(testUser, loggedInUser.getUsername(), "Logged in user's username should match");
        
        // Attempt login with invalid credentials
        User failedLogin = userService.login(testUser, "wrongpass");
        assertNull(failedLogin, "Login should return null for invalid credentials");
    }
}
