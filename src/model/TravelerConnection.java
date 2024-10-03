package model;

public class TravelerConnection {
   // Or you can remove it if not needed
    private char travelerName1;
    private char travelerName2;

    public TravelerConnection(int id, char traveler1, char traveler2) {
       
        this.travelerName1 = traveler1;
        this.travelerName2 = traveler2;
    }

    public char getTravelerName1() {
        return travelerName1;
    }

    public char getTravelerName2() {
        return travelerName2;
    }
}
