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

    private static class NodeRecord {
        String stationCode;
        double sortWeight;

        NodeRecord(String stationCode, double sortWeight) {
            this.stationCode = stationCode;
            this.sortWeight = sortWeight;
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
        
        // PriorityQueue now stores NodeRecords to avoid O(N) remove() calls
        java.util.PriorityQueue<NodeRecord> queue = new java.util.PriorityQueue<>(
                java.util.Comparator.comparingDouble(nr -> nr.sortWeight)
        );

        for (String stationCode : graph.getStations().keySet()) {
            distances.put(stationCode, Double.MAX_VALUE);
            travelTimes.put(stationCode, Integer.MAX_VALUE);
        }

        distances.put(sourceCode, 0.0);
        travelTimes.put(sourceCode, 0);
        queue.add(new NodeRecord(sourceCode, 0.0));

        // Keep track of visited nodes to skip stale records in the queue
        java.util.Set<String> visited = new java.util.HashSet<>();

        while (!queue.isEmpty()) {
            NodeRecord currentRecord = queue.poll();
            String current = currentRecord.stationCode;
            
            // Skip if we've already processed this node via a shorter path
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            if (current.equals(destinationCode)) {
                break; // Found shortest path
            }

            for (Edge edge : graph.getEdges(current)) {
                String neighbor = edge.getDestinationCode();
                if (visited.contains(neighbor)) {
                    continue;
                }
                
                double newDist = distances.get(current) + edge.getDistance();
                int newTime = travelTimes.get(current) + edge.getTravelTimeMinutes();

                boolean isBetter = sortByDistance ? newDist < distances.get(neighbor) : newTime < travelTimes.get(neighbor);

                if (isBetter) {
                    distances.put(neighbor, newDist);
                    travelTimes.put(neighbor, newTime);
                    previous.put(neighbor, current);
                    
                    double newWeight = sortByDistance ? newDist : newTime;
                    queue.add(new NodeRecord(neighbor, newWeight));
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
