package controller;

import models.*;
import utils.StatusPlayer;
import utils.StreamData;
import views.ListPlayerForm;
import views.SignUpForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListPlayerController {
    private ListPlayerForm listPlayerForm;

    public ListPlayerForm getListPlayerForm() {
        return listPlayerForm;
    }

    public ListPlayerController(ListPlayerForm listPlayerForm){
        this.listPlayerForm = listPlayerForm;
        this.listPlayerForm.addActionListener(new ListPlayerController.ListPlayerListener());
        updatePlayerList(ClientController.players);
    }
    public ListPlayerController(){
    }
    class ListPlayerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Search button action
            if(e.getSource()==listPlayerForm.getSearchButton()){
                String searchText = listPlayerForm.getSearchField().getText().toLowerCase();
                List<PlayerStatus> filteredPlayers = new ArrayList<>();
                for (PlayerStatus player : ClientController.players) {
                    if (player.getName().toLowerCase().contains(searchText)) {
                        filteredPlayers.add(player);
                    }
                }
                updatePlayerList(filteredPlayers);
            }

            // Make friend button action
            if(e.getSource() == listPlayerForm.getMakeFriendButton()){
                int selectedRow = listPlayerForm.getPlayerTable().getSelectedRow();
                if (selectedRow != -1) {
                    String selectedPlayerName = (String) listPlayerForm.getTableModel().getValueAt(selectedRow, 0);
                    String selectedIsFriend = (String) listPlayerForm.getTableModel().getValueAt(selectedRow, 2);
                    String selectedStatus = (String) listPlayerForm.getTableModel().getValueAt(selectedRow, 2);
                    if(selectedIsFriend.equals("✔️")){
                        JOptionPane.showMessageDialog(listPlayerForm, "Đã là bạn bè với " + selectedPlayerName);
                        return;
                    }
                    if(selectedStatus.equals(StatusPlayer.IN_GAME.value)){
                        JOptionPane.showMessageDialog(listPlayerForm, selectedPlayerName + " đang trong trận");
                        return;
                    }
                    sendPlayerNameToServer(selectedPlayerName);
                    JOptionPane.showMessageDialog(listPlayerForm, "Lời mời kết bạn tới " + selectedPlayerName + " đã được gửi");
                }
            }

            // Back button action
            if(e.getSource()==listPlayerForm.getBackButton()){
                ClientController.closeFrame(ClientController.FrameName.LIST_PLAYER);
                ClientController.openFrame(ClientController.FrameName.HOME);
            }
        }
    }
    public void updatePlayerList(List<PlayerStatus> players) {
        this.listPlayerForm.getTableModel().setRowCount(0); // Clear existing rows
        for (PlayerStatus player : players) {
            String displayName = player.isFriend() ? "✔️" : "";
            this.listPlayerForm.getTableModel().addRow(new Object[]{player.getName(), player.getStatus(),displayName});
        }
    }

    public void sendPlayerNameToServer(String playerName) {
        try {
            ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.SEND_INVITE_FRIEND, playerName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Sending player name to server: " + playerName);
    }
    public void acceptFriend(ObjectWrapper objectWrapper){
        String msg = objectWrapper.getStatus();

        if(msg.equals("no"))
        {
            JOptionPane.showMessageDialog(listPlayerForm, objectWrapper.getObject() + " không chấp nhận lời mời kết bạn");
        }
        else if (msg.equals("yes")){
            try {
                ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.UPDATE_LIST_PLAYER, null);
                ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.UPDATE_LIST_FRIEND, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JOptionPane.showMessageDialog(listPlayerForm, objectWrapper.getObject() + " chấp nhận lời mời kết bạn");
        }
    }
    public void receiveInviteFriend(ObjectWrapper objectWrapper){
        Player player = (Player) objectWrapper.getObject();
        int check = JOptionPane.showConfirmDialog(listPlayerForm, player.getPlayerName() + " gửi lời mời kết bạn", "Lời mời kết bạn", JOptionPane.YES_NO_OPTION);
        if (check == 1) {
            FriendInvite friendInvite = new FriendInvite(player, null, false);
            try {
                ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.ACCEPT_INVITE_FRIEND, friendInvite);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (check == 0) {
            FriendInvite friendInvite = new FriendInvite(player, null, true);
            try {
                ClientController.getSocketHandler().getSendMessages().send(StreamData.Message.ACCEPT_INVITE_FRIEND, friendInvite);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
