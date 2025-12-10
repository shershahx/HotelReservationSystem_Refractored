package hotelreservationsystem;

import hotelreservationsystem.constants.AppConstants;
import hotelreservationsystem.exceptions.RoomException;
import hotelreservationsystem.utils.InputValidator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing hotel rooms
 * 
 * Refactoring Techniques Applied:
 * - Replace Error Code with Exception (custom RoomException)
 * - Encapsulate Collection (return unmodifiable list)
 * - Extract Method (separate query from modifier)
 * - Introduce Null Object (use Optional)
 * - Separate Query from Modifier
 * 
 * @author Refactored by Software Re-Engineering
 */
public class RoomService {
    private final List<Room> rooms;

    public RoomService() {
        this.rooms = new ArrayList<>();
    }

    /**
     * Adds a room to the system with validation
     * Refactoring Technique: Introduce Assertion
     */
    public void addRoom(Room room) throws RoomException {
        if (room == null) {
            throw new RoomException("Room cannot be null");
        }
        
        // Check for duplicate room ID
        if (findRoomById(room.getRoomId()).isPresent()) {
            throw new RoomException("Room with ID " + room.getRoomId() + " already exists");
        }
        
        rooms.add(room);
    }

    /**
     * Updates an existing room
     * Refactoring Technique: Replace Error Code with Exception
     */
    public void updateRoom(Room room) throws RoomException {
        if (room == null) {
            throw new RoomException("Room cannot be null");
        }
        
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId() == room.getRoomId()) {
                rooms.set(i, room);
                return;
            }
        }
        throw new RoomException(AppConstants.ERROR_ROOM_NOT_FOUND);
    }

    /**
     * Finds a room by ID and returns Optional
     * Refactoring Technique: Introduce Null Object (using Optional)
     * Refactoring Technique: Separate Query from Modifier
     */
    private Optional<Room> findRoomById(int roomId) {
        return rooms.stream()
                .filter(room -> room.getRoomId() == roomId)
                .findFirst();
    }

    /**
     * Gets a room by ID
     * Refactoring Technique: Replace Error Code with Exception
     */
    public Room getRoomById(int roomId) throws RoomException {
        if (!InputValidator.isValidId(roomId)) {
            throw new RoomException(AppConstants.ERROR_INVALID_ROOM_ID);
        }
        
        return findRoomById(roomId)
                .orElseThrow(() -> new RoomException(AppConstants.ERROR_ROOM_NOT_FOUND));
    }

    /**
     * Returns unmodifiable list of all rooms
     * Refactoring Technique: Encapsulate Collection
     */
    public List<Room> getAllRooms() {
        return Collections.unmodifiableList(rooms);
    }

    /**
     * Returns list of available rooms only
     * Refactoring Technique: Extract Method
     * Refactoring Technique: Encapsulate Collection
     */
    public List<Room> getAvailableRooms() {
        return rooms.stream()
                .filter(Room::isAvailable)
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    Collections::unmodifiableList
                ));
    }

    /**
     * Checks if a room is available
     * Refactoring Technique: Separate Query from Modifier
     */
    public boolean isRoomAvailable(int roomId) throws RoomException {
        Room room = getRoomById(roomId);
        return room.isAvailable();
    }

    /**
     * Sets room availability
     * Refactoring Technique: Replace Error Code with Exception
     */
    public void setRoomAvailability(int roomId, boolean isAvailable) throws RoomException {
        Room room = getRoomById(roomId);
        room.setAvailable(isAvailable);
    }

    /**
     * Marks a room as occupied
     * Refactoring Technique: Extract Method (specific use case)
     */
    public void markRoomAsOccupied(int roomId) throws RoomException {
        Room room = getRoomById(roomId);
        if (!room.isAvailable()) {
            throw new RoomException(AppConstants.ERROR_ROOM_NOT_AVAILABLE);
        }
        room.markAsOccupied();
    }

    /**
     * Marks a room as available
     * Refactoring Technique: Extract Method (specific use case)
     */
    public void markRoomAsAvailable(int roomId) throws RoomException {
        Room room = getRoomById(roomId);
        room.markAsAvailable();
    }

    /**
     * Removes a room from the system
     * Refactoring Technique: Replace Error Code with Exception
     */
    public void removeRoom(int roomId) throws RoomException {
        if (!InputValidator.isValidId(roomId)) {
            throw new RoomException(AppConstants.ERROR_INVALID_ROOM_ID);
        }
        
        boolean removed = rooms.removeIf(room -> room.getRoomId() == roomId);
        if (!removed) {
            throw new RoomException(AppConstants.ERROR_ROOM_NOT_FOUND);
        }
    }

    /**
     * Gets the count of available rooms
     * Refactoring Technique: Extract Method
     */
    public long getAvailableRoomCount() {
        return rooms.stream()
                .filter(Room::isAvailable)
                .count();
    }

    /**
     * Finds rooms by type
     * Refactoring Technique: Extract Method
     */
    public List<Room> findRoomsByType(String roomType) {
        if (!InputValidator.isValidString(roomType)) {
            return Collections.emptyList();
        }
        
        return rooms.stream()
                .filter(room -> room.getRoomType().equalsIgnoreCase(roomType))
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    Collections::unmodifiableList
                ));
    }
}
