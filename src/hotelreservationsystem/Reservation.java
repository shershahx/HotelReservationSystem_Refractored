package hotelreservationsystem;

import hotelreservationsystem.constants.AppConstants;
import hotelreservationsystem.utils.DateUtils;
import hotelreservationsystem.utils.InputValidator;
import java.util.Date;

/**
 * Represents a hotel reservation
 * 
 * Refactoring Techniques Applied:
 * - Encapsulate Field (private fields with validation)
 * - Introduce Assertion (validation in setters and constructor)
 * - Self Encapsulate Field
 * - Replace Data Value with Object (enhanced behavior)
 * 
 * @author Refactored by Software Re-Engineering
 */
public class Reservation {
    private int reservationId;
    private int userId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalCost;

    /**
     * Constructor with validation
     * Refactoring Technique: Introduce Assertion
     */
    public Reservation(int reservationId, int userId, int roomId, 
                      Date checkInDate, Date checkOutDate, double totalCost) {
        if (!InputValidator.isValidId(reservationId)) {
            throw new IllegalArgumentException(AppConstants.ERROR_INVALID_RESERVATION_ID);
        }
        if (!InputValidator.isValidId(userId)) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        if (!InputValidator.isValidId(roomId)) {
            throw new IllegalArgumentException(AppConstants.ERROR_INVALID_ROOM_ID);
        }
        if (!DateUtils.isValidDateRange(checkInDate, checkOutDate)) {
            throw new IllegalArgumentException(AppConstants.ERROR_INVALID_DATE_RANGE);
        }
        if (totalCost < 0) {
            throw new IllegalArgumentException("Total cost cannot be negative");
        }
        
        this.reservationId = reservationId;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = new Date(checkInDate.getTime()); // Defensive copy
        this.checkOutDate = new Date(checkOutDate.getTime()); // Defensive copy
        this.totalCost = totalCost;
    }

    // Getter for immutable reservationId
    public int getReservationId() {
        return reservationId;
    }

    public int getUserId() {
        return userId;
    }

    /**
     * Validates user ID before setting
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setUserId(int userId) {
        if (!InputValidator.isValidId(userId)) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    /**
     * Validates room ID before setting
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setRoomId(int roomId) {
        if (!InputValidator.isValidId(roomId)) {
            throw new IllegalArgumentException(AppConstants.ERROR_INVALID_ROOM_ID);
        }
        this.roomId = roomId;
    }

    /**
     * Returns defensive copy of check-in date
     * Refactoring Technique: Encapsulate Field (prevent external modification)
     */
    public Date getCheckInDate() {
        return new Date(checkInDate.getTime());
    }

    /**
     * Validates and sets check-in date with defensive copy
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setCheckInDate(Date checkInDate) {
        if (!DateUtils.isValidDateRange(checkInDate, this.checkOutDate)) {
            throw new IllegalArgumentException(AppConstants.ERROR_INVALID_DATE_RANGE);
        }
        this.checkInDate = new Date(checkInDate.getTime());
    }

    /**
     * Returns defensive copy of check-out date
     * Refactoring Technique: Encapsulate Field (prevent external modification)
     */
    public Date getCheckOutDate() {
        return new Date(checkOutDate.getTime());
    }

    /**
     * Validates and sets check-out date with defensive copy
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setCheckOutDate(Date checkOutDate) {
        if (!DateUtils.isValidDateRange(this.checkInDate, checkOutDate)) {
            throw new IllegalArgumentException(AppConstants.ERROR_INVALID_DATE_RANGE);
        }
        this.checkOutDate = new Date(checkOutDate.getTime());
    }

    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Validates total cost before setting
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setTotalCost(double totalCost) {
        if (totalCost < 0) {
            throw new IllegalArgumentException("Total cost cannot be negative");
        }
        this.totalCost = totalCost;
    }

    /**
     * Calculate number of nights for this reservation
     * Refactoring Technique: Move Method (calculation belongs to Reservation)
     */
    public long getNumberOfNights() {
        return DateUtils.calculateDaysBetween(checkInDate, checkOutDate);
    }

    /**
     * Recalculate total cost based on room price
     * Refactoring Technique: Replace Data Value with Object (adding behavior)
     */
    public void recalculateCost(double roomPrice) {
        if (!InputValidator.isValidPrice(roomPrice)) {
            throw new IllegalArgumentException(AppConstants.ERROR_INVALID_PRICE);
        }
        this.totalCost = roomPrice * getNumberOfNights();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalCost=" + totalCost +
                ", nights=" + getNumberOfNights() +
                '}';
    }
}

