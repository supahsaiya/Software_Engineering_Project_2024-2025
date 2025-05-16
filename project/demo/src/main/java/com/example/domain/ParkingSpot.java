package com.example.domain;

public class ParkingSpot {
    private String id;
    private String name;
    private Location location;

    public ParkingSpot(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public Location getLocation() { return location; }

    @Override
    public String toString() {
        return name + " - " + location.toString();
    }
}
