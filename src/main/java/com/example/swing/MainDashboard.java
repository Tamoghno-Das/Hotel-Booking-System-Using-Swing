package com.example.swing;

import com.example.service.ChatbotService;
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

         JTextField chatInput = new JTextField(30);
         JButton sendBtn = new JButton("Send");
         JTextArea chatArea = new JTextArea(10, 40);

         frame.add(new JLabel("Chatbot:"));
         frame.add(chatInput);
         frame.add(sendBtn);
         frame.add(chatArea);



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

         ChatbotService bot = new ChatbotService();

         sendBtn.addActionListener(e -> {

             String userText = chatInput.getText();

             // Ask AI
             String aiResponse = bot.askAI(userText);

             // Extract fields
             String action = bot.extract(aiResponse, "ACTION");
             String hotel = bot.extract(aiResponse, "HOTEL");
             String type = bot.extract(aiResponse, "TYPE");
             String daysStr = bot.extract(aiResponse, "DAYS");
             String roomStr = bot.extract(aiResponse, "ROOMID");

             String result = "";

             try {

                 switch (action.toUpperCase()) {

                     case "BOOK":
                         int days = Integer.parseInt(daysStr);
                         result = service.bookRoom(hotel, type, days);
                         break;

                     case "CANCEL":
                         int roomId = Integer.parseInt(roomStr);
                         result = service.cancelBooking(hotel, roomId);
                         break;

                     case "VIEW":
                         result = service.viewAvailableRooms(hotel);
                         break;

                     case "PRICE":
                         int d = Integer.parseInt(daysStr);
                         result = service.getPriceSuggestion(type, d, hotel);
                         break;

                     default:
                         result = "Sorry, I didn't understand.";
                 }

             } catch (Exception ex) {
                 result = ex.getMessage();
             }

             // Display chat
             chatArea.append("User: " + userText + "\n");
             chatArea.append("Bot: " + result + "\n\n");

             chatInput.setText("");
         });




     }
}
