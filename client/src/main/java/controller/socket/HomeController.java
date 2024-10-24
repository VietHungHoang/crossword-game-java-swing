package controller.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controller.ClientController;
import controller.LoginController;
import utils.StreamData;
import views.HomeForm;

public class HomeController {
    private HomeForm homeForm;
    public HomeController(HomeForm homeForm){
        this.homeForm = homeForm;
        this.homeForm.addActionListener(new HomeListener());
        homeForm.getUserLabel().setText("Xin chào, " + ClientController.getSocketHandler().getReceiveMessages().getLoginController().getPlayerLogin().getPlayerName());
        homeForm.getScoreLabel().setText("Điểm: " + ClientController.getSocketHandler().getReceiveMessages().getLoginController().getPlayerLogin().getTotalPoint());
    }

    class HomeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == homeForm.getFriendButton()){
                ClientController.closeFrame(ClientController.FrameName.HOME);
                ClientController.openFrame(ClientController.FrameName.INVITE_ROOM);
            }
            if(e.getSource()== homeForm.getLogoutButton()){
                try {
                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.LOGOUT, null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
