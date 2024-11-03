package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import models.Game;
import models.PlayerStatus;
import models.Room;
import views.ServerView;

public class ServerController {
    private static ServerView view;
    private static final int port = 12345;
    private static final String hostName = "127.0.0.1";
    // private static final String hostName = "10.21.233.77";
    private static final String dbName = "game_db";
    private static final String username = "root";
    private static final String password = "123456";
    private static Connection conn;
    private static ServerSocket myServer; 
    public static ArrayList<SocketHandlers> socketHandlers;
    public static List<Room> rooms;
    public static List<Game> games;
    public ServerController(ServerView serverView){
        rooms = new ArrayList<>();
        games = new ArrayList<>();
        socketHandlers = new ArrayList<>();
        this.view = serverView;
        this.getDBConnection(this.dbName, this.username, this.password);
        this.openServer(port);
        view.showMessage("Server is running...");
        while(true){
            this.listening();
        }
    }

    private void getDBConnection(String dbName, String username, String password) {
        String dbUrl = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        try {
            this.conn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Connect db successfully");
        } catch (Exception e) {
            view.showMessage(e.toString());
        }
    }

    private void openServer(int port){
        try{
            this.myServer = new ServerSocket(port);
        } catch (IOException e){
            view.showMessage(e.toString());
        }
    }

    private void listening(){
        try {
            Socket socketClient = myServer.accept();
            SocketHandlers socketHandler = new SocketHandlers(socketClient, this.conn);
            this.socketHandlers.add(socketHandler);
            socketHandler.start();
            System.out.println("New client request received : " + socketClient);
            
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static ArrayList<SocketHandlers> getSocketHandlers() {
        return socketHandlers;
    }
}
