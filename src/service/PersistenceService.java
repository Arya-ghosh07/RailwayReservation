package service;

import model.User;
import model.Station;
import model.Train;
import model.Ticket;

import java.util.List;

/**
 * Interface defining operations for data persistence.
 * This abstracts storage (Text Files, SQLite, etc.) from business logic.
 */
public interface PersistenceService {
    
    // User persistence
    List<User> loadUsers();
    void saveUsers(List<User> users);

    // Station persistence
    List<Station> loadStations();
    void saveStations(List<Station> stations);

    // Train persistence
    List<Train> loadTrains();
    void saveTrains(List<Train> trains);

    // Ticket/Booking persistence
    List<Ticket> loadTickets();
    void saveTickets(List<Ticket> tickets);
}
