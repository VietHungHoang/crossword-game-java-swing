package controller;

import java.sql.Connection;

import dao.PlayerDAO;
import dao.UserDAO;
import models.Game;
import models.ObjectWrapper;
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

    public void handleEndGame(Player player){
        Player reamainingPlayer = getRemainingPlayer(player);
        this.view.showMessage(player.getPlayerName() + "is win and " + reamainingPlayer.getPlayerName() + "is lost");
        socketHandlers.send(new ObjectWrapper("WIN_GAME", player));
        socketHandlers.send(new ObjectWrapper("LOST_GAME", reamainingPlayer));

    }

    public Player getRemainingPlayer(Player player){
        for(Game x : ServerController.games)
            if(x.getPlayer1().getId().equals(player.getId()))
                return x.getPlayer2();
            else if(x.getPlayer2().getId().equals(player.getId()))
                return x.getPlayer1();
        return null;
    }
}
