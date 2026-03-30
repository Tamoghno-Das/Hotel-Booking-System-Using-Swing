package com.example.service;


import com.example.entity.Bill;
import com.example.entity.Hotel;
import com.example.entity.Room;
import com.example.exception.BookingException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Function;

/**
 * Service class for business logic
 */
public class HotelService {

    Hotel[] hotels = new Hotel[2];

    public HotelService() {

        // Initialize Hotels
        hotels[0] = new Hotel("Sea View", 5);
        hotels[1] = new Hotel("Mountain Stay", 5);

        // Initialize Rooms
        for (int i = 0; i < 5; i++) {
            hotels[0].rooms[i] = new Room(i + 1, i % 2 == 0 ? "AC" : "NON-AC");
            hotels[1].rooms[i] = new Room(i + 1, i % 2 == 0 ? "AC" : "NON-AC");
        }
    }

    /**
     * Book room using lambda filtering
     */
    public String bookRoom(String hotelName, String type, int days) throws BookingException {

        int available = countAvailable(hotelName);

// AI price prediction
        double aiPrice = getAIPrice(type, days, available);

        double base = aiPrice * days;

// Lambda GST
        Function<Double, Double> gstCalc =
                amt -> amt * 0.18;

        double gst = gstCalc.apply(base);
        double total = base + gst;

        for (Hotel hotel : hotels) {

            if (hotel.hotelName.equalsIgnoreCase(hotelName)) {

                for (Room room : hotel.rooms) {

                    if (room.type.equalsIgnoreCase(type) && !room.isBooked) {
                        room.isBooked = true;
                        room.bill = new Bill(room.roomId, days, base, gst, total);
                        return "Room " + room.roomId + " booked at ₹" + aiPrice + " per day. Total: ₹" + total;
                    }
                }
            }
        }
        throw new BookingException("No " + type + " rooms available in " + hotelName);
    }

    /**
     * Cancel booking (Smart check)
     */
    public String cancelBooking(String hotelName, int roomId) {

        for (Hotel hotel : hotels) {

            if (hotel.hotelName.equalsIgnoreCase(hotelName)) {

                for (Room room : hotel.rooms) {

                    if (room.roomId == roomId && room.isBooked) {
                        room.isBooked = false;
                        return "Booking Cancelled for Room " + roomId;
                    }
                }
            }
        }
        return "Invalid Cancellation!";
    }

    /**
     * View Available Rooms
     */
    public String viewAvailableRooms(String hotelName) {

        String result = "";

        for (Hotel hotel : hotels) {
            if (hotel.hotelName.equalsIgnoreCase(hotelName)) {

                for (Room room : hotel.rooms) {
                    if (!room.isBooked) {
                        result += "Room " + room.roomId + " (" + room.type + ")\n";
                    }
                }
            }
        }
        return result;
    }

    /**
     * Custom Operation: Count Available Rooms
     */
    public int countAvailable(String hotelName) {

        int count = 0;

        for (Hotel hotel : hotels) {
            if (hotel.hotelName.equalsIgnoreCase(hotelName)) {

                for (Room room : hotel.rooms) {
                    if (!room.isBooked) count++;
                }
            }
        }
        return count;
    }

    /**
     * View bill for a booked room
     */
    public String viewBill(String hotelName, int roomId) {

        for (Hotel hotel : hotels) {

            if (hotel.hotelName.equalsIgnoreCase(hotelName)) {

                for (Room room : hotel.rooms) {

                    if (room.roomId == roomId && room.bill != null) {
                        return room.bill.generateBill();
                    }
                }
            }
        }
        return "Bill Not Found!";
    }

    /**
     * AI Price Prediction using Ollama
     */
    double getAIPrice(String type, int days, int availableRooms) {

        double predictedPrice = 0;

        try {

            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            // Prompt for AI
            String prompt = "Predict hotel room price in INR. " +
                    "Room Type: " + type +
                    ", Days: " + days +
                    ", Available Rooms: " + availableRooms +
                    ". Only return a number.";

            String jsonInput = "{ \"model\": \"llama3\", \"prompt\": \"" + prompt + "\", \"stream\": false }";

            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String response = "";
            String line;

            while ((line = br.readLine()) != null) {
                response += line;
            }

            // Extract number from response
            String digits = response.replaceAll("[^0-9]", "");

            if (!digits.isEmpty()) {
                predictedPrice = Double.parseDouble(digits);
            } else {
                predictedPrice = type.equals("AC") ? 2000 : 1000;
            }

        } catch (Exception e) {
            predictedPrice = type.equals("AC") ? 2000 : 1000;
        }

        return predictedPrice;
    }

    /**
     * Custom Operation: Get AI Suggested Price without booking
     */
    public String getPriceSuggestion(String type, int days, String hotelName) {

        int available = countAvailable(hotelName);

        double price = getAIPrice(type, days, available);

        return "AI Suggested Price per day: ₹" + price;
    }



}

