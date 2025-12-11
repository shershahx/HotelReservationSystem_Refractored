package hotelreservationsystem.exceptions;

/**
 * Custom exception for user-related errors
 * 
 * @author Software Re-Engineering Enhancement
 */
public class UserException extends Exception {
    
    public UserException(String message) {
        super(message);
    }
    
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
