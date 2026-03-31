package com.example.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * AI Chatbot using Ollama
 */
public class ChatbotService {

    /**
     * Send user query to Ollama
     */
    public String askAI(String userInput) {

        String response = "";

        try {

            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String prompt =
                    "You are a hotel booking assistant.\n" +
                            "Convert user request into structured format:\n" +
                            "ACTION: BOOK / CANCEL / VIEW / PRICE\n" +
                            "HOTEL: name\nTYPE: AC/NON-AC\nDAYS: number\nROOMID: number\n\n" +
                            "User: " + userInput;

            String json = "{ \"model\": \"llama3\", \"prompt\": \"" + prompt + "\", \"stream\": false }";

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                response += line;
            }

        } catch (Exception e) {
            response = "AI Error!";
        }

        return response;
    }

    /**
     * Extract structured fields (simple parsing)
     */
    public String extract(String text, String key) {

        int i = text.indexOf(key);
        if (i == -1) return "";

        int end = text.indexOf("\n", i);
        if (end == -1) end = text.length();

        return text.substring(i + key.length() + 1, end).trim();
    }
}
