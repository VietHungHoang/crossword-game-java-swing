package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;

import models.ObjectWrapper;
import models.Player;
import models.User;
import utils.StreamData;
import views.ServerView;

public class SocketHandlers extends Thread {
    private Socket socketClient;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Connection conn;
    private ServerView view;
    private LoginController loginController;
    private SignUpController signUpController;
    private WaitingForGameController waitingForGameController;
    private RoomController roomController;
    private String roomID;
    private boolean IsReadyForGame = false;
    private GameController gameController;
    public LoginController getLoginController() {
        return this.loginController;
    }
    public SocketHandlers(Socket socketClient,Connection conn) throws IOException {
        this.socketClient = socketClient;
        this.oos = new ObjectOutputStream(this.socketClient.getOutputStream());
        this.ois = new ObjectInputStream(this.socketClient.getInputStream());
        this.conn = conn;
    }

    @Override
public void run() {
    while (true) {
        try {
            ObjectWrapper objectWrapper = (ObjectWrapper) ois.readObject();
            String received = objectWrapper.getIdentifier();
            StreamData.Message message = StreamData.getMessageFromData(received);
            System.out.println("Nhận thông điệp từ client: " + message);

            switch (message) {
                case LOGIN:
                    this.loginController = new LoginController(view, conn, this);
                    this.loginController.checkLogin((User) objectWrapper.getObject());
                    break;
                case SIGNUP:
                    this.signUpController = new SignUpController(view, conn, this);
                    this.signUpController.signUp((User) objectWrapper.getObject());
                    break;
                case LOGOUT:
                    this.loginController.logOut();
                    break;
                case WAITING_FOR_GAME:
                    this.waitingForGameController = new WaitingForGameController(view, conn, this);
                    this.waitingForGameController.handleFindingRoom();
                    break;
                case PLAYER_READY:
                    System.out.println("Nhan ve Object Player Ready");
                    if (this.waitingForGameController != null) {
                        this.waitingForGameController.handlePlayerReady();
                    }
                    break;
                case CANCEL_WAITING:
                    if (this.waitingForGameController != null) {
                        this.waitingForGameController.handleCancelWaiting();
                    }
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


    public void send(ObjectWrapper objectWrapper) {
        try {
            this.oos.writeObject(objectWrapper);
            this.oos.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public void sendToPlayer(Player player, ObjectWrapper objectWrapper) {
    if (this.getLoginController().getPlayerLogin().equals(player)) {
        send(objectWrapper);
    }
}

}
