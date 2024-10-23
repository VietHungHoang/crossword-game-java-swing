package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controller.ClientController;
import utils.StreamData;
import views.LoginForm;
import models.User;

public class LoginController {
    private LoginForm loginForm;
    
    public LoginController(LoginForm loginForm){
        this.loginForm = loginForm;
        this.loginForm.addActionListener(new LoginListener());
    }

    class LoginListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == loginForm.getBtnLogin()){
                String username = loginForm.getTxtUsername().getText();
                String password = new String(loginForm.getTxtPassword().getPassword());
                User user = new User(username, password);
                try {
                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.LOGIN, user);
                    System.out.println("Send username=" + username + "?password=" + password);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void loginHandler(String msg){
        if(msg.equals("success")){
            ClientController.closeFrame(ClientController.FrameName.LOGIN);
            ClientController.openFrame(ClientController.FrameName.HOME);
            System.out.println("success");
        }
        else{
            System.out.println("error");
        }
    }
    
}
