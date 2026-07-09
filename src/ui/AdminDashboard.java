package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard containing features for admin users: managing trains, stations, schedules, reports.
 */
public class AdminDashboard extends JPanel {
    private MainFrame mainFrame;

    public AdminDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Admin Management Area."));
        add(centerPanel, BorderLayout.CENTER);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            mainFrame.getUserService().logout();
            mainFrame.showPanel("LoginPanel");
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
