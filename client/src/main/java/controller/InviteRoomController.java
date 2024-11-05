package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;

import models.ObjectWrapper;
import models.PlayerFriend;
import utils.StreamData;
import views.InviteRoomForm;

public class InviteRoomController {
    private InviteRoomForm inviteRoomForm;
    

    public InviteRoomController(InviteRoomForm inviteRoomForm){
        this.inviteRoomForm = inviteRoomForm;
        this.inviteRoomForm.addActionListener(new InviteRoomListener());
    } 


    public void inviteRoomHandler(ObjectWrapper objectWrapper){
        
    }
    public void getListFriendHandler(ObjectWrapper objectWrapper){
      @SuppressWarnings("unchecked")
      List<PlayerFriend> listFriend = (List<PlayerFriend>) objectWrapper.getObject();
      JTable friendTable = inviteRoomForm.getFriendTable();
      
      // Tạo model mới với số rows bằng số lượng friend
      javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) friendTable.getModel();
      model.setRowCount(listFriend.size());  // Set số lượng rows
      
      // Set giá trị cho từng row
      for (int i = 0; i < listFriend.size(); i++) {
          PlayerFriend playerFriend = listFriend.get(i);
          friendTable.setValueAt(playerFriend.getPlayerName(), i, 0);
          friendTable.setValueAt(playerFriend.getStatus(), i, 1);  
      }
    }

   class InviteRoomListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Xử lý các button cố định trước
        if (e.getSource() == inviteRoomForm.getLeaveButton()) {
            // Xử lý rời phòng
            // ClientController.closeFrame(ClientController.FrameName.INVITE_ROOM);
            // ClientController.openFrame(ClientController.FrameName.HOME);
            return;
        }
        
        if (e.getSource() == inviteRoomForm.getStartButton()) {
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
        for (int i = 0; i < friendTable.getRowCount(); i++) {
            JButton inviteBtn = inviteRoomForm.getInviteButtonAt(i);
            if (e.getSource() == inviteBtn) {
                // Lấy thông tin người chơi từ bảng
                String playerName = (String) friendTable.getValueAt(i, 0);
                try {
                    // Gửi lời mời cho server
                    ClientController.getSocketHandler().getSendMessages().send(
                        StreamData.Message.INVITE_FRIEND_TO_ROOM, 
                        playerName
                    );
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}



}
