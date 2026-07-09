package ui;

import graph.DijkstraAlgorithm;
import graph.Edge;
import graph.StationGraph;
import model.Station;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Panel to visualize the railway station network graph and display Dijkstra/BFS/DFS pathfinding.
 */
public class NetworkPanel extends JPanel {
    private MainFrame mainFrame;
    private JComboBox<Station> sourceBox;
    private JComboBox<Station> destBox;
    private JTextArea resultArea;
    private GraphDrawPanel graphDrawPanel;
    private List<String> currentShortestPath;

    public NetworkPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Railway Network - Shortest Path Finder", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel controls = new JPanel();
        sourceBox = new JComboBox<>();
        destBox = new JComboBox<>();
        JButton findBtn = new JButton("Find Shortest Path");
        
        controls.add(new JLabel("Source:"));
        controls.add(sourceBox);
        controls.add(new JLabel("Destination:"));
        controls.add(destBox);
        controls.add(findBtn);
        
        add(controls, BorderLayout.NORTH); // Moved to North (below title) to give room for map

        // Graph drawing area
        graphDrawPanel = new GraphDrawPanel();
        add(graphDrawPanel, BorderLayout.CENTER);

        resultArea = new JTextArea(8, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(resultArea);
        
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.add(scroll, BorderLayout.CENTER);
        
        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.addActionListener(e -> {
            if (mainFrame.getUserService().getCurrentUser().getRole() == model.Role.ADMIN) {
                mainFrame.showPanel("AdminDashboard");
            } else {
                mainFrame.showPanel("UserDashboard");
            }
        });
        JPanel navPanel = new JPanel();
        navPanel.add(backBtn);
        bottomContainer.add(navPanel, BorderLayout.SOUTH);
        
        add(bottomContainer, BorderLayout.SOUTH);

        findBtn.addActionListener(e -> findPath());
    }

    @Override
    public void addNotify() {
        super.addNotify();
        populateStations();
        currentShortestPath = null;
        graphDrawPanel.repaint();
    }

    private void populateStations() {
        sourceBox.removeAllItems();
        destBox.removeAllItems();
        if (mainFrame.getStationService() != null) {
            List<Station> stations = mainFrame.getStationService().getAllStations();
            for (Station s : stations) {
                sourceBox.addItem(s);
                destBox.addItem(s);
            }
        }
    }

    private void findPath() {
        Station source = (Station) sourceBox.getSelectedItem();
        Station dest = (Station) destBox.getSelectedItem();
        
        if (source == null || dest == null) {
            resultArea.setText("Please select source and destination.");
            return;
        }

        StationGraph graph = mainFrame.getStationService().getStationGraph();
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        
        DijkstraAlgorithm.PathResult result = dijkstra.findShortestPath(graph, source.getCode(), dest.getCode(), true);
        
        if (result == null) {
            resultArea.setText("No path found between " + source.getName() + " and " + dest.getName());
            currentShortestPath = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Shortest Path: ");
            
            List<String> path = result.getStationCodes();
            for (int i = 0; i < path.size(); i++) {
                Station s = mainFrame.getStationService().getStation(path.get(i));
                sb.append(s.getName());
                if (i < path.size() - 1) {
                    sb.append(" -> ");
                }
            }
            
            sb.append("\nDistance: ").append(result.getTotalDistance()).append(" km | ");
            sb.append("Time: ").append(result.getTotalTravelTimeMinutes()).append(" mins\n");
            
            sb.append("\nAvailable Direct Trains:\n");
            boolean trainFound = false;
            for (model.Train t : mainFrame.getTrainService().getAllTrains()) {
                if ((t.getSourceStationCode().equals(source.getCode()) && t.getDestinationStationCode().equals(dest.getCode())) ||
                    (t.getSourceStationCode().equals(dest.getCode()) && t.getDestinationStationCode().equals(source.getCode()))) {
                    sb.append("- ").append(t.getTrainName()).append(" (").append(t.getTrainNumber()).append(") - Runs: ").append(t.getDaysOfOperation()).append("\n");
                    trainFound = true;
                }
            }
            
            if (!trainFound) {
                sb.append("No direct trains found. Hop-by-hop Route:\n");
                for (int i = 0; i < path.size() - 1; i++) {
                    String hopSrc = path.get(i);
                    String hopDst = path.get(i+1);
                    Station s1 = mainFrame.getStationService().getStation(hopSrc);
                    Station s2 = mainFrame.getStationService().getStation(hopDst);
                    
                    sb.append("  ").append(s1.getName()).append(" -> ").append(s2.getName()).append(":\n");
                    boolean hopTrainFound = false;
                    for (model.Train t : mainFrame.getTrainService().getAllTrains()) {
                        // Check if train connects these two stations (either direction)
                        if ((t.getSourceStationCode().equals(hopSrc) && t.getDestinationStationCode().equals(hopDst)) ||
                            (t.getSourceStationCode().equals(hopDst) && t.getDestinationStationCode().equals(hopSrc))) {
                            sb.append("    * ").append(t.getTrainName()).append(" (").append(t.getTrainNumber()).append(")\n");
                            hopTrainFound = true;
                            break; // Show at most one train per hop to keep it clean
                        }
                    }
                    if (!hopTrainFound) {
                        sb.append("    * (Use Local Connecting Trains)\n");
                    }
                }
            }
            
            resultArea.setText(sb.toString());
            resultArea.setCaretPosition(0); // Scroll to top
            currentShortestPath = path;
        }
        graphDrawPanel.repaint();
    }

    private class GraphDrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (mainFrame == null || mainFrame.getStationService() == null) return;

            StationGraph graph = mainFrame.getStationService().getStationGraph();
            Map<String, Station> stations = graph.getStations();
            Map<String, List<Edge>> adjacencyList = graph.getAdjacencyList();

            // Draw all edges (grey lines)
            g2.setColor(Color.LIGHT_GRAY);
            g2.setStroke(new BasicStroke(2));
            for (String srcCode : adjacencyList.keySet()) {
                Station src = stations.get(srcCode);
                if (src == null) continue;
                for (Edge edge : adjacencyList.get(srcCode)) {
                    Station dst = stations.get(edge.getDestinationCode());
                    if (dst != null) {
                        g2.drawLine(src.getX(), src.getY(), dst.getX(), dst.getY());
                    }
                }
            }

            // Highlight shortest path edges (blue thick lines)
            if (currentShortestPath != null && currentShortestPath.size() > 1) {
                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(4));
                for (int i = 0; i < currentShortestPath.size() - 1; i++) {
                    Station src = stations.get(currentShortestPath.get(i));
                    Station dst = stations.get(currentShortestPath.get(i+1));
                    if (src != null && dst != null) {
                        g2.drawLine(src.getX(), src.getY(), dst.getX(), dst.getY());
                    }
                }
            }

            // Draw stations (nodes)
            int radius = 8;
            for (Station s : stations.values()) {
                boolean inPath = currentShortestPath != null && currentShortestPath.contains(s.getCode());
                g2.setColor(inPath ? Color.BLUE : Color.RED);
                g2.fillOval(s.getX() - radius, s.getY() - radius, radius * 2, radius * 2);
                
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString(s.getName(), s.getX() + 10, s.getY() - 5);
            }
        }
    }
}
