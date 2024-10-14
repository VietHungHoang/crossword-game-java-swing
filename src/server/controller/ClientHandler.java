package server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Nhận thông tin đăng nhập từ client
            String message = in.readLine();
            System.out.println("Received: " + message);

            String[] data = message.split(":");
            String command = data[0];
            String username = data[1];
            String password = data[2];

            // Kiểm tra thông tin đăng nhập
            String response;
            if (command.equals("LOGIN") && username.equals("admin") && password.equals("12345")) {
                response = "Login successful!";
            } else {
                response = "Login failed!";
            }

            // Gửi phản hồi cho client
            out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
