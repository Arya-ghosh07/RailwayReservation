package service;

import model.Station;
import graph.StationGraph;
import java.util.List;

/**
 * Manages stations and updates the railway network graph.
 */
public class StationService {
    private StationGraph stationGraph;
    private PersistenceService persistenceService;

    public StationService(StationGraph stationGraph, PersistenceService persistenceService) {
        this.stationGraph = stationGraph;
        this.persistenceService = persistenceService;
        
        // Load stations and populate graph
        List<Station> stations = persistenceService.loadStations();
        for (Station s : stations) {
            stationGraph.addStation(s);
        }
        
        // Load edges from data/edges.txt
        try {
            java.util.List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("data/edges.txt"));
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    addConnection(parts[0], parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]));
                }
            }
        } catch (Exception e) {
            System.err.println("Could not load edges: " + e.getMessage());
        }
    }

    public StationGraph getStationGraph() {
        return stationGraph;
    }

    public boolean addStation(String code, String name, int x, int y) {
        // Placeholder
        return false;
    }

    public boolean deleteStation(String code) {
        // Placeholder
        return false;
    }

    public Station getStation(String code) {
        return stationGraph.getStations().get(code);
    }

    public List<Station> getAllStations() {
        return new java.util.ArrayList<>(stationGraph.getStations().values());
    }

    public void addConnection(String sourceCode, String destCode, double distance, int travelTimeMinutes) {
        stationGraph.addEdge(sourceCode, destCode, distance, travelTimeMinutes);
    }

    public void removeConnection(String sourceCode, String destCode) {
        // Placeholder
    }
}
