package model;

public class TravelTip {
    private int id;
    private String tip;

    public TravelTip(int id, String tip) {
        this.id = id;
        this.tip = tip;
    }

    public TravelTip(String tip) {
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public String getTip() {
        return tip;
    }
}
