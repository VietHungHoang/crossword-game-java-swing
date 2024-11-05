package controller.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controller.ClientController;
import utils.StreamData;
import views.HomeForm;

import javax.swing.*;

public class HomeController {
    private HomeForm homeForm;

    public HomeController(HomeForm homeForm) {
        this.homeForm = homeForm;
        this.homeForm.addActionListener(new HomeListener());
        homeForm.getUserLabel().setText("Xin chào, " + ClientController.getSocketHandler().getReceiveMessages().getLoginController().getPlayerLogin().getPlayerName());
        homeForm.getScoreLabel().setText("Điểm: " + ClientController.getSocketHandler().getReceiveMessages().getLoginController().getPlayerLogin().getTotalPoint());
    }

    class HomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == homeForm.getFriendButton()) {
                try {
                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.INVITE_ROOM, null);
                    ClientController.closeFrame(ClientController.FrameName.HOME);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (e.getSource() == homeForm.getLogoutButton()) {
                try {
                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.LOGOUT, null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (e.getSource() == homeForm.getRankingButton()) {
                try {
                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.RANKING, null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(e.getSource() == homeForm.getRankButton()){
                try {
                    ClientController.openFrame(ClientController.FrameName.WAITING_FOR_GAME);
                    ClientController.closeFrame(ClientController.FrameName.HOME);
                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.WAITING_FOR_GAME, null);
                } 
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (e.getSource() == homeForm.getCustomButton()) {
                try {
                    ClientController.closeFrame(ClientController.FrameName.HOME);
                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.LIST_PLAYER, null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}