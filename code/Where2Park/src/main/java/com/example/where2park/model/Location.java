package com.example.where2park.model;

public class Location {
    private double latitude;
    private double longitude;
    private String address;

    public Location(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    // Getters
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getAddress() { return address; }

    // Optional: toString for printing
    @Override
    public String toString() {
        return address + " (" + latitude + ", " + longitude + ")";
    }
}
