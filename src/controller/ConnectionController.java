package controller;

import model.TravelerConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionController {
    private Connection connection;

    public ConnectionController() {
        // Set up the database connection
        try {
            // Change the database URL, username, and password as per your configuration
            String url = "jdbc:mysql://localhost:3306/traveler_db";
            String user = "root";
            String password = "krish1410";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initializeConnectionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS traveler_connections (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "traveler_name1 VARCHAR(255), " +
                     "traveler_name2 VARCHAR(255))";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addConnection(TravelerConnection connection) {
        String sql = "INSERT INTO traveler_connections (traveler_name1, traveler_name2) VALUES (?, ?)";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, connection.getTravelerName1());
            pstmt.setString(2, connection.getTravelerName2());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TravelerConnection> getConnections() {
        List<TravelerConnection> connections = new ArrayList<>();
        String sql = "SELECT traveler_name1, traveler_name2 FROM traveler_connections";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String travelerName1 = rs.getString("traveler_name1");
                String travelerName2 = rs.getString("traveler_name2");
                connections.add(new TravelerConnection(0, travelerName1, travelerName2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connections;
    }
}
