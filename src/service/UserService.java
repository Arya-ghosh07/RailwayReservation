package service;

import model.User;
import java.util.List;
import java.util.UUID;

/**
 * Handles user management, registration, and authentication logic.
 */
public class UserService {
    private List<User> users;
    private PersistenceService persistenceService;
    private User currentUser;

    public UserService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        this.users = persistenceService.loadUsers();
    }

    public boolean register(String username, String password, String name, model.Role role) {
        // Validate password
        if (password == null || password.length() < 8 || password.length() > 16) {
            throw new IllegalArgumentException("Password must be between 8 and 16 characters.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character.");
        }

        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("Username already taken.");
            }
        }
        
        // Create new user
        String id = UUID.randomUUID().toString();
        User newUser = new User(id, username, password, name, role);
        users.add(newUser);
        
        // Save to persistent storage
        persistenceService.saveUsers(users);
        return true;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                this.currentUser = user;
                return user;
            }
        }
        return null;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return users;
    }
}
