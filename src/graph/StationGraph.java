package graph;

import model.Station;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the railway network graph using an adjacency list.
 */
public class StationGraph {
    // Maps a station code to its list of connecting edges
    private Map<String, List<Edge>> adjacencyList;
    // Maps a station code to the Station model details
    private Map<String, Station> stations;

    public StationGraph() {
        this.adjacencyList = new HashMap<>();
        this.stations = new HashMap<>();
    }

    public void addStation(Station station) {
        stations.put(station.getCode(), station);
        adjacencyList.putIfAbsent(station.getCode(), new ArrayList<>());
    }

    public void removeStation(String stationCode) {
        stations.remove(stationCode);
        adjacencyList.remove(stationCode);
        for (List<Edge> edges : adjacencyList.values()) {
            edges.removeIf(edge -> edge.getDestinationCode().equals(stationCode));
        }
    }

    public void addEdge(String sourceCode, String destinationCode, double distance, int travelTimeMinutes) {
        adjacencyList.putIfAbsent(sourceCode, new ArrayList<>());
        adjacencyList.get(sourceCode).add(new Edge(sourceCode, destinationCode, distance, travelTimeMinutes));
    }

    public void removeEdge(String sourceCode, String destinationCode) {
        if (adjacencyList.containsKey(sourceCode)) {
            adjacencyList.get(sourceCode).removeIf(edge -> edge.getDestinationCode().equals(destinationCode));
        }
    }

    public List<Edge> getEdges(String stationCode) {
        return adjacencyList.getOrDefault(stationCode, new ArrayList<>());
    }

    public Map<String, Station> getStations() {
        return stations;
    }

    public Map<String, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public void clear() {
        stations.clear();
        adjacencyList.clear();
    }
}
