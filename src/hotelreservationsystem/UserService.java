package hotelreservationsystem;

import hotelreservationsystem.enums.UserRole;
import hotelreservationsystem.exceptions.UserException;
import hotelreservationsystem.utils.InputValidator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users and authentication
 * 
 * @author Software Re-Engineering Enhancement
 */
public class UserService {
    private final List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
    }

    /**
     * Register a new user
     */
    public User registerUser(int userId, String username, String password, UserRole role) 
            throws UserException {
        if (findUserByUsername(username).isPresent()) {
            throw new UserException("Username already exists");
        }
        
        User user = new User(userId, username, password, role);
        users.add(user);
        return user;
    }

    /**
     * Authenticate a user
     */
    public User authenticate(String username, String password) throws UserException {
        if (!InputValidator.isValidString(username) || !InputValidator.isValidString(password)) {
            throw new UserException("Invalid credentials");
        }

        User user = findUserByUsername(username)
                .orElseThrow(() -> new UserException("User not found"));

        if (!user.verifyPassword(password)) {
            throw new UserException("Invalid password");
        }

        return user;
    }

    /**
     * Find user by username
     */
    private Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    /**
     * Get user by ID
     */
    public User getUserById(int userId) throws UserException {
        return users.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new UserException("User not found"));
    }

    /**
     * Get all users (admin only)
     */
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        return findUserByUsername(username).isPresent();
    }

    /**
     * Get next available user ID
     */
    public int getNextUserId() {
        return users.stream()
                .mapToInt(User::getUserId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Update user role (admin only)
     */
    public void updateUserRole(int userId, UserRole newRole) throws UserException {
        User user = getUserById(userId);
        user.setRole(newRole);
    }

    /**
     * Delete user (admin only)
     */
    public void deleteUser(int userId) throws UserException {
        boolean removed = users.removeIf(user -> user.getUserId() == userId);
        if (!removed) {
            throw new UserException("User not found");
        }
    }

    /**
     * Get user count
     */
    public int getUserCount() {
        return users.size();
    }
}
