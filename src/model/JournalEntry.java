package model;

import java.sql.Date;

public class JournalEntry {
    private int id; // Field for unique ID
    private int travelerId; // ID of the traveler associated with the entry
    private String entry; // The journal entry text
    private Date entryDate; // Use java.sql.Date for better compatibility with the database

    // Constructor
    public JournalEntry(int id, int travelerId, String entry, Date entryDate) {
        this.id = id;
        this.travelerId = travelerId;
        this.entry = entry;
        this.entryDate = entryDate;
    }

    // Getters
    public int getId() {
        return id; // Getter for ID
    }

    public int getTravelerId() {
        return travelerId; // Getter for traveler ID
    }

    public String getEntry() {
        return entry; // Getter for journal entry text
    }

    public Date getEntryDate() {
        return entryDate; // Getter for entry date
    }
}
