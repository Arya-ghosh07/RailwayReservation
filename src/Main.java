import ui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point of the Intelligent Railway Reservation and Network Management System.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
