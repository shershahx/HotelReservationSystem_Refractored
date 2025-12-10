package hotelreservationsystem.utils;

/**
 * Utility class for input validation
 * Refactoring Technique: Extract Class
 * 
 * @author Refactored by Software Re-Engineering
 */
public final class InputValidator {
    
    // Private constructor to prevent instantiation
    private InputValidator() {
        throw new AssertionError("Cannot instantiate utility class");
    }
    
    /**
     * Validates that an ID is positive
     * Refactoring Technique: Extract Method
     */
    public static boolean isValidId(int id) {
        return id > 0;
    }
    
    /**
     * Validates that a price is positive
     * Refactoring Technique: Extract Method
     */
    public static boolean isValidPrice(double price) {
        return price > 0;
    }
    
    /**
     * Validates that a string is not null or empty
     * Refactoring Technique: Extract Method
     */
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
