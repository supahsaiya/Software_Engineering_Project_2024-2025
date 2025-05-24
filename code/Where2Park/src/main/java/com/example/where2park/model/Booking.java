package com.example.where2park.model;

public class Booking {
    private String spot;
    private String date;

    public Booking(String spot, String date) {
        this.spot = spot;
        this.date = date;
    }

    public String getSpot() {
        return spot;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return spot + " - " + date;
    }
}
