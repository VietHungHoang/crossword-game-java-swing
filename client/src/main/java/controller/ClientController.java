package controller;

import javax.swing.JOptionPane;

import controller.socket.HomeController;
import controller.socket.SocketHandlers;
import views.HomeForm;
import views.InviteRoomForm;
import views.LoginForm;
import views.SignUpForm;



public class ClientController {

    public static SocketHandlers socketHandlers;
    private static String severAddress = "localhost";
    private static int port = 12345;
    private static LoginForm loginForm;
    private static SignUpForm signUpForm;
    private static HomeForm homeForm;
    private static InviteRoomForm inviteRoomForm;

    private static LoginController loginController;
    private static HomeController homeController;
    private static InviteRoomController inviteRoomController;
    private static SignUpController signUpController;

    public enum FrameName{
        LOGIN, 
        SIGNUP,
        HOME, 
        INVITE_ROOM, 
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

            case SIGNUP:
            signUpForm.dispose();
            default:
                break;
        }
    }

    public static SocketHandlers getSocketHandler() {
        return socketHandlers;
    }

}
