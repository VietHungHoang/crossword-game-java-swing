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


    public void invitePlayer(String roomId, Player player) {
        Room room = ServerController.rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);
        if (room != null && !room.getPlayers().contains(player) && room.getPlayers().size() < 2 && player.getStatus().equals("Trực tuyến"))  {
            player.setStatus("Trong phòng");
            ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.INVITE_ROOM.name(), room);
            socketHandlers.send(objectWrapper);
            System.out.println("Sent invite room " + room + " to " + player.getPlayerName());
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


}
