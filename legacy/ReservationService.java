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

public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>();

    public void makeReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void cancelReservation(int reservationId) throws Exception {
        boolean removed = reservations.removeIf(reservation -> reservation.getReservationId() == reservationId);
        if (!removed) {
            throw new Exception("Reservation not found");
        }
    }

    public Reservation getReservationById(int reservationId) throws Exception {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        throw new Exception("Reservation not found");
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public void updateReservation(Reservation updatedReservation) throws Exception {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId() == updatedReservation.getReservationId()) {
                reservations.set(i, updatedReservation);
                return;
            }
        }
        throw new Exception("Reservation not found");
    }
}
