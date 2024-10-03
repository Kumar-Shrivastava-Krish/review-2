package controller;

import model.JournalEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalController {
    private static final String URL = "jdbc:mysql://localhost:3306/traveler_db";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "krish1410"; // Your MySQL password

    public void initializeJournalEntryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS journal_entry (\n"
                + " id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " traveler_id INT NOT NULL,\n"
                + " entry TEXT NOT NULL,\n"
                + " entry_date DATE NOT NULL,\n"
                + " FOREIGN KEY (traveler_id) REFERENCES traveler(id)\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("Journal Entry table created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<JournalEntry> getJournalEntries() {
        List<JournalEntry> entryList = new ArrayList<>();
        String sql = "SELECT * FROM journal_entry";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                JournalEntry entry = new JournalEntry(
                    rs.getInt("id"),
                    rs.getInt("traveler_id"),
                    rs.getString("entry"),
                    rs.getDate("entry_date")
                );
                entryList.add(entry);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entryList;
    }

    public void addJournalEntry(JournalEntry journalEntry) {
        String sql = "INSERT INTO journal_entry (traveler_id, entry, entry_date) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, journalEntry.getTravelerId());
            pstmt.setString(2, journalEntry.getEntry());
            pstmt.setDate(3, journalEntry.getEntryDate());
            pstmt.executeUpdate();
            System.out.println("Journal Entry added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
