package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import models.ObjectWrapper;
import models.Player;
import models.User;
import utils.StreamData;
import views.LoginForm;

public class LoginController {
    private LoginForm loginForm;
    public Player playerLogin = null;


    public LoginController(LoginForm loginForm) {
        this.loginForm = loginForm;
        this.loginForm.addActionListener(new LoginListener());
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginForm.getBtnLogin()) {
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
            if (e.getSource() == loginForm.getBtnSwap()) {
                ClientController.closeFrame(ClientController.FrameName.LOGIN);
                ClientController.openFrame(ClientController.FrameName.SIGNUP);
            }
        }
    }

    public void loginHandler(ObjectWrapper objecetWrapper) {
        String msg = objecetWrapper.getStatus();
        if (msg.equals("success")) {
            // Player đang chơi trong hệ thống
            // JOptionPane.showMessageDialog(loginForm, "Đăng nhập thành công ", "Đăng nhập
            // thành công", JOptionPane.INFORMATION_MESSAGE);
            playerLogin = (Player) objecetWrapper.getObject();
            System.out.println(playerLogin.toString());
            try {
                ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.UPDATE_LIST_PLAYER, null);
                ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.UPDATE_LIST_FRIEND, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ClientController.closeFrame(ClientController.FrameName.LOGIN);
            ClientController.openFrame(ClientController.FrameName.HOME);
            ClientController.setCurrentPlayer(playerLogin);
            System.out.println("success");

        } else if(msg.equals("failed")){
            JOptionPane.showMessageDialog(loginForm, "Đăng nhập thất bại", "Đăng nhập không thành công",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("error");
        }
        else{
            JOptionPane.showMessageDialog(loginForm, "Tài khoản đang đăng nhập ở nơi khác", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void logOut() {
        this.playerLogin = null;
        try {
            ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.UPDATE_LIST_PLAYER, null);
            ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.UPDATE_LIST_FRIEND, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientController.closeFrame(ClientController.FrameName.HOME);
        ClientController.openFrame(ClientController.FrameName.LOGIN);
    }

    public Player getPlayerLogin() {
        return playerLogin;
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }


}
