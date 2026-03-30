package com.example.entity;

/**
 * Bill class stores booking billing details
 */
public class Bill {

    int roomId;
    int days;
    double baseAmount;
    double gst;
    double totalAmount;

    public Bill(int roomId, int days, double baseAmount, double gst, double totalAmount) {
        this.roomId = roomId;
        this.days = days;
        this.baseAmount = baseAmount;
        this.gst = gst;
        this.totalAmount = totalAmount;
    }

    /**
     * Generate formatted bill
     */
    public String generateBill() {
        return "Room ID: " + roomId +
                "\nDays: " + days +
                "\nBase Amount: ₹" + baseAmount +
                "\nGST (18%): ₹" + gst +
                "\nTotal Amount: ₹" + totalAmount;
    }
}
