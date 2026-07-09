package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides BFS and DFS traversal implementations to search for paths in the railway network.
 */
public class GraphSearch {

    /**
     * Finds all paths between source and destination using Breadth-First Search (BFS).
     * BFS will naturally find paths with fewer stops/hops first.
     */
    public List<List<String>> findAllPathsBFS(StationGraph graph, String sourceCode, String destinationCode) {
        // Placeholder
        return new ArrayList<>();
    }

    /**
     * Finds all paths between source and destination using Depth-First Search (DFS).
     * DFS explores paths in-depth and can backtrack to find all valid routes.
     */
    public List<List<String>> findAllPathsDFS(StationGraph graph, String sourceCode, String destinationCode) {
        // Placeholder
        return new ArrayList<>();
    }
}
