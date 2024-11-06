package controller;

import java.security.SecureRandom;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.PlayerDAO;
import dao.UserDAO;
import models.ObjectWrapper;
import models.Player;
import models.PlayerFriend;
import models.PlayerStatus;
import models.Room;
import utils.RandomString;
import utils.StreamData;
import views.ServerView;

public class InviteRoomController {
  private ServerView view;
  private UserDAO userDAO;
  private PlayerDAO playerDAO;
  private SocketHandlers socketHandlers;

  public InviteRoomController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
    }

    public void createInviteRoom() {
        RandomString randomString = new RandomString(9, new SecureRandom(), RandomString.DIGITS);
        String randomId = randomString.nextString();
        List<Player> playersInRoom = new ArrayList<>();
        playersInRoom.add(this.socketHandlers.getLoginController().getPlayerLogin());
        Room room = new Room(randomId, new Date(), this.socketHandlers.getLoginController().getPlayerLogin(), playersInRoom, "1/2", false);
        this.socketHandlers.getLoginController().getPlayerLogin().setStatus("In Room");
        ServerController.rooms.add(room);
        System.out.println("Khoi tao room vui ve"+room);
        ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.INVITE_ROOM.name(), room);
        room.setStatus("1/2");
        socketHandlers.send(objectWrapper);
        getListFriend();
        this.socketHandlers.setRoomID(randomId);
        socketHandlers.getListPlayerController().updateListPlayer();
        socketHandlers.getInviteRoomController().updateListFriend();
        System.out.println(socketHandlers.getLoginController().getPlayerLogin().getPlayerName() + " da tao room" + randomId);
    }
    public void getListFriend() {
      List<PlayerFriend> listPlayer = playerDAO.getListFriend(socketHandlers.getLoginController().getPlayerLogin().getId());
      ArrayList<SocketHandlers> allSocketHandlers = ServerController.socketHandlers;
    
    // Cập nhật status cho từng người chơi trong danh sách bạn bè
    for (PlayerFriend friend : listPlayer) {
        boolean isOnline = false;
        Player playerOnline =null;
        for (SocketHandlers handler : allSocketHandlers) {
            if (handler.getLoginController() != null && 
                handler.getLoginController().getPlayerLogin() != null && 
                handler.getLoginController().getPlayerLogin().getPlayerName().equals(friend.getPlayerName())) {
                isOnline = true;
                playerOnline = handler.getLoginController().getPlayerLogin();
                break;
            }
        }
        friend.setStatus(isOnline ? playerOnline.getStatus() : "Offline");
    }

    ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.GET_LIST_FRIEND.name(), listPlayer);
    socketHandlers.send(objectWrapper);
    }

    public void updateListFriend(){
        List<SocketHandlers> allSocketHandlers = ServerController.socketHandlers;
        Map<String, String> listPlayerOnline = listPlayer()
                .stream()
                .collect(Collectors.toMap(PlayerStatus::getName, PlayerStatus::getStatus));
        for(SocketHandlers client : allSocketHandlers){
            if(client.getLoginController()!=null && client.getLoginController().getPlayerLogin()!=null){
                System.out.println("Ban be cua "+client.getLoginController().getPlayerLogin().getPlayerName()+": ");
                List<PlayerFriend> friendClient = playerDAO.getListFriend(client.getLoginController().getPlayerLogin().getId());
                for (PlayerFriend friend : friendClient) {
                    System.out.println(listPlayerOnline.get(friend.getPlayerName()));
                    if(listPlayerOnline.get(friend.getPlayerName())!=null){
                        friend.setStatus(listPlayerOnline.get(friend.getPlayerName()));
                    }
                    else {
                        friend.setStatus("Offline");
                    }
                }
                ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.UPDATE_LIST_FRIEND.name(), friendClient);
                client.send(objectWrapper);
            }
        }
    }


    public void invitePlayer(String playerName) {
        SocketHandlers clientHandler = ServerController.socketHandlers.stream().filter(h -> h.getLoginController().getPlayerLogin().getPlayerName().equals(playerName)).findFirst().orElse(null);
        Player player =  clientHandler.getLoginController().getPlayerLogin();
        Room room = ServerController.rooms.stream().filter(r -> r.getId().equals(this.socketHandlers.getRoomID())).findFirst().orElse(null);
        if (room != null && !room.getPlayers().contains(player) && room.getPlayers().size() < 2 && player.getStatus().equals("Online") && !room.isRanking())  {
            ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.RECEIVE_INVITE_ROOM.name(), room);
            clientHandler.send(objectWrapper);
            System.out.println("Sent invite room " + room.getId() + " to " + player.getPlayerName());
        }
    }
    public void acceptInviteRoom(String roomId){
      Room roomInServer = ServerController.rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);
      this.socketHandlers.getLoginController().getPlayerLogin().setStatus("In Room");
      if (roomInServer != null && !roomInServer.isRanking() && roomInServer.getPlayers().size() < 2) {
          // Cập nhật trạng thái phòng
          ServerController.rooms.remove(roomInServer);
          roomInServer = new Room(roomInServer);
          roomInServer.setStatus("2/2");
          Player acceptingPlayer = this.socketHandlers.getLoginController().getPlayerLogin();

          roomInServer.getPlayers().add(acceptingPlayer);
          
          ServerController.rooms.add(roomInServer);
          System.out.println(roomInServer);
          // Gửi ACCEPT_INVITE_ROOM với roomInServer đã được cập nhật
          // System.out.println("Dua nguoi choi" + acceptingPlayer.getPlayerName() + " vao room " + roomInServer.getId() + "co status " + roomInServer.getStatus() + " va co player " + roomInServer.getPlayers().get(0).getPlayerName() + " va " + roomInServer.getPlayers().get(1).getPlayerName());
          ObjectWrapper acceptMessage = new ObjectWrapper(StreamData.Message.ACCEPT_INVITE_ROOM.name(), roomInServer);
          socketHandlers.send(acceptMessage);
          getListFriend();
          this.socketHandlers.setRoomID(roomId);
          // Tìm người mời và gửi UPDATE_INVITE_ROOM
          Player invitingPlayer = roomInServer.getPlayers().stream()
              .filter(p -> !p.getPlayerName().equals(acceptingPlayer.getPlayerName()))
              .findFirst()
              .orElse(null);
              
          if (invitingPlayer != null) {
              // Gửi roomInServer đã cập nhật thay vì chỉ gửi invitingPlayer
              ObjectWrapper updateMessage = new ObjectWrapper(StreamData.Message.UPDATE_INVITE_ROOM.name(), roomInServer);
              for(SocketHandlers handler : ServerController.socketHandlers){
                  if(handler.getLoginController().getPlayerLogin().getPlayerName().equals(invitingPlayer.getPlayerName())){
                      handler.send(updateMessage);
                      break;
                  }
              }
          }
          socketHandlers.getListPlayerController().updateListPlayer();
          socketHandlers.getInviteRoomController().updateListFriend();
      }
  }
    public void readyForGame(String roomId, Player player) {
        Room room = ServerController.rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);
        if (room != null && room.getPlayers().contains(player) && room.getPlayers().size() == 2) {
            room.setStatus("Sẵn sàng chơi");
            ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.PLAYER_READY.name(), room);
            socketHandlers.send(objectWrapper);
            System.out.println("Sent player ready " + player.getPlayerName() + " to " + room.getId());
        }
    }

    public void cancelReadyForGame(String roomId, Player player) {
        Room room = ServerController.rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);
        if (room != null && room.getStatus().equals("Sẵn sàng chơi")) {
            room.setStatus("Chờ đối thủ");
            ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.CANCEL_READY.name(), room);
            socketHandlers.send(objectWrapper);
            System.out.println("Sent cancel ready " + player.getPlayerName() + " to " + room.getId());
        }
    }

    public void leaveInviteRoom() {
      // Check if roomID exists
      String currentRoomID = this.socketHandlers.getRoomID();
      if (currentRoomID == null) {
          System.out.println("Warning: Player trying to leave room but has no roomID");
          return;
      }
      socketHandlers.getLoginController().getPlayerLogin().setStatus("Online");
      socketHandlers.getListPlayerController().updateListPlayer();
        socketHandlers.getInviteRoomController().updateListFriend();
      // Lấy room từ ServerController.rooms
      Room room = ServerController.rooms.stream()
              .filter(r -> r.getId().equals(currentRoomID))
              .findFirst()
              .orElse(null);
      ServerController.rooms.remove(room);       
      if (room == null) {
          System.out.println("Warning: Room " + currentRoomID + " not found");
          this.socketHandlers.setRoomID(null);
          return;
      }
    
      System.out.println(room + " Truoc khi xoa on lai trong room");
      Room updatedRoom = new Room(room);
      // System.out.println(updatedRoom.getPlayers() + "Truoc khi xoa on lai trong room");
      updatedRoom.getPlayers().remove(this.socketHandlers.getLoginController().getPlayerLogin());
      updatedRoom.setStatus("1/2");
      ServerController.rooms.add(updatedRoom);
      this.socketHandlers.setRoomID(null);
      this.socketHandlers.getLoginController().getPlayerLogin().setStatus("Online");
      //Truyền ObjectWrapper xuống
      ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.LEAVE_INVITE_ROOM.name(), updatedRoom);
      socketHandlers.send(objectWrapper);
      System.out.println(updatedRoom+ " Con lai trong room");
      // System.out.println(updatedRoom.getPlayers().isEmpty() + " Room nay khong con ai");
      if (updatedRoom.getPlayers().isEmpty()) {
          ServerController.rooms.remove(updatedRoom);
      } else {
          // Thằng còn lại nhận đc UPDATE_ROOM
          ObjectWrapper objectWrapper1 = new ObjectWrapper(StreamData.Message.UPDATE_INVITE_ROOM.name(), updatedRoom);
          for (SocketHandlers handler : ServerController.socketHandlers) {
              if (handler.getLoginController().getPlayerLogin().getPlayerName()
                      .equals(updatedRoom.getPlayers().get(0).getPlayerName())) {
                  handler.send(objectWrapper1);
                  break;
              }
          }
      }
  }

    public List<PlayerStatus> listPlayer(){
        List<SocketHandlers> clients = ServerController.getSocketHandlers();
        List<PlayerStatus> playerStatusList = new ArrayList<>();
        for(SocketHandlers clientHandler:clients){
            Player player=null;
            if(clientHandler.getLoginController()!=null
                    && clientHandler.getLoginController().getPlayerLogin()!=null){
                player = clientHandler.getLoginController().getPlayerLogin();
                    PlayerStatus playerStatus = new PlayerStatus(player.getPlayerName(), false, player.getStatus());
                    playerStatusList.add(playerStatus);
            }
        }
        return playerStatusList;
    }

}
