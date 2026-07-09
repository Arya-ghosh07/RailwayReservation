package graph;

import java.util.List;

/**
 * Implementation of Dijkstra's Algorithm to find the shortest path in the railway network.
 */
public class DijkstraAlgorithm {

    /**
     * Represents a node or result of the pathfinder.
     */
    public static class PathResult {
        private List<String> stationCodes;
        private double totalDistance;
        private int totalTravelTimeMinutes;

        public PathResult(List<String> stationCodes, double totalDistance, int totalTravelTimeMinutes) {
            this.stationCodes = stationCodes;
            this.totalDistance = totalDistance;
            this.totalTravelTimeMinutes = totalTravelTimeMinutes;
        }

        public List<String> getStationCodes() {
            return stationCodes;
        }

        public double getTotalDistance() {
            return totalDistance;
        }

        public int getTotalTravelTimeMinutes() {
            return totalTravelTimeMinutes;
        }
    }

    /**
     * Finds the shortest path between source and destination station in the given graph.
     * 
     * @param graph The station network graph
     * @param sourceCode The starting station code
     * @param destinationCode The ending station code
     * @param sortByDistance True if optimizing for distance, false if optimizing for travel time
     * @return PathResult containing the path and accumulated weights
     */
    public PathResult findShortestPath(StationGraph graph, String sourceCode, String destinationCode, boolean sortByDistance) {
        java.util.Map<String, Double> distances = new java.util.HashMap<>();
        java.util.Map<String, Integer> travelTimes = new java.util.HashMap<>();
        java.util.Map<String, String> previous = new java.util.HashMap<>();
        java.util.PriorityQueue<String> queue = new java.util.PriorityQueue<>(
                (a, b) -> sortByDistance 
                          ? Double.compare(distances.get(a), distances.get(b))
                          : Integer.compare(travelTimes.get(a), travelTimes.get(b))
        );

        for (String stationCode : graph.getStations().keySet()) {
            distances.put(stationCode, Double.MAX_VALUE);
            travelTimes.put(stationCode, Integer.MAX_VALUE);
        }

        distances.put(sourceCode, 0.0);
        travelTimes.put(sourceCode, 0);
        queue.add(sourceCode);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(destinationCode)) {
                break; // Found shortest path
            }

            for (Edge edge : graph.getEdges(current)) {
                String neighbor = edge.getDestinationCode();
                
                double newDist = distances.get(current) + edge.getDistance();
                int newTime = travelTimes.get(current) + edge.getTravelTimeMinutes();

                boolean isBetter = sortByDistance ? newDist < distances.get(neighbor) : newTime < travelTimes.get(neighbor);

                if (isBetter) {
                    distances.put(neighbor, newDist);
                    travelTimes.put(neighbor, newTime);
                    previous.put(neighbor, current);
                    
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        if (!previous.containsKey(destinationCode) && !sourceCode.equals(destinationCode)) {
            return null; // No path found
        }

        java.util.List<String> path = new java.util.ArrayList<>();
        String curr = destinationCode;
        while (curr != null) {
            path.add(0, curr);
            curr = previous.get(curr);
        }

        return new PathResult(path, distances.get(destinationCode), travelTimes.get(destinationCode));
    }
}
