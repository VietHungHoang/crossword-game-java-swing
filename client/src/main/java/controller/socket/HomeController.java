package controller.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.ClientController;
import views.HomeForm;

public class HomeController {
    private HomeForm homeForm;
    
    public HomeController(HomeForm homeForm){
        this.homeForm = homeForm;
        this.homeForm.addActionListener(new HomeListener());
    }

    class HomeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == homeForm.getFriendButton()){
                ClientController.closeFrame(ClientController.FrameName.HOME);
                ClientController.openFrame(ClientController.FrameName.INVITE_ROOM);
            }
        }
    }
}
