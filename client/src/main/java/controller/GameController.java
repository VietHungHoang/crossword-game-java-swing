package controller;

import javax.swing.JOptionPane;

import models.ObjectWrapper;
import models.Player;
import views.GameForm;

public class GameController {
  private GameForm gameForm;
  private Player player1;
  private Player player2;

  public GameController(GameForm gameForm){
    this.gameForm = gameForm;
  }

  public void showMessage(String msg){
    JOptionPane.showMessageDialog(gameForm, msg);
  }

  public void setPlayer1(Player player1){
    this.player1 = player1;
    gameForm.getLabelPlayer1().setText(player1.getPlayerName());
  }
  public void setPlayer2(Player player2){
    this.player2 = player2;
    gameForm.getLabelPlayer2().setText(player2.getPlayerName());
  }



  public void GameControllerhandler(ObjectWrapper objectWrapper){
    String msg = objectWrapper.getStatus();
    if(msg.equals("start-game")){
      System.out.println("Start game");
    }
  }
}
