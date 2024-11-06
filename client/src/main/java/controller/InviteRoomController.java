package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;

import models.PlayerFriend;
import models.Room;
import utils.StreamData;
import views.InviteRoomForm;

public class InviteRoomController {
    private InviteRoomForm inviteRoomForm;
    

    public InviteRoomController(InviteRoomForm inviteRoomForm){
        this.inviteRoomForm = inviteRoomForm;
        this.inviteRoomForm.addActionListener(new InviteRoomListener());
    } 
    
    public void getListFriendHandler(List<PlayerFriend> listFriend){
      try {
        this.inviteRoomForm.updateFriendList(listFriend);
      } catch (Exception e) {
        e.printStackTrace();
      }
      // this.inviteRoomForm.updateFriendList(listFriend);
      this.inviteRoomForm.addActionListener(new InviteRoomListener());
    }

    public void updateInviteRoomHandler(Room room){
      this.inviteRoomForm.updateInviteRoom(room);
    }
    public void leaveInviteRoomHandler(){
      this.inviteRoomForm.dispose();
      ClientController.openFrame(ClientController.FrameName.HOME);
    }
   class InviteRoomListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inviteRoomForm.getLeaveButton()) {
            try {
                ClientController.getSocketHandler().getSendMessages()
                    .send(StreamData.Message.LEAVE_INVITE_ROOM, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }
        
        if (e.getSource() == inviteRoomForm.getStartButton()) {
            try {
                ClientController.getSocketHandler().getSendMessages()
                    .send(StreamData.Message.START_GAME, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }

        if (e.getSource() == inviteRoomForm.getInviteButton()) {
            JTable friendTable = inviteRoomForm.getFriendTable();
            int selectedRow = friendTable.getSelectedRow();
            if (selectedRow != -1) {
                String playerName = (String) friendTable.getValueAt(selectedRow, 0);
                String status = (String) friendTable.getValueAt(selectedRow, 1);
                
                if (status.equals("Online")) {
                    try {
                        ClientController.getSocketHandler().getSendMessages()
                            .send(StreamData.Message.INVITE_FRIEND_TO_ROOM, playerName);
                        inviteRoomForm.getInviteButton().setEnabled(false);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}



}
