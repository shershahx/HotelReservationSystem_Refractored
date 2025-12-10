package hotelreservationsystem.exceptions;

/**
 * Custom exception for room-related errors
 * Refactoring Technique: Replace Error Code with Exception
 * 
 * @author Refactored by Software Re-Engineering
 */
public class RoomException extends Exception {
    
    public RoomException(String message) {
        super(message);
    }
    
    public RoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
