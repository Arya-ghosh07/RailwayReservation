package service;

import model.Role;
import model.User;
import model.Station;
import model.Train;
import model.Ticket;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Text-file based implementation of the PersistenceService.
 */
public class FilePersistenceService implements PersistenceService {
    private static final String DATA_DIR = resolveDataDir();

    private static String resolveDataDir() {
        if (new java.io.File("data").exists()) return "data/";
        if (new java.io.File("../data").exists()) return "../data/";
        return "data/";
    }
    private static final String USERS_FILE = DATA_DIR + "users.txt";
    private static final String STATIONS_FILE = DATA_DIR + "stations.txt";
    private static final String TRAINS_FILE = DATA_DIR + "trains.txt";
    private static final String TICKETS_FILE = DATA_DIR + "bookings.txt";

    @Override
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = FileUtil.readLines(USERS_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    User user = new User(parts[0], parts[1], parts[2], parts[3], Role.valueOf(parts[4]));
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void saveUsers(List<User> users) {
        List<String> lines = new ArrayList<>();
        lines.add("# Format: ID|Username|Password|Name|Role");
        for (User user : users) {
            String line = String.join("|", user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getRole().name());
            lines.add(line);
        }
        try {
            FileUtil.writeLines(USERS_FILE, lines);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    @Override
    public List<Station> loadStations() {
        List<Station> stations = new ArrayList<>();
        try {
            List<String> lines = FileUtil.readLines(STATIONS_FILE);
            for (String line : lines) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    stations.add(new Station(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading stations: " + e.getMessage());
        }
        return stations;
    }

    @Override
    public void saveStations(List<Station> stations) {
        List<String> lines = new ArrayList<>();
        lines.add("# Format: Code|Name|X|Y");
        for (Station s : stations) {
            lines.add(String.join("|", s.getCode(), s.getName(), String.valueOf(s.getX()), String.valueOf(s.getY())));
        }
        try {
            FileUtil.writeLines(STATIONS_FILE, lines);
        } catch (IOException e) {
            System.err.println("Error saving stations: " + e.getMessage());
        }
    }

    @Override
    public List<Train> loadTrains() {
        List<Train> trains = new ArrayList<>();
        try {
            List<String> lines = FileUtil.readLines(TRAINS_FILE);
            for (String line : lines) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    trains.add(new Train(parts[0], parts[1], parts[2], parts[3], 
                                         Integer.parseInt(parts[4]), Double.parseDouble(parts[5]), parts[6]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading trains: " + e.getMessage());
        }
        return trains;
    }

    @Override
    public void saveTrains(List<Train> trains) {
        List<String> lines = new ArrayList<>();
        lines.add("# Format: Number|Name|Source|Dest|Seats|Fare|Days");
        for (Train t : trains) {
            lines.add(String.join("|", t.getTrainNumber(), t.getTrainName(), t.getSourceStationCode(), 
                      t.getDestinationStationCode(), String.valueOf(t.getTotalSeats()), String.valueOf(t.getBaseFare()), t.getDaysOfOperation()));
        }
        try {
            FileUtil.writeLines(TRAINS_FILE, lines);
        } catch (IOException e) {
            System.err.println("Error saving trains: " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> loadTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try {
            List<String> lines = FileUtil.readLines(TICKETS_FILE);
            for (String line : lines) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 13) {
                    String seatPref = parts.length >= 14 ? parts[13] : "No Preference";
                    tickets.add(new Ticket(
                        parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]),
                        parts[5], parts[6], parts[7], Integer.parseInt(parts[8]),
                        Integer.parseInt(parts[9]), model.BookingStatus.valueOf(parts[10]),
                        Double.parseDouble(parts[11]), parts[12], seatPref
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tickets: " + e.getMessage());
        }
        return tickets;
    }

    @Override
    public void saveTickets(List<Ticket> tickets) {
        List<String> lines = new ArrayList<>();
        lines.add("# Format: PNR|UserID|TrainNo|Name|Age|Source|Dest|Date|SeatNo|WLNo|Status|Fare|Timestamp|SeatPref");
        for (Ticket t : tickets) {
            lines.add(String.join("|", 
                t.getPnr(), t.getUserId(), t.getTrainNumber(), t.getPassengerName(), String.valueOf(t.getPassengerAge()),
                t.getSourceStation(), t.getDestinationStation(), t.getTravelDate(), String.valueOf(t.getSeatNumber()),
                String.valueOf(t.getWaitingListNumber()), t.getStatus().name(), String.valueOf(t.getFare()), t.getBookingTimestamp(), t.getSeatPreference()
            ));
        }
        try {
            FileUtil.writeLines(TICKETS_FILE, lines);
        } catch (IOException e) {
            System.err.println("Error saving tickets: " + e.getMessage());
        }
    }
}
