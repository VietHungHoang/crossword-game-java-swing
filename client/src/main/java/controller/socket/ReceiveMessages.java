package controller.socket;

import java.io.ObjectInputStream;

import controller.LoginController;
import controller.SignUpController;
import models.ObjectWrapper;
import models.Player;
import utils.StreamData;
import views.LoginForm;
import views.SignUpForm;

public class ReceiveMessages extends Thread{
    private ObjectInputStream ois;
    private LoginController loginController;
    private SignUpController signUpController;
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
