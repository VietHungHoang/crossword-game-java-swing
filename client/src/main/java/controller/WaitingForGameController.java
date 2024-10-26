package controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.ObjectWrapper;
import views.WaitingForGameForm;
public class WaitingForGameController {
  private WaitingForGameForm waitingForGameForm;

  public WaitingForGameController(WaitingForGameForm waitingForGameForm) {
    this.waitingForGameForm = waitingForGameForm;
  }
  public WaitingForGameController(){}
  public WaitingForGameForm getWaitingForGameForm() {
    return waitingForGameForm;
  }
  public void setWaitingForGameForm(WaitingForGameForm waitingForGameForm) {
    this.waitingForGameForm = waitingForGameForm;
  }

  // Handle event
  class WaitingForGameListener implements ActionListener{
      //Handle event 
      public void actionPerformed(ActionEvent e){
        if(e.getSource() == waitingForGameForm.getBtnCancel()){
          System.out.println("Cancel");
          //TODO: Cancel  finding game
          
        }
      }

      // Handle Socket return
     
  }
  public void waitingForGameHandler(ObjectWrapper objectWrapper){
    String msg = objectWrapper.getStatus();
    if(msg.equals("success")){
      //TODO: Handle success
    }
  }
  public static void main(String[] args) {
    new WaitingForGameForm();
  }
}
