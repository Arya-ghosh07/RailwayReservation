package model;

/**
 * Represents a booked ticket in the railway system.
 */
public class Ticket {
    private String pnr;             // Unique PNR number
    private String userId;          // ID of user who booked the ticket
    private String trainNumber;     // Train number
    private String passengerName;
    private int passengerAge;
    private String sourceStation;   // Source station code
    private String destinationStation; // Destination station code
    private String travelDate;      // Date of travel (yyyy-MM-dd)
    private int seatNumber;         // Allocated seat number (e.g., 1 to totalSeats), -1 if in waiting list
    private int waitingListNumber;  // Waiting list sequence number (e.g., 1, 2, 3...), 0 if confirmed
    private BookingStatus status;   // Booking status: CONFIRMED, WAITING_LIST, CANCELLED
    private double fare;            // Calculated ticket fare
    private String bookingTimestamp;// Time of booking
    private String seatPreference;  // e.g., Lower, Upper, Window

    // Default Constructor
    public Ticket() {}

    // Parametrized Constructor
    public Ticket(String pnr, String userId, String trainNumber, String passengerName, int passengerAge,
                  String sourceStation, String destinationStation, String travelDate, int seatNumber,
                  int waitingListNumber, BookingStatus status, double fare, String bookingTimestamp, String seatPreference) {
        this.pnr = pnr;
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.passengerName = passengerName;
        this.passengerAge = passengerAge;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.travelDate = travelDate;
        this.seatNumber = seatNumber;
        this.waitingListNumber = waitingListNumber;
        this.status = status;
        this.fare = fare;
        this.bookingTimestamp = bookingTimestamp;
        this.seatPreference = seatPreference;
    }

    public String getSeatPreference() {
        return seatPreference;
    }

    public void setSeatPreference(String seatPreference) {
        this.seatPreference = seatPreference;
    }

    // Getters and Setters
    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getPassengerAge() {
        return passengerAge;
    }

    public void setPassengerAge(int passengerAge) {
        this.passengerAge = passengerAge;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getWaitingListNumber() {
        return waitingListNumber;
    }

    public void setWaitingListNumber(int waitingListNumber) {
        this.waitingListNumber = waitingListNumber;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getBookingTimestamp() {
        return bookingTimestamp;
    }

    public void setBookingTimestamp(String bookingTimestamp) {
        this.bookingTimestamp = bookingTimestamp;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "pnr='" + pnr + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", status=" + status +
                ", seatNumber=" + seatNumber +
                ", WL=" + waitingListNumber +
                '}';
    }
}
