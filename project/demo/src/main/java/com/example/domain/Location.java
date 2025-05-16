package com.example.domain;

public class Location {
    private double latitude;
    private double longitude;
    private String address; // Optional, for manual entry

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(double latitude, double longitude, String address) {
        this(latitude, longitude);
        this.address = address;
    }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public String getAddress() { return address; }

    @Override
    public String toString() {
        return address != null ? address : "(" + latitude + ", " + longitude + ")";
    }
}
