package view;

import controller.JobController;
import controller.JournalController;
import controller.TouristSpotController;
import controller.ConnectionController;
import controller.UserController;
import model.Job;
import model.JournalEntry;
import model.TouristSpot;
import model.TravelerConnection;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class TravelerApp {
    private static JobController jobController = new JobController();
    private static JournalController journalController = new JournalController();
    private static TouristSpotController touristSpotController = new TouristSpotController();
    private static ConnectionController connectionController = new ConnectionController();
    private static UserController userController = new UserController();

    public static void main(String[] args) {
        // Initialize the database and create the necessary tables
        jobController.initializeDatabase();
        journalController.initializeJournalEntryTable();
        touristSpotController.initializeTouristSpotTable();
        connectionController.initializeConnectionsTable();
        userController.initializeUserTable();

        // Sample user registration (username: krish, password: krish2)
        userController.register(new User("krish", "krish2"));

        // Prompt for user login
        if (userLogin()) {
            showMainApplication();
        }
    }

    private static boolean userLogin() {
        JFrame loginFrame = new JFrame("User Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);
        loginFrame.setLocationRelativeTo(null); // Center on the screen
        loginFrame.setLayout(new GridLayout(3, 2, 10, 10));
        loginFrame.getContentPane().setBackground(new Color(255, 255, 255)); // White background

        // Input fields for username and password
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JLabel statusLabel = new JLabel("", JLabel.CENTER);

        // Login button action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (userController.authenticate(username, password)) {
                statusLabel.setText("Login successful!");
                loginFrame.dispose(); // Close the login frame
                showMainApplication(); // Proceed to main application
            } else {
                statusLabel.setText("Login failed. Try again.");
            }
        });

        // Add components to the login frame
        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(statusLabel);

        loginFrame.setVisible(true);
        return false; // Return false if login window is shown
    }

    private static void showMainApplication() {
        // Main application JFrame setup
        JFrame frame = new JFrame("Traveler App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().setBackground(new Color(255, 255, 255)); // White background

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns, 10px gaps
        panel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Create buttons with custom styles
        JButton findJobsButton = createStyledButton("Find Remote Jobs");
        JButton seeJournalButton = createStyledButton("See Journal Entries");
        JButton addJournalButton = createStyledButton("Add Journal Entry");
        JButton seeTouristSpotsButton = createStyledButton("See Tourist Spots");
        JButton connectWithOthersButton = createStyledButton("Connect with Others");
        JButton showConnectionsButton = createStyledButton("Show Connections");

        // Status bar for feedback
        JLabel statusBar = new JLabel("Status: Ready", JLabel.LEFT);
        statusBar.setFont(new Font("Arial", Font.PLAIN, 12));
        statusBar.setForeground(Color.GRAY); // Set status bar text color to gray

        // Add ActionListener to the buttons
        findJobsButton.addActionListener(e -> showJobsDialog(frame, statusBar));
        seeJournalButton.addActionListener(e -> showJournalEntriesDialog(frame, statusBar));
        addJournalButton.addActionListener(e -> addJournalEntryDialog(frame, statusBar));
        seeTouristSpotsButton.addActionListener(e -> showTouristSpotsDialog(frame, statusBar));
        connectWithOthersButton.addActionListener(e -> connectWithOthersDialog(frame, statusBar));
        showConnectionsButton.addActionListener(e -> showConnectionsDialog(frame, statusBar));

        // Add buttons to the panel
        panel.add(findJobsButton);
        panel.add(seeJournalButton);
        panel.add(addJournalButton);
        panel.add(seeTouristSpotsButton);
        panel.add(connectWithOthersButton);
        panel.add(showConnectionsButton);

        // Add the panel and status bar to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

        // Set frame visibility
        frame.setVisible(true);
    }

    private static void showJobsDialog(JFrame parentFrame, JLabel statusBar) {
        StringBuilder jobsDisplay = new StringBuilder("Job Listings:\n");
        for (Job job : jobController.getJobs()) {
            jobsDisplay.append(job.getTitle()).append(" at ").append(job.getCompany()).append("\n");
        }
        showMessageDialog(parentFrame, jobsDisplay.toString(), "Job Listings", statusBar);
    }

    private static void showJournalEntriesDialog(JFrame parentFrame, JLabel statusBar) {
        StringBuilder journalDisplay = new StringBuilder("Journal Entries:\n");
        for (JournalEntry entry : journalController.getJournalEntries()) {
            journalDisplay.append("Entry: ").append(entry.getEntry()).append(" on ").append(entry.getEntryDate()).append("\n");
        }
        showMessageDialog(parentFrame, journalDisplay.toString(), "Journal Entries", statusBar);
    }

    private static void addJournalEntryDialog(JFrame parentFrame, JLabel statusBar) {
        JTextField travelerIdField = new JTextField();
        JTextField entryField = new JTextField();
        JTextField dateField = new JTextField();
        Object[] message = {
            "Traveler ID:", travelerIdField,
            "Entry:", entryField,
            "Entry Date (YYYY-MM-DD):", dateField
        };

        int option = JOptionPane.showConfirmDialog(parentFrame, message, "Add Journal Entry", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int travelerId = Integer.parseInt(travelerIdField.getText());
                String entry = entryField.getText();
                Date entryDate = Date.valueOf(dateField.getText());
                JournalEntry journalEntry = new JournalEntry(0, travelerId, entry, entryDate);
                journalController.addJournalEntry(journalEntry);
                statusBar.setText("Status: Added journal entry.");
                JOptionPane.showMessageDialog(parentFrame, "Journal Entry added successfully.");
            } catch (Exception ex) {
                statusBar.setText("Status: Error adding journal entry.");
                JOptionPane.showMessageDialog(parentFrame, "Error: " + ex.getMessage());
            }
        }
    }

    private static void showTouristSpotsDialog(JFrame parentFrame, JLabel statusBar) {
        StringBuilder spotsDisplay = new StringBuilder("Tourist Spots:\n");
        for (TouristSpot spot : touristSpotController.getTouristSpots()) {
            spotsDisplay.append(spot.getName()).append(" in ").append(spot.getLocation())
                    .append(": ").append(spot.getDescription()).append("\n");
        }
        showMessageDialog(parentFrame, spotsDisplay.toString(), "Tourist Spots", statusBar);
    }

    private static void connectWithOthersDialog(JFrame parentFrame, JLabel statusBar) {
        JTextField travelerId1Field = new JTextField();
        JTextField travelerId2Field = new JTextField();
        Object[] message = {
            "Your Traveler ID:", travelerId1Field,
            "Traveler ID to Connect With:", travelerId2Field
        };

        int option = JOptionPane.showConfirmDialog(parentFrame, message, "Connect with Others", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int travelerId1 = Integer.parseInt(travelerId1Field.getText());
                int travelerId2 = Integer.parseInt(travelerId2Field.getText());
                TravelerConnection connection = new TravelerConnection(0, traveler1, traveler2);
                connectionController.addConnection(connection); // Ensure this method works correctly
                statusBar.setText("Status: Added connection.");
                JOptionPane.showMessageDialog(parentFrame, "Connection added successfully.");
            } catch (Exception ex) {
                statusBar.setText("Status: Error adding connection.");
                JOptionPane.showMessageDialog(parentFrame, "Error: " + ex.getMessage());
            }
        }
    }

    private static void showConnectionsDialog(JFrame parentFrame, JLabel statusBar) {
        List<TravelerConnection> connections = connectionController.getConnections(); // Ensure this method is correctly implemented
        StringBuilder connectionsDisplay = new StringBuilder("Connections:\n");
        for (TravelerConnection connection : connections) {
            connectionsDisplay.append("Traveler  1: ").append(connection.getTravelerName1())
                    .append(", Traveler  2: ").append(connection.getTravelerId2()).append("\n");
        }
        showMessageDialog(parentFrame, connectionsDisplay.toString(), "Connections", statusBar);
    }

    private static void showMessageDialog(JFrame parentFrame, String message, String title, JLabel statusBar) {
        // Create a modal dialog centered on the parent frame
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame); // Center on parent frame

        // Add a scroll pane for longer messages
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(245, 245, 245));
        textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            dialog.dispose();
            statusBar.setText("Status: Dialog closed.");
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor on hover

        // Adding hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Cornflower blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180)); // Steel blue
            }
        });

        return button;
    }
}
