package hotelreservationsystem.exceptions;

/**
 * Custom exception for reservation-related errors
 * Refactoring Technique: Replace Error Code with Exception
 * 
 * @author Refactored by Software Re-Engineering
 */
public class ReservationException extends Exception {
    
    public ReservationException(String message) {
        super(message);
    }
    
    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}
