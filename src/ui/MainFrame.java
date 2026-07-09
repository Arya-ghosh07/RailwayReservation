package ui;

import service.FilePersistenceService;
import service.UserService;
import model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Main Frame of the application. Manages page transitions using CardLayout.
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Services
    private UserService userService;
    private service.TrainService trainService;
    private service.BookingService bookingService;
    private service.StationService stationService;

    public MainFrame() {
        super("Intelligent Railway Reservation & Network Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Initialize services
        FilePersistenceService persistenceService = new FilePersistenceService();
        userService = new UserService(persistenceService);
        trainService = new service.TrainService(persistenceService);
        bookingService = new service.BookingService(persistenceService, trainService);
        
        graph.StationGraph stationGraph = new graph.StationGraph();
        stationService = new service.StationService(stationGraph, persistenceService);

        // Setup CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add panels
        LoginPanel loginPanel = new LoginPanel(this);
        UserDashboard userDashboard = new UserDashboard(this);
        AdminDashboard adminDashboard = new AdminDashboard(this);
        NetworkPanel networkPanel = new NetworkPanel(this);

        cardPanel.add(loginPanel, "LoginPanel");
        cardPanel.add(userDashboard, "UserDashboard");
        cardPanel.add(adminDashboard, "AdminDashboard");
        cardPanel.add(networkPanel, "NetworkPanel");

        add(cardPanel);

        // Show default panel
        showPanel("LoginPanel");
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public UserService getUserService() {
        return userService;
    }

    public service.TrainService getTrainService() {
        return trainService;
    }

    public service.BookingService getBookingService() {
        return bookingService;
    }

    public service.StationService getStationService() {
        return stationService;
    }
}
