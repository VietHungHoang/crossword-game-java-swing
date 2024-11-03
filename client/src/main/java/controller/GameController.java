package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import models.ObjectWrapper;
import models.Player;
import models.User;
import utils.StreamData;
import views.GameForm;

public class GameController {
  private GameForm gameForm;
  private Player player1;
  private Player player2;

class SubmitAnswerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JButton button = (JButton)e.getSource();
            boolean done = false;
            int cnt = 0;
            button.setEnabled(false);
            for (JButton x : gameForm.getListKeywordBtns())
                if (x.getText().equals("")) {
                    if(!done){
                        x.setText(button.getText());
                        done = true;
                    }
                    else cnt++;

                }
            if (cnt == 0) {
                StringBuilder sb = new StringBuilder();
                for(JButton x : gameForm.getListKeywordBtns()){
                    sb.append(x.getText());
                }
                if(sb.toString().equalsIgnoreCase(gameForm.getKeyword())){
                    try {
                        ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.WIN_GAME, player1);
                    } catch (Exception ex) {
                    }
                }
                else{
                    JOptionPane.showMessageDialog(gameForm, "Chưa đúng", "Đăng nhập không thành công", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

  public GameController(GameForm gameForm){
    this.gameForm = gameForm;
    this.gameForm.addActionListener(new SubmitAnswerListener());
  }

  public void showMessage(String msg){
    JOptionPane.showMessageDialog(gameForm, msg);
  }

  public void setPlayer1(Player player1){
    this.player1 = player1;
    gameForm.getLblPlayer1Name().setText(player1.getPlayerName());
  }
  public void setPlayer2(Player player2){
    this.player2 = player2;
    gameForm.getLblPlayer2Name().setText(player2.getPlayerName());
  }

  public void handleEndGame(String msg){
    if(msg.equals("Win"))
        ClientController.openFrame(ClientController.FrameName.WIN_GAME);
    else
        ClientController.openFrame(ClientController.FrameName.LOST_GAME);

  }

}
