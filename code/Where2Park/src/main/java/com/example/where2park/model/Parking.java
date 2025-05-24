package com.example.where2park.model;

public class Parking {
    private String name;
    private double lat;
    private double lon;
    private String address;
    private String tel;

    public Parking(String name, double lat, double lon, String address, String tel) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.tel = tel;
    }

    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public String getAddress() { return address; }
    public String getTel() { return tel; }
}
