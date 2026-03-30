package com.example.entity;

public class Hotel {
    public String hotelName;
    public Room[] rooms;

    public Hotel(String hotelName, int size) {
        this.hotelName = hotelName;
        this.rooms = new Room[size];
    }
}
