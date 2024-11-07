package controller;

import java.sql.Connection;
import java.util.List;
import java.util.Random;

import dao.KeywordDAO;
import dao.PlayerDAO;
import dao.UserDAO;
import models.Game;
import models.ObjectWrapper;
import models.Player;
import models.Room;
import utils.StreamData;
import views.ServerView;
public class GameController {
    private ServerView view;
    private Player playerLogin;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;
    private KeywordDAO keywordDAO;

    public GameController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
        this.keywordDAO = new KeywordDAO(conn);
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
        Player currentPlayer = socketHandlers.getLoginController().getPlayerLogin();
        for(Game x : ServerController.games){
             if(x.getPlayer1().getId() == currentPlayer.getId()){
                 socketHandlers.send(new ObjectWrapper(StreamData.Message.DRAW_GAME.name(), null));
                 for(SocketHandlers y : ServerController.getSocketHandlers()){
                     if(y.getLoginController().getPlayerLogin().getId() == x.getPlayer2().getId()){
                         y.send(new ObjectWrapper(StreamData.Message.DRAW_GAME.name(), null));
                         break;
                     }
                 }
             }
        }
    }

    public void handleStartGameWithFriend(){
        Player currentPlayer = socketHandlers.getLoginController().getPlayerLogin();
        for(Room x : ServerController.rooms){
            if(x.getPlayers().get(0).getId() == currentPlayer.getId() || x.getPlayers().get(1).getId() == currentPlayer.getId()){
                    Game game = new Game(x);
                    Random random = new Random();
                    Long z = random.nextInt(this.keywordDAO.countAll())*1L;
                    game.setKeyword(this.keywordDAO.findById(z));
                    System.out.println("Da tao phong cho ca 2 nguoi choi trong phong");
                    ServerController.games.add(game);
                    System.out.println("Da them phong vao danh sach game");
                    ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.START_GAME_FRIEND.name(), game);
                    System.out.println("Game object Wrapper" + game.toString());
                    for(SocketHandlers socketHandler : ServerController.socketHandlers ){
                        if(socketHandler.getLoginController().getPlayerLogin().equals(x.getPlayers().get(0)) || socketHandler.getLoginController().getPlayerLogin().equals(x.getPlayers().get(1))){
                            socketHandler.send(objectWrapper);
                        }
                    }
                    break;
                }
        }
    }
}
