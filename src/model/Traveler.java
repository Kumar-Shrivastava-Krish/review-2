package model;

public class Traveler {
    private int id;
    private String name;
    private String email;
    private String location;

    public Traveler(int id, String name, String email, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getLocation() {
        return location;
    }
}
