package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a train with its basic information and route schedules.
 */
public class Train {
    private String trainNumber;
    private String trainName;
    private String sourceStationCode;
    private String destinationStationCode;
    private int totalSeats;
    private int availableSeats;
    private double baseFare;
    private String daysOfOperation; // e.g., "Daily", "Mon,Wed,Fri"
    private List<Schedule> route;   // Sorted list of schedules/stops

    // Default Constructor
    public Train() {
        this.route = new ArrayList<>();
    }

    // Parametrized Constructor
    public Train(String trainNumber, String trainName, String sourceStationCode, String destinationStationCode,
                 int totalSeats, double baseFare, String daysOfOperation) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.sourceStationCode = sourceStationCode;
        this.destinationStationCode = destinationStationCode;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; // Initially all seats are available
        this.baseFare = baseFare;
        this.daysOfOperation = daysOfOperation;
        this.route = new ArrayList<>();
    }

    // Getters and Setters
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSourceStationCode() {
        return sourceStationCode;
    }

    public void setSourceStationCode(String sourceStationCode) {
        this.sourceStationCode = sourceStationCode;
    }

    public String getDestinationStationCode() {
        return destinationStationCode;
    }

    public void setDestinationStationCode(String destinationStationCode) {
        this.destinationStationCode = destinationStationCode;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public String getDaysOfOperation() {
        return daysOfOperation;
    }

    public void setDaysOfOperation(String daysOfOperation) {
        this.daysOfOperation = daysOfOperation;
    }

    public List<Schedule> getRoute() {
        return route;
    }

    public void setRoute(List<Schedule> route) {
        this.route = route;
    }

    public void addStop(Schedule stop) {
        this.route.add(stop);
        // Route list should be sorted by sequence number or time, which can be done in service
    }

    @Override
    public String toString() {
        return trainName + " (" + trainNumber + ")";
    }
}
