package controller;

import javax.swing.JOptionPane;

import controller.socket.HomeController;
import controller.socket.SocketHandlers;
import views.GameForm;
import views.HomeForm;
import views.InviteRoomForm;
import views.LoginForm;
import views.SignUpForm;
import views.WaitingForGameForm;

public class ClientController {

    public static SocketHandlers socketHandlers;
    private static String severAddress = "localhost";
    private static int port = 12345;
    private static LoginForm loginForm;
    private static SignUpForm signUpForm;
    private static HomeForm homeForm;
    private static InviteRoomForm inviteRoomForm;
    private static WaitingForGameForm waitingForGameForm;
    private static GameForm gameForm;
    private static LoginController loginController;
    private static HomeController homeController;
    private static InviteRoomController inviteRoomController;
    private static SignUpController signUpController;
    private static WaitingForGameController waitingForGameController;
    private static GameController gameController;

    public enum FrameName{
        LOGIN, 
        SIGNUP,
        HOME, 
        INVITE_ROOM, 
        WAITING_FOR_GAME,
        GAME
    }

    public ClientController(){
        socketHandlers = new SocketHandlers();
        if(socketHandlers.connect(severAddress, port)){
            System.out.println("Connect server successfully!");
            openFrame(FrameName.LOGIN);
        } 
            
        else {
            System.out.println("Connect server error!");
            JOptionPane.showMessageDialog(null, "Connect server error!");
        }
    }

    public static void openFrame(FrameName frameName){
        switch (frameName) {
            case LOGIN:
                loginForm = new LoginForm();
                loginController = new LoginController(loginForm);
                loginForm.setVisible(true);
                break;
            case HOME:
                homeForm = new HomeForm();
                homeController = new HomeController(homeForm);
                homeForm.setVisible(true);
                break;
            case INVITE_ROOM:
                inviteRoomForm = new InviteRoomForm();
                inviteRoomController = new InviteRoomController(inviteRoomForm);
                inviteRoomForm.setVisible(true);
                break;
            case SIGNUP:
                signUpForm = new SignUpForm();
                signUpController = new SignUpController(signUpForm);
                signUpForm.setVisible(true);
                break;
            case WAITING_FOR_GAME:
                waitingForGameForm = new WaitingForGameForm();
                waitingForGameController = new WaitingForGameController(waitingForGameForm);
                waitingForGameForm.setVisible(true);
                break;
            case GAME:
                gameForm = new GameForm("Player 1", "Player 2", "Player 3", 10, 10);
                gameController = new GameController(gameForm);
                gameForm.setVisible(true);
                break;
            default:

                break;
        }
    }

    public static void closeFrame(FrameName frameName) {
        switch (frameName) {
            case LOGIN:
              loginForm.dispose();
              break;
        
            case HOME:
              homeForm.dispose();
              break;

            case INVITE_ROOM:
              inviteRoomForm.dispose();
              break;

            case SIGNUP:
              signUpForm.dispose();
              break;

            case WAITING_FOR_GAME:
              waitingForGameForm.dispose();
              break;

            case GAME:
              gameForm.dispose();
              break;
            default:
              break;
        }
    }

    public static SocketHandlers getSocketHandler() {
        return socketHandlers;
    }

}
