package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private static final int PORT = 12345;  // Cổng để lắng nghe kết nối

    public static void main(String[] args) {
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
