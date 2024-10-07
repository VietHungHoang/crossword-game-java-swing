package client;
//Lớp chính của client, xử lý kết nối với server

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private String serverAddress = "localhost";  // Địa chỉ server
    private int serverPort = 12345;  // Cổng server

    public void sendLoginRequest(String username, String password) {
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Gửi thông tin đăng nhập tới server
            out.println("LOGIN:" + username + ":" + password);

            // Nhận phản hồi từ server
            String response = in.readLine();
            System.out.println("Response from server: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
