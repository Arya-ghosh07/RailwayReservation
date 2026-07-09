package model;

/**
 * Represents a railway station (vertex in the network graph).
 */
public class Station {
    private String code; // e.g., "NDLS"
    private String name; // e.g., "New Delhi"
    private int x;       // X coordinate for UI visualization
    private int y;       // Y coordinate for UI visualization

    // Default Constructor
    public Station() {}

    // Parametrized Constructor
    public Station(String code, String name, int x, int y) {
        this.code = code;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return code.equalsIgnoreCase(station.code);
    }

    @Override
    public int hashCode() {
        return code.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}
