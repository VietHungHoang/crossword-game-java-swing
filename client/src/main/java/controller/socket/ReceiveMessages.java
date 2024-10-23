package controller.socket;

import java.io.ObjectInputStream;

import controller.LoginController;
import models.ObjectWrapper;
import models.User;
import utils.StreamData;
import views.LoginForm;

public class ReceiveMessages extends Thread{
   private ObjectInputStream ois;
   private LoginController loginController;

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
                        this.loginController.loginHandler(objectWrapper.getStatus());
                        break;
                
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
