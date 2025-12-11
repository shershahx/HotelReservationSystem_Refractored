package hotelreservationsystem;

import hotelreservationsystem.enums.UserRole;
import hotelreservationsystem.utils.InputValidator;

/**
 * Represents a user in the Hotel Reservation System
 * 
 * Refactoring Techniques Applied:
 * - Replace Type Code with Enum (role field)
 * - Encapsulate Field (private fields with validation)
 * - Introduce Assertion (validation in setters)
 * - Self Encapsulate Field
 * 
 * @author Refactored by Software Re-Engineering
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private UserRole role;

    /**
     * Constructor with validation
     * Refactoring Technique: Introduce Assertion
     */
    public User(int userId, String username, String password, UserRole role) {
        if (!InputValidator.isValidId(userId)) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        if (!InputValidator.isValidString(username)) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (!InputValidator.isValidString(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters only - immutable userId
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Validates username before setting
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setUsername(String username) {
        if (!InputValidator.isValidString(username)) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    /**
     * Checks if provided password matches stored password
     * Refactoring Technique: Encapsulate Field - hide direct password access
     */
    public boolean verifyPassword(String password) {
        return this.password != null && this.password.equals(password);
    }

    /**
     * Updates password with validation
     * Refactoring Technique: Remove Setting Method (for password)
     * Now requires old password verification
     */
    public void changePassword(String oldPassword, String newPassword) {
        if (!verifyPassword(oldPassword)) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        if (!InputValidator.isValidString(newPassword)) {
            throw new IllegalArgumentException("New password cannot be null or empty");
        }
        this.password = newPassword;
    }

    public UserRole getRole() {
        return role;
    }

    /**
     * Validates role before setting
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setRole(UserRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
    }

    /**
     * Display user information (excluding password for security)
     * Refactoring Technique: Hide Method - password not exposed
     */
    @Override
    public String toString() {
        return "User " +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role=" + role;
    }
}

