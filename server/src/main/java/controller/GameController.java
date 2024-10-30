package controller;

import java.sql.Connection;

import dao.PlayerDAO;
import dao.UserDAO;
import models.Game;
import models.Player;
import models.Room;
import views.ServerView;
public class GameController {
    private ServerView view;
    private Player playerLogin;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;

    public GameController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
    }

    public Game getGameByRoom(Room room) {
      Integer id = Integer.parseInt(room.getId());
      return ServerController.games.stream()
              .filter(game -> game.getRoomId().equals(id))
              .findFirst()
              .orElse(null);
    }
    public void createAGame(Room room) {
        Game game = new Game(room);
        System.out.println("Da tao phong cho ca 2 nguoi choi trong phong");
        ServerController.games.add(game);
    }
    public void deleteAGame(Game game) {
        ServerController.games.remove(game);
    }


}
