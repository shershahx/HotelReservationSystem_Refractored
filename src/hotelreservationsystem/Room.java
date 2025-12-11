package hotelreservationsystem;

import hotelreservationsystem.utils.InputValidator;

/**
 * Represents a hotel room in the system
 * 
 * Refactoring Techniques Applied:
 * - Encapsulate Field (private fields with validation)
 * - Introduce Assertion (validation in setters and constructor)
 * - Self Encapsulate Field
 * - Replace Data Value with Object (enhanced behavior)
 * 
 * @author Refactored by Software Re-Engineering
 */
public class Room {
    private int roomId;
    private String roomType;
    private double price;
    private boolean isAvailable;

    /**
     * Constructor with validation
     * Refactoring Technique: Introduce Assertion
     */
    public Room(int roomId, String roomType, double price, boolean isAvailable) {
        if (!InputValidator.isValidId(roomId)) {
            throw new IllegalArgumentException("Room ID must be positive");
        }
        if (!InputValidator.isValidString(roomType)) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }
        if (!InputValidator.isValidPrice(price)) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    // Getter for immutable roomId
    public int getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    /**
     * Validates room type before setting
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setRoomType(String roomType) {
        if (!InputValidator.isValidString(roomType)) {
            throw new IllegalArgumentException("Room type cannot be null or empty");
        }
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Validates price before setting
     * Refactoring Technique: Encapsulate Field with validation
     */
    public void setPrice(double price) {
        if (!InputValidator.isValidPrice(price)) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * Business logic: Mark room as occupied
     * Refactoring Technique: Replace Data Value with Object (adding behavior)
     */
    public void markAsOccupied() {
        this.isAvailable = false;
    }

    /**
     * Business logic: Mark room as available
     * Refactoring Technique: Replace Data Value with Object (adding behavior)
     */
    public void markAsAvailable() {
        this.isAvailable = true;
    }

    /**
     * Calculate cost for given number of nights
     * Refactoring Technique: Move Method (move calculation to Room)
     */
    public double calculateCost(long numberOfNights) {
        if (numberOfNights <= 0) {
            throw new IllegalArgumentException("Number of nights must be positive");
        }
        return this.price * numberOfNights;
    }

    @Override
    public String toString() {
        return "Room " +
                "roomId=" + roomId +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable;
    }
}

