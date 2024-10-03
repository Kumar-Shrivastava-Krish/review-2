package controller;

import model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobController {
    private static final String URL = "jdbc:mysql://localhost:3306/traveler_db"; // Change database name if needed
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "krish1410"; // Your MySQL password

    // Create a new table if it doesn't exist
    public void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS jobs (\n"
                + " id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " title VARCHAR(255) NOT NULL,\n"
                + " company VARCHAR(255) NOT NULL,\n"
                + " location VARCHAR(255) NOT NULL,\n"
                + " description TEXT NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("Jobs table created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add a job to the database
    public void addJob(Job job) {
        String sql = "INSERT INTO jobs(title, company, location, description) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, job.getTitle());
            pstmt.setString(2, job.getCompany());
            pstmt.setString(3, job.getLocation());
            pstmt.setString(4, job.getDescription());
            pstmt.executeUpdate();
            System.out.println("Job added: " + job.getTitle());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Get all jobs from the database
    public List<Job> getJobs() {
        List<Job> jobList = new ArrayList<>();
        String sql = "SELECT * FROM jobs";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Job job = new Job(
                    rs.getString("title"),
                    rs.getString("company"),
                    rs.getString("location"),
                    rs.getString("description")
                );
                jobList.add(job);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobList;
    }
}
