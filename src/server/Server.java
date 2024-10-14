package server;


import server.controller.ClientHandler;
import server.dao.ConnectionUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class Server {

    private static final int PORT = 12345;  // Cổng để lắng nghe kết nối
    public static Connection connection;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Ket noi toi csdl
        connection = ConnectionUtils.getMyConnection();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
