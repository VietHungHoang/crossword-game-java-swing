package controller;

import views.EndGameForm;

public class EndGameController {
    private EndGameForm endGameForm;

    public EndGameController(EndGameForm endGameForm) {
        this.endGameForm = endGameForm;
    }

    public void handleEndGame(String msg){
        if(msg.equals("Win")){
//            ClientController.openFrame();
        }
    }
}
