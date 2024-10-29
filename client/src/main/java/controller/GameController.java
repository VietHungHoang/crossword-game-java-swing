package controller;

import java.awt.event.ActionListener;

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
    gameForm.showMessage(msg);
  }

  public void addActionListener(ActionListener act){
    gameForm.addActionListener(act);
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
