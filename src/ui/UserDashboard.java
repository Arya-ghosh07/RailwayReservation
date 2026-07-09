package ui;

import model.Train;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Dashboard containing features for standard users: searching, booking, history.
 */
public class UserDashboard extends JPanel {
    private MainFrame mainFrame;
    private JTable trainTable;
    private DefaultTableModel tableModel;

    public UserDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("User Dashboard - Train Search & Booking", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Center Panel with Table
        String[] columnNames = {"Train No", "Name", "Source", "Destination", "Seats", "Fare", "Days"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        trainTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(trainTable);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Search Bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search Train (No/Name):"));
        JTextField searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton clearBtn = new JButton("Clear");
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(clearBtn);
        
        searchBtn.addActionListener(e -> refreshTrainList(searchField.getText().trim()));
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            refreshTrainList("");
        });
        
        JPanel topCenterPanel = new JPanel(new BorderLayout());
        topCenterPanel.add(searchPanel, BorderLayout.NORTH);
        topCenterPanel.add(new JLabel("Available Trains:"), BorderLayout.SOUTH);
        
        centerPanel.add(topCenterPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel with Actions
        JPanel bottomPanel = new JPanel();
        
        JButton refreshBtn = new JButton("Refresh List");
        refreshBtn.addActionListener(e -> refreshTrainList(""));
        
        JButton bookBtn = new JButton("Book Selected Train");
        bookBtn.addActionListener(e -> bookSelectedTrain());

        JButton myBookingsBtn = new JButton("My Bookings");
        myBookingsBtn.addActionListener(e -> showMyBookingsDialog());

        JButton networkBtn = new JButton("Find Shortest Route");
        networkBtn.addActionListener(e -> mainFrame.showPanel("NetworkPanel"));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            mainFrame.getUserService().logout();
            mainFrame.showPanel("LoginPanel");
        });

        bottomPanel.add(refreshBtn);
        bottomPanel.add(bookBtn);
        bottomPanel.add(myBookingsBtn);
        bottomPanel.add(networkBtn);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Called when the panel is shown
    @Override
    public void addNotify() {
        super.addNotify();
        refreshTrainList("");
    }

    private void refreshTrainList(String query) {
        tableModel.setRowCount(0); // clear existing
        if (mainFrame.getTrainService() != null) {
            List<Train> trains = mainFrame.getTrainService().getAllTrains();
            for (Train t : trains) {
                boolean match = query.isEmpty() || 
                                t.getTrainNumber().toLowerCase().contains(query.toLowerCase()) || 
                                t.getTrainName().toLowerCase().contains(query.toLowerCase());
                
                if (match) {
                    tableModel.addRow(new Object[]{
                        t.getTrainNumber(), t.getTrainName(), 
                        t.getSourceStationCode(), t.getDestinationStationCode(),
                        t.getAvailableSeats(), t.getBaseFare(), t.getDaysOfOperation()
                    });
                }
            }
        }
    }

    private void bookSelectedTrain() {
        int selectedRow = trainTable.getSelectedRow();
        if (selectedRow >= 0) {
            String trainNo = (String) tableModel.getValueAt(selectedRow, 0);
            String trainName = (String) tableModel.getValueAt(selectedRow, 1);
            String source = (String) tableModel.getValueAt(selectedRow, 2);
            String dest = (String) tableModel.getValueAt(selectedRow, 3);

            JTextField nameField = new JTextField(15);
            JTextField ageField = new JTextField(5);
            JTextField dateField = new JTextField(10);
            dateField.setText(java.time.LocalDate.now().toString());
            
            String[] seatOptions = {"No Preference", "Lower", "Middle", "Upper", "Side Lower", "Side Upper", "Window"};
            JComboBox<String> seatBox = new JComboBox<>(seatOptions);
            
            int num1 = (int)(Math.random() * 10) + 1;
            int num2 = (int)(Math.random() * 10) + 1;
            int expectedSum = num1 + num2;
            JTextField captchaField = new JTextField(5);

            JPanel myPanel = new JPanel(new GridLayout(5, 2, 5, 5));
            myPanel.add(new JLabel("Passenger Name:"));
            myPanel.add(nameField);
            myPanel.add(new JLabel("Age:"));
            myPanel.add(ageField);
            myPanel.add(new JLabel("Travel Date (YYYY-MM-DD):"));
            myPanel.add(dateField);
            myPanel.add(new JLabel("Seat Preference:"));
            myPanel.add(seatBox);
            myPanel.add(new JLabel("CAPTCHA: What is " + num1 + " + " + num2 + "?"));
            myPanel.add(captchaField);

            int result = JOptionPane.showConfirmDialog(this, myPanel, 
                     "Book Ticket - " + trainName + " (" + trainNo + ")", JOptionPane.OK_CANCEL_OPTION);
                     
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String date = dateField.getText();
                    String seatPref = (String) seatBox.getSelectedItem();
                    int userCaptcha = Integer.parseInt(captchaField.getText());
                    
                    if (userCaptcha != expectedSum) {
                        JOptionPane.showMessageDialog(this, "Incorrect CAPTCHA! Booking failed.", "Security Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (name.trim().isEmpty() || date.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all details.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    String userId = mainFrame.getUserService().getCurrentUser().getId();
                    model.Ticket ticket = mainFrame.getBookingService().bookTicket(userId, trainNo, name, age, source, dest, date, seatPref);
                    
                    if (ticket != null) {
                        JOptionPane.showMessageDialog(this, "Ticket Booked Successfully!\n\nPNR: " + ticket.getPnr() + 
                            "\nFare: Rs " + ticket.getFare() + "\nSeat Pref: " + ticket.getSeatPreference() + "\nStatus: " + ticket.getStatus().name());
                    } else {
                        JOptionPane.showMessageDialog(this, "Booking failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number format in Age or CAPTCHA.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a train from the list to book.");
        }
    }

    private void showMyBookingsDialog() {
        String userId = mainFrame.getUserService().getCurrentUser().getId();
        List<model.Ticket> tickets = mainFrame.getBookingService().getBookingHistory(userId);
        
        String[] cols = {"PNR", "Train", "Name", "Date", "Status", "Fare"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        
        for (model.Ticket t : tickets) {
            model.addRow(new Object[]{
                t.getPnr(), t.getTrainNumber(), t.getPassengerName(), 
                t.getTravelDate(), t.getStatus().name(), t.getFare()
            });
        }
        
        JTable historyTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton cancelBtn = new JButton("Cancel Selected Ticket");
        cancelBtn.addActionListener(e -> {
            int row = historyTable.getSelectedRow();
            if (row >= 0) {
                String pnr = (String) model.getValueAt(row, 0);
                String status = (String) model.getValueAt(row, 4);
                if (status.equals("CANCELLED")) {
                    JOptionPane.showMessageDialog(this, "Ticket is already cancelled.");
                    return;
                }
                
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel PNR: " + pnr + "?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = mainFrame.getBookingService().cancelTicket(pnr);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Ticket cancelled successfully.");
                        model.setValueAt("CANCELLED", row, 4);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to cancel ticket.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a ticket to cancel.");
            }
        });
        
        JPanel bottom = new JPanel();
        bottom.add(cancelBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        
        JOptionPane.showMessageDialog(this, panel, "My Bookings", JOptionPane.PLAIN_MESSAGE);
    }
}
