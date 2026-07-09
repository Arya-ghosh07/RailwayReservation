package model;

/**
 * Represents a train stop schedule along its route.
 */
public class Schedule {
    private String stationCode;
    private String arrivalTime;   // e.g., "14:30"
    private String departureTime; // e.g., "14:35"
    private double distanceFromSource; // in Kilometers
    private int stopSequence;     // Sequence number (1 for source, 2, 3...)

    // Default Constructor
    public Schedule() {}

    // Parametrized Constructor
    public Schedule(String stationCode, String arrivalTime, String departureTime, double distanceFromSource, int stopSequence) {
        this.stationCode = stationCode;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.distanceFromSource = distanceFromSource;
        this.stopSequence = stopSequence;
    }

    // Getters and Setters
    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public double getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(double distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "stationCode='" + stationCode + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", distanceFromSource=" + distanceFromSource +
                ", stopSequence=" + stopSequence +
                '}';
    }
}
