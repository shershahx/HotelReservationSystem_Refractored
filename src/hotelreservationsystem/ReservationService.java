package hotelreservationsystem;

import hotelreservationsystem.constants.AppConstants;
import hotelreservationsystem.exceptions.ReservationException;
import hotelreservationsystem.utils.InputValidator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing hotel reservations
 * 
 * Refactoring Techniques Applied:
 * - Replace Error Code with Exception (custom ReservationException)
 * - Encapsulate Collection (return unmodifiable list)
 * - Extract Method (separate concerns)
 * - Introduce Null Object (use Optional)
 * - Separate Query from Modifier
 * 
 * @author Refactored by Software Re-Engineering
 */
public class ReservationService {
    private final List<Reservation> reservations;

    public ReservationService() {
        this.reservations = new ArrayList<>();
    }

    /**
     * Makes a new reservation with validation
     * Refactoring Technique: Introduce Assertion
     * Refactoring Technique: Rename Method (more descriptive)
     */
    public void makeReservation(Reservation reservation) throws ReservationException {
        if (reservation == null) {
            throw new ReservationException("Reservation cannot be null");
        }
        
        // Check for duplicate reservation ID
        if (findReservationById(reservation.getReservationId()).isPresent()) {
            throw new ReservationException("Reservation with ID " + 
                reservation.getReservationId() + " already exists");
        }
        
        reservations.add(reservation);
    }

    /**
     * Cancels an existing reservation
     * Refactoring Technique: Replace Error Code with Exception
     * Refactoring Technique: Rename Method (more descriptive)
     */
    public void cancelReservation(int reservationId) throws ReservationException {
        if (!InputValidator.isValidId(reservationId)) {
            throw new ReservationException(AppConstants.ERROR_INVALID_RESERVATION_ID);
        }
        
        boolean removed = reservations.removeIf(
            reservation -> reservation.getReservationId() == reservationId
        );
        
        if (!removed) {
            throw new ReservationException(AppConstants.ERROR_RESERVATION_NOT_FOUND);
        }
    }

    /**
     * Finds a reservation by ID and returns Optional
     * Refactoring Technique: Introduce Null Object (using Optional)
     * Refactoring Technique: Separate Query from Modifier
     */
    private Optional<Reservation> findReservationById(int reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.getReservationId() == reservationId)
                .findFirst();
    }

    /**
     * Gets a reservation by ID
     * Refactoring Technique: Replace Error Code with Exception
     */
    public Reservation getReservationById(int reservationId) throws ReservationException {
        if (!InputValidator.isValidId(reservationId)) {
            throw new ReservationException(AppConstants.ERROR_INVALID_RESERVATION_ID);
        }
        
        return findReservationById(reservationId)
                .orElseThrow(() -> new ReservationException(
                    AppConstants.ERROR_RESERVATION_NOT_FOUND));
    }

    /**
     * Returns unmodifiable list of all reservations
     * Refactoring Technique: Encapsulate Collection
     */
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(reservations);
    }

    /**
     * Gets all reservations for a specific user
     * Refactoring Technique: Extract Method
     */
    public List<Reservation> getReservationsByUserId(int userId) {
        if (!InputValidator.isValidId(userId)) {
            return Collections.emptyList();
        }
        
        return reservations.stream()
                .filter(reservation -> reservation.getUserId() == userId)
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    Collections::unmodifiableList
                ));
    }

    /**
     * Gets all reservations for a specific room
     * Refactoring Technique: Extract Method
     */
    public List<Reservation> getReservationsByRoomId(int roomId) {
        if (!InputValidator.isValidId(roomId)) {
            return Collections.emptyList();
        }
        
        return reservations.stream()
                .filter(reservation -> reservation.getRoomId() == roomId)
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    Collections::unmodifiableList
                ));
    }

    /**
     * Updates an existing reservation
     * Refactoring Technique: Replace Error Code with Exception
     */
    public void updateReservation(Reservation updatedReservation) throws ReservationException {
        if (updatedReservation == null) {
            throw new ReservationException("Reservation cannot be null");
        }
        
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId() == 
                updatedReservation.getReservationId()) {
                reservations.set(i, updatedReservation);
                return;
            }
        }
        throw new ReservationException(AppConstants.ERROR_RESERVATION_NOT_FOUND);
    }

    /**
     * Gets the total count of reservations
     * Refactoring Technique: Extract Method
     */
    public int getReservationCount() {
        return reservations.size();
    }

    /**
     * Calculates total revenue from all reservations
     * Refactoring Technique: Extract Method
     */
    public double calculateTotalRevenue() {
        return reservations.stream()
                .mapToDouble(Reservation::getTotalCost)
                .sum();
    }

    /**
     * Checks if a reservation exists
     * Refactoring Technique: Extract Method
     * Refactoring Technique: Separate Query from Modifier
     */
    public boolean reservationExists(int reservationId) {
        return findReservationById(reservationId).isPresent();
    }
}
