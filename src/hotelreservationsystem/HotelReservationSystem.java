package hotelreservationsystem;

import hotelreservationsystem.constants.AppConstants;
import hotelreservationsystem.exceptions.ReservationException;
import hotelreservationsystem.exceptions.RoomException;
import hotelreservationsystem.utils.DateUtils;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Hotel Reservation System
 * 
 * Refactoring Techniques Applied:
 * - Extract Method (breaking down long methods)
 * - Replace Magic Number with Symbolic Constant
 * - Introduce Parameter Object (Scanner passed as parameter)
 * - Separate Query from Modifier
 * - Replace Conditional with Polymorphism (menu system could be improved further)
 * 
 * @author Refactored by Software Re-Engineering
 */
public class HotelReservationSystem {
    private static final RoomService roomService = new RoomService();
    private static final ReservationService reservationService = new ReservationService();
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        initializeSystem();
        runMainLoop();
        cleanup();
    }

    /**
     * Initialize system with default rooms
     * Refactoring Technique: Extract Method
     */
    private static void initializeSystem() {
        try {
            roomService.addRoom(new Room(
                AppConstants.DEFAULT_SINGLE_ROOM_ID, 
                AppConstants.ROOM_TYPE_SINGLE, 
                AppConstants.DEFAULT_SINGLE_ROOM_PRICE, 
                true
            ));
            roomService.addRoom(new Room(
                AppConstants.DEFAULT_DOUBLE_ROOM_ID, 
                AppConstants.ROOM_TYPE_DOUBLE, 
                AppConstants.DEFAULT_DOUBLE_ROOM_PRICE, 
                true
            ));
            roomService.addRoom(new Room(
                AppConstants.DEFAULT_SUITE_ROOM_ID, 
                AppConstants.ROOM_TYPE_SUITE, 
                AppConstants.DEFAULT_SUITE_ROOM_PRICE, 
                true
            ));
        } catch (RoomException e) {
            System.err.println("Error initializing system: " + e.getMessage());
        }
    }

    /**
     * Main application loop
     * Refactoring Technique: Extract Method
     */
    private static void runMainLoop() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            int option = readMenuOption();
            running = processMenuOption(option);
        }
    }

    /**
     * Display main menu
     * Refactoring Technique: Extract Method
     */
    private static void displayMenu() {
        System.out.println("\n========================================");
        System.out.println("  Hotel Reservation System");
        System.out.println("========================================");
        System.out.println(AppConstants.MENU_VIEW_ROOMS + ". View All Rooms");
        System.out.println(AppConstants.MENU_ADD_ROOM + ". Add Room");
        System.out.println(AppConstants.MENU_MAKE_RESERVATION + ". Make Reservation");
        System.out.println(AppConstants.MENU_CANCEL_RESERVATION + ". Cancel Reservation");
        System.out.println(AppConstants.MENU_VIEW_RESERVATIONS + ". View All Reservations");
        System.out.println(AppConstants.MENU_EXIT + ". Exit");
        System.out.println("========================================");
        System.out.print("Choose an option: ");
    }

    /**
     * Read menu option from user with error handling
     * Refactoring Technique: Extract Method
     * Refactoring Technique: Replace Exception with Test
     */
    private static int readMenuOption() {
        try {
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return option;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1; // Invalid option
        }
    }

    /**
     * Process menu option
     * Refactoring Technique: Extract Method
     * Refactoring Technique: Decompose Conditional
     * 
     * @return false if user wants to exit, true otherwise
     */
    private static boolean processMenuOption(int option) {
        try {
            switch (option) {
                case AppConstants.MENU_VIEW_ROOMS:
                    viewAllRooms();
                    break;
                case AppConstants.MENU_ADD_ROOM:
                    addRoom();
                    break;
                case AppConstants.MENU_MAKE_RESERVATION:
                    makeReservation();
                    break;
                case AppConstants.MENU_CANCEL_RESERVATION:
                    cancelReservation();
                    break;
                case AppConstants.MENU_VIEW_RESERVATIONS:
                    viewAllReservations();
                    break;
                case AppConstants.MENU_EXIT:
                    displayExitMessage();
                    return false;
                default:
                    displayInvalidOptionMessage();
            }
        } catch (Exception e) {
            displayErrorMessage(e);
        }
        return true;
    }

    /**
     * Display all rooms
     * Refactoring Technique: Extract Method
     */
    private static void viewAllRooms() {
        System.out.println("\n--- All Rooms ---");
        List<Room> rooms = roomService.getAllRooms();
        
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }
        
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("\nAvailable rooms: " + roomService.getAvailableRoomCount());
    }

    /**
     * Add a new room
     * Refactoring Technique: Extract Method (broken down from original)
     */
    private static void addRoom() {
        try {
            System.out.println("\n--- Add New Room ---");
            
            int roomId = readRoomId();
            String roomType = readRoomType();
            double price = readRoomPrice();
            boolean isAvailable = readRoomAvailability();

            Room room = new Room(roomId, roomType, price, isAvailable);
            roomService.addRoom(room);
            System.out.println(AppConstants.SUCCESS_ROOM_ADDED);
            
        } catch (RoomException e) {
            displayErrorMessage(e);
        }
    }

    /**
     * Read room ID from user
     * Refactoring Technique: Extract Method
     */
    private static int readRoomId() {
        System.out.print("Enter Room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine();
        return roomId;
    }

    /**
     * Read room type from user
     * Refactoring Technique: Extract Method
     */
    private static String readRoomType() {
        System.out.print("Enter Room Type (Single/Double/Suite): ");
        return scanner.nextLine();
    }

    /**
     * Read room price from user
     * Refactoring Technique: Extract Method
     */
    private static double readRoomPrice() {
        System.out.print("Enter Room Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        return price;
    }

    /**
     * Read room availability from user
     * Refactoring Technique: Extract Method
     */
    private static boolean readRoomAvailability() {
        System.out.print("Is the Room Available? (true/false): ");
        boolean isAvailable = scanner.nextBoolean();
        scanner.nextLine();
        return isAvailable;
    }

    /**
     * Make a new reservation
     * Refactoring Technique: Extract Method (heavily refactored from long method)
     * Refactoring Technique: Replace Temp with Query
     */
    private static void makeReservation() {
        try {
            System.out.println("\n--- Make Reservation ---");
            
            int reservationId = readReservationId();
            int userId = readUserId();
            int roomId = readRoomId();
            
            // Verify room is available
            verifyRoomAvailability(roomId);
            
            Date checkInDate = readCheckInDate();
            Date checkOutDate = readCheckOutDate();
            
            // Validate date range
            validateDateRange(checkInDate, checkOutDate);
            
            // Calculate cost
            double totalCost = calculateReservationCost(roomId, checkInDate, checkOutDate);
            
            // Create and save reservation
            Reservation reservation = new Reservation(
                reservationId, userId, roomId, 
                checkInDate, checkOutDate, totalCost
            );
            reservationService.makeReservation(reservation);
            roomService.markRoomAsOccupied(roomId);
            
            displayReservationSuccess(reservation);
            
        } catch (ReservationException | RoomException e) {
            displayErrorMessage(e);
        }
    }

    /**
     * Verify room is available before booking
     * Refactoring Technique: Extract Method
     * Refactoring Technique: Introduce Assertion
     */
    private static void verifyRoomAvailability(int roomId) throws RoomException {
        if (!roomService.isRoomAvailable(roomId)) {
            throw new RoomException(AppConstants.ERROR_ROOM_NOT_AVAILABLE);
        }
    }

    /**
     * Validate date range
     * Refactoring Technique: Extract Method
     * Refactoring Technique: Introduce Assertion
     */
    private static void validateDateRange(Date checkInDate, Date checkOutDate) 
            throws ReservationException {
        if (!DateUtils.isValidDateRange(checkInDate, checkOutDate)) {
            throw new ReservationException(AppConstants.ERROR_INVALID_DATE_RANGE);
        }
    }

    /**
     * Calculate reservation cost
     * Refactoring Technique: Extract Method
     * Refactoring Technique: Replace Temp with Query
     */
    private static double calculateReservationCost(int roomId, Date checkInDate, 
                                                   Date checkOutDate) throws RoomException {
        Room room = roomService.getRoomById(roomId);
        long numberOfNights = DateUtils.calculateDaysBetween(checkInDate, checkOutDate);
        return room.calculateCost(numberOfNights);
    }

    /**
     * Read reservation ID from user
     * Refactoring Technique: Extract Method
     */
    private static int readReservationId() {
        System.out.print("Enter Reservation ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    /**
     * Read user ID from user
     * Refactoring Technique: Extract Method
     */
    private static int readUserId() {
        System.out.print("Enter User ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    /**
     * Read check-in date from user
     * Refactoring Technique: Extract Method
     */
    private static Date readCheckInDate() {
        System.out.print("Enter Check-in Date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        return java.sql.Date.valueOf(dateStr);
    }

    /**
     * Read check-out date from user
     * Refactoring Technique: Extract Method
     */
    private static Date readCheckOutDate() {
        System.out.print("Enter Check-out Date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        return java.sql.Date.valueOf(dateStr);
    }

    /**
     * Display reservation success message
     * Refactoring Technique: Extract Method
     */
    private static void displayReservationSuccess(Reservation reservation) {
        System.out.println("\n" + AppConstants.SUCCESS_RESERVATION_MADE);
        System.out.println("Reservation Details:");
        System.out.println(reservation);
    }

    /**
     * Cancel a reservation
     * Refactoring Technique: Extract Method
     * Refactoring Technique: Decompose Conditional
     */
    private static void cancelReservation() {
        try {
            System.out.println("\n--- Cancel Reservation ---");
            System.out.print("Enter Reservation ID to cancel: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();

            Reservation reservation = reservationService.getReservationById(reservationId);
            
            // Cancel reservation and free the room
            reservationService.cancelReservation(reservationId);
            roomService.markRoomAsAvailable(reservation.getRoomId());
            
            System.out.println(AppConstants.SUCCESS_RESERVATION_CANCELED);
            
        } catch (ReservationException | RoomException e) {
            displayErrorMessage(e);
        }
    }

    /**
     * Display all reservations
     * Refactoring Technique: Extract Method
     */
    private static void viewAllReservations() {
        System.out.println("\n--- All Reservations ---");
        List<Reservation> reservations = reservationService.getAllReservations();
        
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
        System.out.println("\nTotal Reservations: " + reservationService.getReservationCount());
        System.out.println("Total Revenue: $" + 
            String.format("%.2f", reservationService.calculateTotalRevenue()));
    }

    /**
     * Display exit message
     * Refactoring Technique: Extract Method
     */
    private static void displayExitMessage() {
        System.out.println("\n========================================");
        System.out.println("  Thank you for using our system!");
        System.out.println("  Goodbye!");
        System.out.println("========================================");
    }

    /**
     * Display invalid option message
     * Refactoring Technique: Extract Method
     */
    private static void displayInvalidOptionMessage() {
        System.out.println("\n[ERROR] Invalid option. Please try again.");
    }

    /**
     * Display error message
     * Refactoring Technique: Extract Method
     */
    private static void displayErrorMessage(Exception e) {
        System.out.println("\n[ERROR] " + e.getMessage());
    }

    /**
     * Cleanup resources
     * Refactoring Technique: Extract Method
     */
    private static void cleanup() {
        if (scanner != null) {
            scanner.close();
        }
    }
}

