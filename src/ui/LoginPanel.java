package ui;

import model.Role;
import model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Panel handling user and admin login and registration forms.
 */
public class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        formPanel.add(loginBtn);
        formPanel.add(registerBtn);

        add(formPanel);

        loginBtn.addActionListener(e -> performLogin());
        registerBtn.addActionListener(e -> performRegistration());
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        User user = mainFrame.getUserService().login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            usernameField.setText("");
            passwordField.setText("");
            
            if (user.getRole() == Role.ADMIN) {
                mainFrame.showPanel("AdminDashboard");
            } else {
                mainFrame.showPanel("UserDashboard");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        // For simplicity, let's register users as regular USERs by default.
        try {
            boolean success = mainFrame.getUserService().register(username, password, username, Role.USER);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
