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
import models.Room;
import utils.RandomString;
import utils.StreamData;
import views.ServerView;

public class WaitingForGameController {
    private ServerView view;
    private Player playerLogin;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;

    public WaitingForGameController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
    }
    public void handleFindingRoom() {
      if (ServerController.rooms.isEmpty()) {
          createWaitingRoom();
          return;
      }

      for (Room room : ServerController.rooms) {
          if (room.getPlayers().size() == 1) {
              joinWaitingRoom(room.getId());
              return;
          }
      }

      createWaitingRoom();
  }
    public void createWaitingRoom() {
        RandomString randomString = new RandomString(9, new SecureRandom(), RandomString.DIGITS);
        String randomId = randomString.nextString();
        List<Player> playersInRoom = new ArrayList<>();
        playersInRoom.add(this.socketHandlers.getLoginController().getPlayerLogin());
        Room room = new Room(randomId, new Date(), this.socketHandlers.getLoginController().getPlayerLogin(), playersInRoom, "1/2");
        this.socketHandlers.getLoginController().getPlayerLogin().setStatus("Đang tìm trận");
        System.out.println("Nguoi dung " + this.socketHandlers.getLoginController().getPlayerLogin().getPlayerName() + " dang tim tran");
        ServerController.rooms.add(room);
    }
    public void joinWaitingRoom(String roomId) {
        Room room = ServerController.rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().get();
        room.getPlayers().add(this.socketHandlers.getLoginController().getPlayerLogin());
        this.socketHandlers.getLoginController().getPlayerLogin().setStatus("Đang tìm trận");
        ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.JOIN_ROOM.name(), room);
        socketHandlers.send(objectWrapper);
        System.out.println("Sent join room " + StreamData.Message.JOIN_ROOM.name());
    }
}
