import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import controller.ServerController;
import views.ServerView;


public class ServerRun {
    public static void main(String[] args) {
        ServerView serverView = new ServerView();
        ServerController serverController = new ServerController(serverView);

    }
}
