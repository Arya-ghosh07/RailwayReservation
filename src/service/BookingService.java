package service;

import model.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages ticket bookings, cancellations, seat allocations, fare calculations, and waiting lists.
 */
public class BookingService {
    private List<Ticket> tickets;
    private PersistenceService persistenceService;
    private TrainService trainService;

    public BookingService(PersistenceService persistenceService, TrainService trainService) {
        this.persistenceService = persistenceService;
        this.trainService = trainService;
        this.tickets = persistenceService.loadTickets();
    }

    /**
     * Books a ticket. Performs seat allocation or adds passenger to waiting list.
     */
    public Ticket bookTicket(String userId, String trainNumber, String passengerName, int passengerAge,
                             String sourceCode, String destCode, String travelDate, String seatPreference) {
        
        // Very basic implementation without real seat tracking for now
        String pnr = "PNR" + System.currentTimeMillis();
        double fare = calculateFare(trainNumber, sourceCode, destCode);
        String timestamp = util.DateTimeUtil.getCurrentTimestampString();
        
        Ticket ticket = new Ticket(pnr, userId, trainNumber, passengerName, passengerAge,
                sourceCode, destCode, travelDate, 1, 0, model.BookingStatus.CONFIRMED, fare, timestamp, seatPreference);
                
        tickets.add(ticket);
        persistenceService.saveTickets(tickets);
        return ticket;
    }

    /**
     * Cancels a ticket by PNR.
     */
    public boolean cancelTicket(String pnr) {
        for (Ticket ticket : tickets) {
            if (ticket.getPnr().equals(pnr) && ticket.getStatus() != model.BookingStatus.CANCELLED) {
                ticket.setStatus(model.BookingStatus.CANCELLED);
                persistenceService.saveTickets(tickets);
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates fare based on distance and route sequence.
     */
    public double calculateFare(String trainNumber, String sourceCode, String destCode) {
        // Basic static calculation for MVP
        if (trainService != null) {
            model.Train t = trainService.getTrain(trainNumber);
            if (t != null) {
                return t.getBaseFare();
            }
        }
        return 500.0; // Fallback
    }

    public List<Ticket> getBookingHistory(String userId) {
        List<Ticket> history = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getUserId().equals(userId)) {
                history.add(ticket);
            }
        }
        return history;
    }

    public List<Ticket> getReservationsForTrain(String trainNumber) {
        // Placeholder
        return new ArrayList<>();
    }

    public List<Ticket> getWaitingListForTrain(String trainNumber, String travelDate) {
        // Placeholder
        return new ArrayList<>();
    }

    public int getAvailableSeatsCount(String trainNumber, String travelDate) {
        // Placeholder
        return 0;
    }

    public List<Ticket> getAllTickets() {
        return tickets;
    }
}
