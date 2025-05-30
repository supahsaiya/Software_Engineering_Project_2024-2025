package com.example.where2park.model;


public class ParkingLot {
    private String name;
    private double lat;
    private double lon;
    private String address;
    private String tel;
    private int totalSpots;
    private int currentlyAvailable;

    public ParkingLot(String name, double lat, double lon, String address, String tel, int totalSpots, int currentlyAvailable ) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.tel = tel;
        this.totalSpots = totalSpots;
        this.currentlyAvailable = currentlyAvailable;
    }



    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public String getAddress() { return address; }
    public String getTel() { return tel; }
    public int getTotalSpots() { return totalSpots; }
    public int getCurrentlyAvailable() { return currentlyAvailable; }


    public void setCurrentlyAvailable(int currentlyAvailable) {
        this.currentlyAvailable = currentlyAvailable;
    }

    public void updateInfo() {

        System.out.println("updateInfo() called");
    }



}
