package graph;

/**
 * Represents a connection (edge) between two stations in the railway graph.
 */
public class Edge {
    private String sourceCode;
    private String destinationCode;
    private double distance;        // Distance in km (primary weight)
    private int travelTimeMinutes;  // Travel time in minutes (secondary weight)

    // Default Constructor
    public Edge() {}

    // Parametrized Constructor
    public Edge(String sourceCode, String destinationCode, double distance, int travelTimeMinutes) {
        this.sourceCode = sourceCode;
        this.destinationCode = destinationCode;
        this.distance = distance;
        this.travelTimeMinutes = travelTimeMinutes;
    }

    // Getters and Setters
    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTravelTimeMinutes() {
        return travelTimeMinutes;
    }

    public void setTravelTimeMinutes(int travelTimeMinutes) {
        this.travelTimeMinutes = travelTimeMinutes;
    }

    @Override
    public String toString() {
        return sourceCode + " -> " + destinationCode + " (" + distance + " km, " + travelTimeMinutes + " mins)";
    }
}
