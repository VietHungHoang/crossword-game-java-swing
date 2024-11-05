package controller;

import controller.socket.SendMessages;
import utils.StreamData;
import views.EndGameForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGameController {
    private EndGameForm endGameForm;

    public EndGameController(EndGameForm endGameForm) {
        this.endGameForm = endGameForm;
        this.endGameForm.addActionListener(new BackHomeListener());
    }
    class BackHomeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == endGameForm.getBackButton()){
                ClientController.closeFrame(ClientController.FrameName.LOST_GAME); // co lua bang nhau
                ClientController.openFrame(ClientController.FrameName.HOME);
            }
//            else{
//                try {
//                    ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.INVITE_REPLAY,  null);
//                    JOptionPane.showMessageDialog();
//                }
//                catch (Exception ex){
//                    System.out.println(ex);
//                }
//            }
    }
}
}
