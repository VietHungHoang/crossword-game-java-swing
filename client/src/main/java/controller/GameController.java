package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import models.ObjectWrapper;
import models.Player;
import models.User;
import utils.StreamData;
import views.EndGameForm;
import views.GameForm;

public class GameController {
  private GameForm gameForm;
  private EndGameForm endGameForm;
  private Player player1;
  private Player player2;

  private Timer countDownTimer;

  private Integer timeLeft = 30;

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
                        gameForm.getCountdownTimer().stop();
                        for(JButton x : gameForm.getKeyboardButtons()){
                            x.setEnabled(false);
                        }
                        for(JButton x : gameForm.getListKeywordBtns()){
                            x.setEnabled(false);
                        }
                        ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.WIN_GAME, null);
                    } catch (Exception ex) {
                    }
                }
                else{

                    JOptionPane.showMessageDialog(gameForm, "Chưa đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

  public GameController(GameForm gameForm){
    this.gameForm = gameForm;
    this.gameForm.addActionListener(new SubmitAnswerListener());
    this.startCountDown();
  }

  public void startCountDown(){
      countDownTimer = new Timer(1000, new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
              if (timeLeft > 0) {
                  timeLeft--;
                  gameForm.getLblCountdown().setText(String.valueOf(timeLeft));
              } else {
                  countDownTimer.stop();
                  try{
                      ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.DRAW_GAME, null);
                  }
                  catch (Exception ex){

                  }
              }
          }
      });
      countDownTimer.start();
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

  public GameForm getGameForm() {
    return gameForm;
  }

  public void handleEndGame(String results){
    System.out.println("Xu ly end game");
    ClientController.closeFrame(ClientController.FrameName.GAME);
    if(results.equals("Win"))
        ClientController.openFrame(ClientController.FrameName.WIN_GAME);
    else
        ClientController.openFrame(ClientController.FrameName.LOST_GAME);

  }
  class BackHomeListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == endGameForm.getBackButton()){
          // endGameForm.dispose();
          // gameForm.dispose();
            // ClientController.closeFrame(ClientController.FrameName.GAME); // co lua bang nhau
      }
  }
}
}
