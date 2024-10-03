package controller;

import java.sql.*;

import model.User;

public class UserController {
    private static final String URL = "jdbc:mysql://localhost:3306/traveler_db";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "krish1410"; // Your MySQL password

    // Initialize the user table
    public void initializeUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " username VARCHAR(255) NOT NULL UNIQUE,\n"
                + " password VARCHAR(255) NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("User table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error initializing user table: " + e.getMessage());
        }
    }

    // Register a new user
    public void register(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    // Authenticate a user
    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if user exists
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }
}
