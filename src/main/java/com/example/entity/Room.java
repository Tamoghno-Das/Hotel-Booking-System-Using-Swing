package com.example.entity;

/**
 * Room class updated with price and bill
 */
public class Room {

    public int roomId;
    public String type;
    public boolean isBooked;
    public double price;
    public Bill bill; // Store bill

    public Room(int roomId, String type) {
        this.roomId = roomId;
        this.type = type;
        this.isBooked = false;

        // Assign price
        this.price = type.equals("AC") ? 2000 : 1000;
    }
}
