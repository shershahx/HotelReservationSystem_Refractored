package hotelreservationsystem.constants;

/**
 * Application-wide constants for the Hotel Reservation System
 * Refactoring Technique: Replace Magic Number with Symbolic Constant
 * 
 * @author Refactored by Software Re-Engineering
 */
public final class AppConstants {
    
    // Date/Time Constants
    public static final long MILLISECONDS_PER_DAY = 1000L * 60 * 60 * 24;
    
    // Default Room Configuration
    public static final int DEFAULT_SINGLE_ROOM_ID = 1;
    public static final int DEFAULT_DOUBLE_ROOM_ID = 2;
    public static final int DEFAULT_SUITE_ROOM_ID = 3;
    
    public static final double DEFAULT_SINGLE_ROOM_PRICE = 100.0;
    public static final double DEFAULT_DOUBLE_ROOM_PRICE = 150.0;
    public static final double DEFAULT_SUITE_ROOM_PRICE = 300.0;
    
    // Room Type Constants
    public static final String ROOM_TYPE_SINGLE = "Single";
    public static final String ROOM_TYPE_DOUBLE = "Double";
    public static final String ROOM_TYPE_SUITE = "Suite";
    
    // Menu Options
    public static final int MENU_VIEW_ROOMS = 1;
    public static final int MENU_ADD_ROOM = 2;
    public static final int MENU_MAKE_RESERVATION = 3;
    public static final int MENU_CANCEL_RESERVATION = 4;
    public static final int MENU_VIEW_RESERVATIONS = 5;
    public static final int MENU_EXIT = 6;
    
    // Error Messages
    public static final String ERROR_ROOM_NOT_FOUND = "Room not found";
    public static final String ERROR_RESERVATION_NOT_FOUND = "Reservation not found";
    public static final String ERROR_ROOM_NOT_AVAILABLE = "Room is not available";
    public static final String ERROR_INVALID_DATE_RANGE = "Check-out date must be after check-in date";
    public static final String ERROR_INVALID_PRICE = "Price must be greater than zero";
    public static final String ERROR_INVALID_ROOM_ID = "Room ID must be positive";
    public static final String ERROR_INVALID_RESERVATION_ID = "Reservation ID must be positive";
    
    // Success Messages
    public static final String SUCCESS_ROOM_ADDED = "Room added successfully!";
    public static final String SUCCESS_RESERVATION_MADE = "Reservation made successfully!";
    public static final String SUCCESS_RESERVATION_CANCELED = "Reservation canceled successfully!";
    
    // Private constructor to prevent instantiation
    private AppConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
