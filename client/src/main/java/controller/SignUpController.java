package controller;

import models.ObjectWrapper;
import models.User;
import utils.StreamData;
import views.screen.SignUpForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SignUpController {
    private SignUpForm signUpForm;

    public SignUpForm getSignUpForm() {
        return signUpForm;
    }

    public SignUpController(SignUpForm signUpForm){
        this.signUpForm = signUpForm;
        this.signUpForm.addActionListener(new RegisterListener());
    }
    public SignUpController(){
    }
    class RegisterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signUpForm.getRegisterButton()) {
                String username = signUpForm.getTxtUsername().getText();
                String password = new String(signUpForm.getTxtPassword().getPassword());
                String confirmPassword = new String(signUpForm.getTxtConfirmPassword().getPassword());
                if(!password.equals(confirmPassword)){
                    JOptionPane.showMessageDialog(signUpForm, "Mật khẩu không trùng nhau", "Mật khẩu sai", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Password: "+ password);
                    System.out.println("Confirm password: "+ confirmPassword);
                    return;
                }
                User user = new User(username, password);
                try {
                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.SIGNUP, user);
                    System.out.println("Send username=" + username + "?password=" + password);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(e.getSource() == signUpForm.getSwapButton()){
                ClientController.closeFrame(ClientController.FrameName.SIGNUP);
                ClientController.openFrame(ClientController.FrameName.LOGIN);
            }
        }
    }
    public void signUpHandler(String msg){
        if (msg.equals("failed")) {
            JOptionPane.showMessageDialog(signUpForm, "Đăng kí thất bại", "Thất bại", JOptionPane.WARNING_MESSAGE);
            System.out.println("ERROR");
        } else if (msg.equals("success")) {
            JOptionPane.showMessageDialog(signUpForm, "Đăng ký thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            ClientController.closeFrame(ClientController.FrameName.SIGNUP);
            ClientController.openFrame(ClientController.FrameName.LOGIN);
            System.out.println("Sign up success");
        }
    }
}
