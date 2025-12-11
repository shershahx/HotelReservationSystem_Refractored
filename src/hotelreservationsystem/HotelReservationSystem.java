package hotelreservationsystem;

import hotelreservationsystem.constants.AppConstants;
import hotelreservationsystem.enums.UserRole;
import hotelreservationsystem.exceptions.ReservationException;
import hotelreservationsystem.exceptions.RoomException;
import hotelreservationsystem.exceptions.UserException;
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
 * ENHANCEMENT: Added User Authentication System
 * 
 * @author Refactored by Software Re-Engineering
 */
public class HotelReservationSystem {
    private static final RoomService roomService = new RoomService();
    private static final ReservationService reservationService = new ReservationService();
    private static final UserService userService = new UserService();
    private static Scanner scanner;
    private static User currentUser = null;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        initializeSystem();
        
        // Main application loop with authentication
        boolean exitProgram = false;
        while (!exitProgram) {
            // Authentication loop
            while (currentUser == null) {
                if (!showAuthMenu()) {
                    exitProgram = true;
                    break; // User chose to exit
                }
            }
            
            // If logged in, run main menu
            if (currentUser != null) {
                runMainLoop();
            }
        }
        
        cleanup();
    }

    /**
     * Initialize system with default rooms and users
     * Refactoring Technique: Extract Method
     */
    private static void initializeSystem() {
        try {
            // Initialize default rooms
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
            
            // Initialize default users
            userService.registerUser(1, "admin", "admin123", UserRole.ADMIN);
            userService.registerUser(2, "staff", "staff123", UserRole.STAFF);
            userService.registerUser(3, "customer", "customer123", UserRole.CUSTOMER);
            
        } catch (RoomException | UserException e) {
            System.err.println("Error initializing system: " + e.getMessage());
        }
    }
    
    /**
     * Show authentication menu
     * @return true to continue, false to exit
     */
    private static boolean showAuthMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   Hotel Reservation System - Login    ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        int choice = readMenuOption();
        
        try {
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return false;
                default:
                    System.out.println("Invalid option!");
            }
        } catch (UserException e) {
            displayErrorMessage(e);
        }
        
        return true;
    }
    
    /**
     * Login functionality
     */
    private static void login() throws UserException {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        currentUser = userService.authenticate(username, password);
        System.out.println("\n✓ Login successful! Welcome, " + currentUser.getUsername() + "!");
        System.out.println("Role: " + currentUser.getRole());
    }
    
    /**
     * Registration functionality
     */
    private static void register() throws UserException {
        System.out.println("\n--- Register New Account ---");
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        if (userService.usernameExists(username)) {
            throw new UserException("Username already taken!");
        }
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();
        
        if (!password.equals(confirmPassword)) {
            throw new UserException("Passwords don't match!");
        }
        
        int userId = userService.getNextUserId();
        User newUser = userService.registerUser(userId, username, password, UserRole.CUSTOMER);
        
        System.out.println("\n✓ Registration successful!");
        System.out.println("Your User ID: " + newUser.getUserId());
        System.out.println("You can now login with your credentials.");
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
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      Hotel Reservation System          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ User: " + String.format("%-32s", currentUser.getUsername()) + "║");
        System.out.println("║ Role: " + String.format("%-32s", currentUser.getRole()) + "║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. View All Rooms");
        
        // Admin/Staff only options - Room Management
        if (isAdminOrStaff()) {
            System.out.println("2. Add Room (Admin/Staff)");
            System.out.println("3. Remove Room (Admin/Staff)");
        }
        
        // Customer only option - Booking
        if (currentUser.getRole() == UserRole.CUSTOMER) {
            System.out.println("2. Make Reservation");
        }
        
        System.out.println("4. Cancel Reservation");
        System.out.println("5. View Reservations");
        
        // Admin only options - User Management
        if (currentUser.getRole() == UserRole.ADMIN) {
            System.out.println("6. View All Users (Admin)");
            System.out.println("7. Manage Users (Admin)");
        }
        
        System.out.println("8. My Profile");
        System.out.println("9. Logout");
        System.out.println("========================================");
        System.out.print("Choose an option: ");
    }
    
    /**
     * Check if current user is admin or staff
     */
    private static boolean isAdminOrStaff() {
        return currentUser.getRole() == UserRole.ADMIN || 
               currentUser.getRole() == UserRole.STAFF;
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
                case 1: // View All Rooms
                    viewAllRooms();
                    break;
                case 2: // Add Room (Admin/Staff) OR Make Reservation (Customer)
                    if (isAdminOrStaff()) {
                        addRoom();
                    } else if (currentUser.getRole() == UserRole.CUSTOMER) {
                        makeReservation();
                    } else {
                        displayInvalidOptionMessage();
                    }
                    break;
                case 3: // Remove Room (Admin/Staff only)
                    if (isAdminOrStaff()) {
                        removeRoom();
                    } else {
                        displayInvalidOptionMessage();
                    }
                    break;
                case 4: // Cancel Reservation
                    cancelReservation();
                    break;
                case 5: // View Reservations
                    viewReservations();
                    break;
                case 6: // View All Users (Admin only)
                    if (currentUser.getRole() == UserRole.ADMIN) {
                        viewAllUsers();
                    } else {
                        displayInvalidOptionMessage();
                    }
                    break;
                case 7: // Manage Users (Admin only)
                    if (currentUser.getRole() == UserRole.ADMIN) {
                        manageUsers();
                    } else {
                        displayInvalidOptionMessage();
                    }
                    break;
                case 8: // My Profile
                    viewMyProfile();
                    break;
                case 9: // Logout
                    return logout();
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
     * Remove a room (Admin/Staff only)
     * Refactoring Technique: Extract Method
     */
    private static void removeRoom() {
        try {
            System.out.println("\n--- Remove Room ---");
            
            // Show all rooms first
            List<Room> allRooms = roomService.getAllRooms();
            if (allRooms.isEmpty()) {
                System.out.println("No rooms available to remove.");
                return;
            }
            
            System.out.println("Current Rooms:");
            for (Room room : allRooms) {
                System.out.println("  " + room);
            }
            
            int roomId = readRoomId();
            
            System.out.print("Are you sure you want to remove room " + roomId + "? (yes/no): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("yes")) {
                roomService.removeRoom(roomId);
                System.out.println("✓ Room removed successfully!");
            } else {
                System.out.println("Room removal cancelled.");
            }
            
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
     * 
     * Customer only: Books for themselves
     */
    private static void makeReservation() {
        try {
            System.out.println("\n--- Make Reservation ---");
            
            // Customer books for themselves only
            int userId = currentUser.getUserId();
            System.out.println("Booking for: " + currentUser.getUsername() + " (ID: " + currentUser.getUserId() + ")");
            
            int reservationId = readReservationId();
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
     * View reservations based on user role
     * Refactoring Technique: Extract Method
     */
    private static void viewReservations() {
        if (isAdminOrStaff()) {
            viewAllReservations();
        } else {
            viewMyReservations();
        }
    }
    
    /**
     * Display all reservations (Admin/Staff only)
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
            try {
                User user = userService.getUserById(reservation.getUserId());
                System.out.println(reservation + " | User: " + user.getUsername());
            } catch (UserException e) {
                System.out.println(reservation + " | User: Unknown");
            }
        }
        System.out.println("\nTotal Reservations: " + reservationService.getReservationCount());
        System.out.println("Total Revenue: $" + 
            String.format("%.2f", reservationService.calculateTotalRevenue()));
    }
    
    /**
     * Display my reservations (Customer view)
     */
    private static void viewMyReservations() {
        System.out.println("\n--- My Reservations ---");
        List<Reservation> reservations = reservationService.getReservationsByUserId(currentUser.getUserId());
        
        if (reservations.isEmpty()) {
            System.out.println("You have no reservations.");
            return;
        }
        
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
        System.out.println("\nTotal: " + reservations.size() + " reservation(s)");
    }
    
    /**
     * View all users (Admin only)
     */
    private static void viewAllUsers() {
        System.out.println("\n--- All Users ---");
        List<User> users = userService.getAllUsers();
        
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("\nTotal Users: " + userService.getUserCount());
    }
    
    /**
     * Manage users (Admin only)
     */
    private static void manageUsers() {
        System.out.println("\n--- Manage Users ---");
        System.out.println("1. Change User Role");
        System.out.println("2. Delete User");
        System.out.println("3. Back");
        System.out.print("Choose an option: ");
        
        int choice = readMenuOption();
        
        try {
            switch (choice) {
                case 1:
                    changeUserRole();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        } catch (UserException e) {
            displayErrorMessage(e);
        }
    }
    
    /**
     * Change user role (Admin only)
     */
    private static void changeUserRole() throws UserException {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Select new role:");
        System.out.println("1. Customer");
        System.out.println("2. Staff");
        System.out.println("3. Admin");
        System.out.print("Choice: ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();
        
        UserRole newRole;
        switch (roleChoice) {
            case 1:
                newRole = UserRole.CUSTOMER;
                break;
            case 2:
                newRole = UserRole.STAFF;
                break;
            case 3:
                newRole = UserRole.ADMIN;
                break;
            default:
                throw new UserException("Invalid role selection");
        }
        
        userService.updateUserRole(userId, newRole);
        System.out.println("✓ User role updated successfully!");
    }
    
    /**
     * Delete user (Admin only)
     */
    private static void deleteUser() throws UserException {
        System.out.print("Enter User ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        
        if (userId == currentUser.getUserId()) {
            throw new UserException("Cannot delete your own account!");
        }
        
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            userService.deleteUser(userId);
            System.out.println("✓ User deleted successfully!");
        }
    }
    
    /**
     * View my profile
     */
    private static void viewMyProfile() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║            My Profile                  ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("User ID: " + currentUser.getUserId());
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Role: " + currentUser.getRole());
        
        List<Reservation> myReservations = reservationService.getReservationsByUserId(currentUser.getUserId());
        System.out.println("Total Reservations: " + myReservations.size());
    }
    
    /**
     * Logout - returns user to login menu
     */
    private static boolean logout() {
        System.out.println("\n✓ Logged out successfully!");
        System.out.println("Goodbye, " + currentUser.getUsername() + "!");
        currentUser = null;
        return false; // Exit main menu loop
    }

    /**
     * Display exit message
     * Refactoring Technique: Extract Method
     */
    private static void displayExitMessage() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  Thank you for using our system!       ║");
        System.out.println("║  Goodbye!                              ║");
        System.out.println("╚════════════════════════════════════════╝");
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

