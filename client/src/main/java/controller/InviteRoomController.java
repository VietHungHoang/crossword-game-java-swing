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
      this.inviteRoomForm.updateFriendList(listFriend);
      this.inviteRoomForm.addActionListener(new InviteRoomListener());
    }

    public void updateInviteRoomHandler(Room room){
      this.inviteRoomForm.updateInviteRoom(room);
    }
    
   class InviteRoomListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Xử lý các button cố định trước
        if (e.getSource() == inviteRoomForm.getLeaveButton()) {
            System.out.println("Roi khoi phong");
            // Xử lý rời phòng
            // ClientController.closeFrame(ClientController.FrameName.INVITE_ROOM);
            // ClientController.openFrame(ClientController.FrameName.HOME);
            return;
        }
        
        if (e.getSource() == inviteRoomForm.getStartButton()) {
            System.out.println("Bat dau game");
            // Xử lý bắt đầu game
            try {
                ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.START_GAME, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }

        // Xử lý các invite button trong bảng
        JTable friendTable = inviteRoomForm.getFriendTable();
        JButton clickedButton = (JButton)e.getSource();
        
        for (int i = 0; i < friendTable.getRowCount(); i++) {
            JButton inviteBtn = inviteRoomForm.getInviteButtonAt(i);
            if (e.getSource() == inviteBtn) {
                String playerName = (String) friendTable.getValueAt(i, 0);
                System.out.println("Invite " + playerName + " to room");
                try {
                    ClientController.getSocketHandler().getSendMessages().send(
                        StreamData.Message.INVITE_FRIEND_TO_ROOM, 
                        playerName
                    );
                    // Optionally disable the button after clicking to prevent multiple invites
                    inviteBtn.setEnabled(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}



}
