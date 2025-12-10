/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem;

/**
 *
 * @author Legendary Khan
 */
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void updateRoom(Room room) throws Exception {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId() == room.getRoomId()) {
                rooms.set(i, room);
                return;
            }
        }
        throw new Exception("Room not found");
    }

    public Room getRoomById(int roomId) throws Exception {
        for (Room room : rooms) {
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        throw new Exception("Room not found");
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public boolean isRoomAvailable(int roomId) throws Exception {
        Room room = getRoomById(roomId);
        return room.isAvailable();
    }

    public void setRoomAvailability(int roomId, boolean isAvailable) throws Exception {
        Room room = getRoomById(roomId);
        room.setAvailable(isAvailable);
    }

    public void removeRoom(int roomId) throws Exception {
        boolean removed = rooms.removeIf(room -> room.getRoomId() == roomId);
        if (!removed) {
            throw new Exception("Room not found");
        }
    }
}
