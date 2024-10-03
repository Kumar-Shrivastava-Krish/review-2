package controller;

import model.TouristSpot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TouristSpotController {
    private static final String URL = "jdbc:mysql://localhost:3306/traveler_db";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "krish1410"; // Your MySQL password

    public void initializeTouristSpotTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tourist_spot (\n"
                + " id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " name VARCHAR(255) NOT NULL,\n"
                + " location VARCHAR(255) NOT NULL,\n"
                + " description TEXT NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("Tourist Spot table created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTouristSpot(TouristSpot spot) {
        String sql = "INSERT INTO tourist_spot (name, location, description) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, spot.getName());
            pstmt.setString(2, spot.getLocation());
            pstmt.setString(3, spot.getDescription());
            pstmt.executeUpdate();
            System.out.println("Tourist Spot added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<TouristSpot> getTouristSpots() {
        List<TouristSpot> spotList = new ArrayList<>();
        String sql = "SELECT * FROM tourist_spot";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                TouristSpot spot = new TouristSpot(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getString("description")
                );
                spotList.add(spot);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return spotList;
    }
}
