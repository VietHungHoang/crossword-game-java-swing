package controller;

import dao.PlayerDAO;
import dao.UserDAO;
import models.FriendInvite;
import models.ObjectWrapper;
import models.Player;
import models.PlayerStatus;
import utils.StreamData;
import views.ServerView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ListPlayerController {
    private ServerView view;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;

    public ListPlayerController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
    }
    public void acceptInviteFriend(ObjectWrapper objectWrapper){
        FriendInvite friendInvite = (FriendInvite) objectWrapper.getObject();
        String msg= StreamData.Message.ACCEPT_INVITE_FRIEND.name();
        msg +=(friendInvite.isStatus()) ? ";yes" : ";no";
        if(friendInvite.isStatus()){
            playerDAO.makeFriend(friendInvite.getPlayer().getId(),socketHandlers.getLoginController().getPlayerLogin().getId());
            updateListPlayer();
        }
        List<SocketHandlers> clients = ServerController.getSocketHandlers();
        for (SocketHandlers clientHandler : clients) {
            if (clientHandler.getLoginController().getPlayerLogin().getPlayerName().equals(friendInvite.getPlayer().getPlayerName())) {
                objectWrapper = new ObjectWrapper(msg,socketHandlers.getLoginController().getPlayerLogin().getPlayerName());
                clientHandler.send(objectWrapper);
                break;
            }
        }

    }
    public void inviteFriend(ObjectWrapper objectWrapper){
        List<SocketHandlers> clients = ServerController.getSocketHandlers();
        // tên player được mời kết bạn
        String playerName = (String) objectWrapper.getObject();
        for (SocketHandlers clientHandler : clients) {
            if (clientHandler.getLoginController()!=null &&
                    clientHandler.getLoginController().getPlayerLogin() !=null &&
                    clientHandler.getLoginController().getPlayerLogin().getPlayerName().equals(playerName)) {
                objectWrapper = new ObjectWrapper(StreamData.Message.SEND_INVITE_FRIEND.name(),socketHandlers.getLoginController().getPlayerLogin());
                clientHandler.send(objectWrapper);
                return;
            }
        }
    }
    public List<PlayerStatus> listPlayer(){
        List<SocketHandlers> clients = ServerController.getSocketHandlers();
        List<PlayerStatus> playerStatusList = new ArrayList<>();
        for(SocketHandlers clientHandler:clients){
            Player player=null;
            if(clientHandler.getLoginController()!=null
                    && clientHandler.getLoginController().getPlayerLogin()!=null
                    && socketHandlers.getLoginController()!=null
                    && socketHandlers.getLoginController().getPlayerLogin()!=null){
                player = clientHandler.getLoginController().getPlayerLogin();
                Player playerLogin = socketHandlers.getLoginController().getPlayerLogin();
                if(!playerLogin.getPlayerName().equals(player.getPlayerName())){
                    PlayerStatus playerStatus = new PlayerStatus(player.getPlayerName(), playerDAO.isFriend(player.getId(),playerLogin.getId()), player.getStatus());
                    playerStatusList.add(playerStatus);
                }
            }
        }
      return playerStatusList;
    }
    public void updateListPlayer(){
        List<SocketHandlers> clients = ServerController.getSocketHandlers();
        for(SocketHandlers clientHandler:clients){
            List< PlayerStatus> updateList= new ArrayList<>();
            for (SocketHandlers clientHandler2 : clients){
                if(!clientHandler2.equals(clientHandler)
                        && clientHandler.getLoginController()!=null
                        && clientHandler.getLoginController().getPlayerLogin()!=null
                        && clientHandler2.getLoginController()!=null
                        && clientHandler2.getLoginController().getPlayerLogin()!=null){
                    Player player = clientHandler.getLoginController().getPlayerLogin();
                    Player playerLogin = clientHandler2.getLoginController().getPlayerLogin();
                    if(!playerLogin.getPlayerName().equals(player.getPlayerName())){
                        PlayerStatus playerStatus = new PlayerStatus(playerLogin.getPlayerName(), playerDAO.isFriend(player.getId(),playerLogin.getId()), playerLogin.getStatus());
                        updateList.add(playerStatus);
                    }
                }
            }
            ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.UPDATE_LIST_PLAYER.name(),updateList);
            clientHandler.send(objectWrapper);
        }
    }


}
