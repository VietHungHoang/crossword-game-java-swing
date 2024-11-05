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

    public void handleEndGame(){
        // Lấy ra người chơi chiến thang va xu ly
        Player player = socketHandlers.getLoginController().getPlayerLogin();
        player.setTotalGame(player.getTotalGame() + 1);
        player.setTotalGameWon(player.getTotalGameWon() + 1);
        player.setTotalPoint(player.getTotalPoint() + 5);
        // Lay ra nguoi thua va xu ly
        Player reamainingPlayer = getRemainingPlayer(player);
        reamainingPlayer.setTotalGame(reamainingPlayer.getTotalGame() + 1);
        this.view.showMessage(player.getPlayerName() + "is win and " + reamainingPlayer.getPlayerName() + "is lost");

        // send to nguoi thang
        socketHandlers.send(new ObjectWrapper("WIN_GAME", player));

        // send to nguoi thua
        for(SocketHandlers x : ServerController.getSocketHandlers()){
            if(x.getLoginController().getPlayerLogin().getId().equals(reamainingPlayer.getId())){
                x.send(new ObjectWrapper("LOST_GAME", reamainingPlayer));
                return;
            }
        }
    }

    public Player getRemainingPlayer(Player player){
        for(Game x : ServerController.games)
            if(x.getPlayer1().getId().equals(player.getId()))
                return x.getPlayer2();
            else if(x.getPlayer2().getId().equals(player.getId()))
                return x.getPlayer1();
        return null;
    }

    public void handleDraw(){
        socketHandlers.getLoginController().getPlayerLogin();
        for(Game x : ServerController.games){
            if(x.)
        }
    }
}
