package com.example.swing;

import com.example.service.HotelService;

import javax.swing.*;
import java.awt.*;


/**
 * Main Dashboard using Java Swing
 */
public class MainDashboard {

     static void main(String[] args) {

        HotelService service = new HotelService();

        JFrame frame = new JFrame("Hotel Booking System");
        frame.setSize(500, 400);
        frame.setLayout(new FlowLayout());

        JTextField hotelField = new JTextField(10);
        JTextField typeField = new JTextField(10);
        JTextField roomField = new JTextField(5);
        JTextField daysField = new JTextField(5);
        frame.add(new JLabel("Days:"));
        frame.add(daysField);


        JButton bookBtn = new JButton("Book Room");
        JButton cancelBtn = new JButton("Cancel Booking");
        JButton viewBtn = new JButton("View Rooms");
        JButton countBtn = new JButton("Count Available");
        JButton billBtn = new JButton("View Bill");
         JButton aiBtn = new JButton("AI Price");
         frame.add(aiBtn);
         frame.add(billBtn);


         JTextArea output = new JTextArea(10, 40);

        frame.add(new JLabel("Hotel Name:"));
        frame.add(hotelField);

        frame.add(new JLabel("Room Type (AC/NON-AC):"));
        frame.add(typeField);

        frame.add(new JLabel("Room ID:"));
        frame.add(roomField);

        frame.add(bookBtn);
        frame.add(cancelBtn);
        frame.add(viewBtn);
        frame.add(countBtn);

        frame.add(output);

        // Lambda Action: Book Room
        bookBtn.addActionListener(e -> {
            try {
                String result = service.bookRoom(
                        hotelField.getText(),
                        typeField.getText(),
                        Integer.parseInt(daysField.getText())
                );
                output.setText(result);
            } catch (Exception ex) {
                output.setText(ex.getMessage());
            }
        });

        // Lambda Action: Cancel Booking
        cancelBtn.addActionListener(e -> {
            String result = service.cancelBooking(
                    hotelField.getText(),
                    Integer.parseInt(roomField.getText())
            );
            output.setText(result);
        });

        // Lambda Action: View Rooms
        viewBtn.addActionListener(e -> {
            String result = service.viewAvailableRooms(
                    hotelField.getText()
            );
            output.setText(result);
        });

        // Lambda Action: Custom Operation
        countBtn.addActionListener(e -> {
            int count = service.countAvailable(
                    hotelField.getText()
            );
            output.setText("Available Rooms: " + count);
        });

        billBtn.addActionListener(e -> {
            String result = service.viewBill(
                    hotelField.getText(),
                    Integer.parseInt(roomField.getText())
            );
            output.setText(result);
        });

         aiBtn.addActionListener(e -> {
             String result = service.getPriceSuggestion(
                     typeField.getText(),
                     Integer.parseInt(daysField.getText()),
                     hotelField.getText()
             );
             output.setText(result);
         });


         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

     }
}
