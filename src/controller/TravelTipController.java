package controller;

import model.TravelTip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelTipController {
    private static final String URL = "jdbc:mysql://localhost:3306/traveler_db";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "krish1410"; // Your MySQL password

    public void initializeTravelTipTable() {
        String sql = "CREATE TABLE IF NOT EXISTS travel_tip (\n"
                + " id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " tip VARCHAR(255) NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("Travel Tip table created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTravelTip(TravelTip travelTip) {
        String sql = "INSERT INTO travel_tip (tip) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, travelTip.getTip());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<TravelTip> getTravelTips() {
        List<TravelTip> tipList = new ArrayList<>();
        String sql = "SELECT * FROM travel_tip";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                TravelTip tip = new TravelTip(
                    rs.getInt("id"),
                    rs.getString("tip")
                );
                tipList.add(tip);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tipList;
    }
}
