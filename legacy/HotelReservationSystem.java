package hotelreservationsystem;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HotelReservationSystem {
    private static RoomService roomService = new RoomService();
    private static ReservationService reservationService = new ReservationService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeSystem();

        while (true) {
            System.out.println("\nWelcome to the Hotel Reservation System");
            System.out.println("1. View All Rooms");
            System.out.println("2. Add Room");
            System.out.println("3. Make Reservation");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View All Reservations");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (option) {
                    case 1:
                        viewAllRooms();
                        break;
                    case 2:
                        addRoom(scanner);
                        break;
                    case 3:
                        makeReservation(scanner);
                        break;
                    case 4:
                        cancelReservation(scanner);
                        break;
                    case 5:
                        viewAllReservations();
                        break;
                    case 6:
                        System.out.println("Exiting system. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void initializeSystem() {
        roomService.addRoom(new Room(1, "Single", 100.0, true));
        roomService.addRoom(new Room(2, "Double", 150.0, true));
        roomService.addRoom(new Room(3, "Suite", 300.0, true));
    }

    private static void viewAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    private static void addRoom(Scanner scanner) {
        try {
            System.out.print("Enter Room ID: ");
            int roomId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter Room Type: ");
            String roomType = scanner.nextLine();
            System.out.print("Enter Room Price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); 
            System.out.print("Is the Room Available? (true/false): ");
            boolean isAvailable = scanner.nextBoolean();

            Room room = new Room(roomId, roomType, price, isAvailable);
            roomService.addRoom(room);
            System.out.println("Room added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }

    private static void makeReservation(Scanner scanner) {
        try {
            System.out.print("Enter Reservation ID: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter User ID: ");
            int userId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter Room ID: ");
            int roomId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter Check-in Date (yyyy-MM-dd): ");
            String checkInDateStr = scanner.nextLine();
            System.out.print("Enter Check-out Date (yyyy-MM-dd): ");
            String checkOutDateStr = scanner.nextLine();

            Date checkInDate = java.sql.Date.valueOf(checkInDateStr);
            Date checkOutDate = java.sql.Date.valueOf(checkOutDateStr);
            double totalCost = roomService.getRoomById(roomId).getPrice() * (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24);

            Reservation reservation = new Reservation(reservationId, userId, roomId, checkInDate, checkOutDate, totalCost);
            reservationService.makeReservation(reservation);
            roomService.setRoomAvailability(roomId, false);
            System.out.println("Reservation made successfully!");
        } catch (Exception e) {
            System.out.println("Error making reservation: " + e.getMessage());
        }
    }

    private static void cancelReservation(Scanner scanner) {
        try {
            System.out.print("Enter Reservation ID to cancel: ");
            int reservationId = scanner.nextInt();

            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation != null) {
                reservationService.cancelReservation(reservationId);
                roomService.setRoomAvailability(reservation.getRoomId(), true);
                System.out.println("Reservation canceled successfully!");
            } else {
                System.out.println("Reservation not found.");
            }
        } catch (Exception e) {
            System.out.println("Error canceling reservation: " + e.getMessage());
        }
    }

    private static void viewAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}

