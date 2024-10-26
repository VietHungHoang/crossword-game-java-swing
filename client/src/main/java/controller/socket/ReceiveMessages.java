package controller.socket;

import java.io.ObjectInputStream;

import controller.LoginController;
import controller.SignUpController;
import controller.WaitingForGameController;
import models.ObjectWrapper;
import utils.StreamData;
import views.LoginForm;
public class ReceiveMessages extends Thread{
    private ObjectInputStream ois;
    private LoginController loginController;
    private SignUpController signUpController;
    private WaitingForGameController waitingForGameController;
    public ReceiveMessages(ObjectInputStream ois) {
        this.ois = ois;
    }
    @Override
    public void run() {
        while (true) {
            try {
                ObjectWrapper objectWrapper = new ObjectWrapper();
                objectWrapper = (ObjectWrapper) ois.readObject();
                String received = objectWrapper.getIdentifier();
                StreamData.Message message = StreamData.getMessageFromData(received);
                System.out.println(message);   
                switch (message) {
                    case LOGIN:
                        this.loginController = new LoginController(new LoginForm());
                        this.loginController.loginHandler(objectWrapper);
                        break;
                    case SIGNUP:
                        this.signUpController= new SignUpController();
                        this.signUpController.signUpHandler(objectWrapper.getStatus());
                        break;
                    case LOGOUT:
                        this.loginController.logOut();
                        break;
                    case CREATE_ROOM:
                      //TODO :  Khoi tao Waiting room From roi dong Frame // 
                      // xu ly logic  obecject room
                        break;
                    case WAITING_FOR_GAME:
                        this.waitingForGameController = new WaitingForGameController();
                        this.waitingForGameController.waitingForGameHandler(objectWrapper);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
