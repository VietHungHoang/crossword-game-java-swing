package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controller.ClientController;
import models.ObjectWrapper;
import models.Player;
import utils.StreamData;
import views.LoginForm;
import models.User;

import javax.swing.*;

public class LoginController {
    private LoginForm loginForm;
    public LoginForm getLoginForm() {
        return loginForm;
    }
    public Player playerLogin=null;
    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }



    public LoginController(LoginForm loginForm){
        this.loginForm = loginForm;
        this.loginForm.addActionListener(new LoginListener());
    }
    public LoginController(){
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
            if (e.getSource() == loginForm.getBtnSwap()){
                ClientController.closeFrame(ClientController.FrameName.LOGIN);
                ClientController.openFrame(ClientController.FrameName.SIGNUP);
            }
        }
    }

    public void loginHandler(ObjectWrapper objecetWrapper){
        String msg = objecetWrapper.getStatus();
        if(msg.equals("success")){
            // Player đang chơi trong hệ thống
            JOptionPane.showMessageDialog(loginForm, "Đăng nhập thành công ", "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
            playerLogin = (Player) objecetWrapper.getObject();
            System.out.println(playerLogin.toString());
            ClientController.closeFrame(ClientController.FrameName.LOGIN);
            ClientController.openFrame(ClientController.FrameName.HOME);
            System.out.println("success");
        }
        else{
            JOptionPane.showMessageDialog(loginForm, "Đăng nhập thất bại", "Đăng nhập không thành công", JOptionPane.ERROR_MESSAGE);
            System.out.println("error");
        }
    }

    public void logOut() {
        this.playerLogin = null;
        ClientController.closeFrame( ClientController.FrameName.HOME);
        ClientController.openFrame( ClientController.FrameName.LOGIN);
    }

    public Player getPlayerLogin() {
        return playerLogin;
    }
}
