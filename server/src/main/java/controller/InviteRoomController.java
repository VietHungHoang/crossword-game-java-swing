package controller;

import java.security.SecureRandom;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.PlayerDAO;
import dao.UserDAO;
import models.ObjectWrapper;
import models.Player;
import models.PlayerFriend;
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
        Room room = new Room(randomId, new Date(), this.socketHandlers.getLoginController().getPlayerLogin(), playersInRoom, "1/2", true);
        this.socketHandlers.getLoginController().getPlayerLogin().setStatus("Trong phòng");
        ServerController.rooms.add(room);
        System.out.println("Khoi tao room vui ve"+room);
        ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.INVITE_ROOM.name(), room);
        room.setStatus("1/2");
        socketHandlers.send(objectWrapper);
        getListFriend();
        socketHandlers.setRoomID(randomId);
        System.out.println(socketHandlers.getLoginController().getPlayerLogin().getPlayerName() + " da tao room" + randomId);
    }
    public void getListFriend() {
      List<PlayerFriend> listPlayer = playerDAO.getListFriend(socketHandlers.getLoginController().getPlayerLogin().getId());
    
    // Lấy danh sách tất cả các SocketHandler đang kết nối
    ArrayList<SocketHandlers> allSocketHandlers = ServerController.socketHandlers;
    
    // Cập nhật status cho từng người chơi trong danh sách bạn bè
    for (PlayerFriend friend : listPlayer) {
        boolean isOnline = false;
        for (SocketHandlers handler : allSocketHandlers) {
            if (handler.getLoginController() != null && 
                handler.getLoginController().getPlayerLogin() != null && 
                handler.getLoginController().getPlayerLogin().getPlayerName().equals(friend.getPlayerName())) {
                isOnline = true;
                break;
            }
        }
        friend.setStatus(isOnline ? "Online" : "Offline");
    }


    for(PlayerFriend player : listPlayer){
        System.out.println("List friend : " +player);
    }
    ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.GET_LIST_FRIEND.name(), listPlayer);
    socketHandlers.send(objectWrapper);
    }


    public void invitePlayer(String playerName) {
        SocketHandlers clientHandler = ServerController.socketHandlers.stream().filter(h -> h.getLoginController().getPlayerLogin().getPlayerName().equals(playerName)).findFirst().orElse(null);
        Player player =  clientHandler.getLoginController().getPlayerLogin();
        Room room = ServerController.rooms.stream().filter(r -> r.getId().equals(this.socketHandlers.getRoomID())).findFirst().orElse(null);
        if (room != null && !room.getPlayers().contains(player) && room.getPlayers().size() < 2 && player.getStatus().equals("Trực tuyến"))  {
            ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.RECEIVE_INVITE_ROOM.name(), room);
            clientHandler.send(objectWrapper);
            System.out.println("Sent invite room " + room.getId() + " to " + player.getPlayerName());
        }
    }
    public void acceptInviteRoom(String roomId){
      Room roomInServer = ServerController.rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);
      this.socketHandlers.getLoginController().getPlayerLogin().setStatus("Trong phòng");
      if (roomInServer != null) {
          // Cập nhật trạng thái phòng
          roomInServer = new Room(roomInServer);
          roomInServer.setStatus("2/2");
          Player acceptingPlayer = this.socketHandlers.getLoginController().getPlayerLogin();

          roomInServer.getPlayers().add(acceptingPlayer);
          

          System.out.println(roomInServer);
          // Gửi ACCEPT_INVITE_ROOM với roomInServer đã được cập nhật
          // System.out.println("Dua nguoi choi" + acceptingPlayer.getPlayerName() + " vao room " + roomInServer.getId() + "co status " + roomInServer.getStatus() + " va co player " + roomInServer.getPlayers().get(0).getPlayerName() + " va " + roomInServer.getPlayers().get(1).getPlayerName());
          ObjectWrapper acceptMessage = new ObjectWrapper(StreamData.Message.ACCEPT_INVITE_ROOM.name(), roomInServer);
          socketHandlers.send(acceptMessage);
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

    public void leaveInviteRoom(){
        Room room = ServerController.rooms.stream().filter(r -> r.getId().equals(this.socketHandlers.getRoomID())).findFirst().orElse(null);
        if (room != null) {
            room.getPlayers().remove(this.socketHandlers.getLoginController().getPlayerLogin());
        }
        this.socketHandlers.setRoomID(null);
        this.socketHandlers.getLoginController().getPlayerLogin().setStatus("Online");
        if(room.getPlayers().size() == 0){
            ServerController.rooms.remove(room);
        }
    }


}
