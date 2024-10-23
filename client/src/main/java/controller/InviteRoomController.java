package controller;

import java.awt.event.ActionEvent;

import views.InviteRoomForm;

public class InviteRoomController {
    private InviteRoomForm inviteRoomForm;
    
    public InviteRoomController(InviteRoomForm inviteRoomForm){
        this.inviteRoomForm = inviteRoomForm;
        // this.inviteRoomForm.addActionListener(new InviteRoomListener());
    }

    // class InviteRoomListener implements ActionListener{
    //     @Override
    //     public void actionPerformed(ActionEvent e){
    //         if(e.getSource() == homeForm.getFriendButton()){
    //             ClientController.closeFrame(ClientController.FrameName.HOME);
    //         }
    //     }
    // }
}
