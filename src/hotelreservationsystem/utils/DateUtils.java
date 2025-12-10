package hotelreservationsystem.utils;

import hotelreservationsystem.constants.AppConstants;
import java.util.Date;

/**
 * Utility class for date operations
 * Refactoring Technique: Extract Class
 * 
 * @author Refactored by Software Re-Engineering
 */
public final class DateUtils {
    
    // Private constructor to prevent instantiation
    private DateUtils() {
        throw new AssertionError("Cannot instantiate utility class");
    }
    
    /**
     * Calculates the number of days between two dates
     * Refactoring Technique: Extract Method
     * 
     * @param checkInDate the check-in date
     * @param checkOutDate the check-out date
     * @return number of days between dates
     */
    public static long calculateDaysBetween(Date checkInDate, Date checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        
        long timeDifference = checkOutDate.getTime() - checkInDate.getTime();
        return timeDifference / AppConstants.MILLISECONDS_PER_DAY;
    }
    
    /**
     * Validates that check-out date is after check-in date
     * Refactoring Technique: Introduce Assertion
     * 
     * @param checkInDate the check-in date
     * @param checkOutDate the check-out date
     * @return true if dates are valid, false otherwise
     */
    public static boolean isValidDateRange(Date checkInDate, Date checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            return false;
        }
        return checkOutDate.after(checkInDate);
    }
}
