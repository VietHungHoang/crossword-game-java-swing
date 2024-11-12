package controller;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dao.GameDAO;
import dao.JoinPlayingDAO;
import dao.KeywordDAO;
import dao.PlayerDAO;
import dao.UserDAO;
import dao.impl.IPlayerDAO;
import models.Game;
import models.JoinPlaying;
import models.Keyword;
import models.ObjectWrapper;
import models.Player;
import models.Room;
import utils.RandomString;
import utils.StatusPlayer;
import utils.StreamData;
import utils.TypeGame;
import views.ServerView;
public class GameController {
    private ServerView view;
    private Player playerLogin;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;
    private KeywordDAO keywordDAO;
    private GameDAO gameDAO;
    private JoinPlayingDAO joinPlaying;

    public GameController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
        this.keywordDAO = new KeywordDAO(conn);
        this.gameDAO = new GameDAO(conn);
        this.joinPlaying = new JoinPlayingDAO(conn);
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
        socketHandlers.getLoginController().getPlayerLogin().setStatus(StatusPlayer.ONLINE.value);
        player.setTotalGame(player.getTotalGame() + 1);
        player.setTotalGameWon(player.getTotalGameWon() + 1);

        // Lay ra nguoi thua va xu ly
        Player reamainingPlayer = getRemainingPlayer(player);
        reamainingPlayer.setTotalGame(reamainingPlayer.getTotalGame() + 1);

        int i = 0;

        for(i = 0; i < ServerController.games.size(); i++) {
            if (ServerController.games.get(i).getPlayer1().getId().equals(player.getId()) ||
                    ServerController.games.get(i).getPlayer2().getId().equals(player.getId())
            ) {
                break;
            }
        }
        Game gamew = ServerController.games.get(i);


        player.setTotalPoint((gamew.getType().equals(TypeGame.RANKED_MATCH.value)) ? player.getTotalPoint() + 5 : player.getTotalPoint());
        playerDAO.updatePlayer(player);

        System.out.println("Update remainning player");
        playerDAO.updatePlayer(reamainingPlayer);
        this.view.showMessage(player.getPlayerName() + " is win and " + reamainingPlayer.getPlayerName() + " is lost");

        gamew.setWinner(player.getId());
        gameDAO.insert(gamew);
        Long gameId = this.gameDAO.getLatestGameId();
        System.out.println("Insert Game ID 2: "+ gameId);
        this.joinPlaying.insert(gameId, player.getId());
        this.joinPlaying.insert(gameId, reamainingPlayer.getId());

        ServerController.rooms.remove(gamew.getRoom());
        ServerController.games.remove(i);

        // send to nguoi thang
        socketHandlers.send(new ObjectWrapper("WIN_GAME", player));

        // send to nguoi thua
        for(SocketHandlers x : ServerController.getSocketHandlers()){
            if(x.getLoginController().getPlayerLogin().getId().equals(reamainingPlayer.getId())){
                x.getLoginController().getPlayerLogin().setStatus(StatusPlayer.ONLINE.value);
                x.send(new ObjectWrapper("LOST_GAME", reamainingPlayer));
                // update PLAYER_STAT
                socketHandlers.send(new ObjectWrapper(StreamData.Message.PLAYER_STAT.name(), new Player(player)));
                x.send(new ObjectWrapper(StreamData.Message.PLAYER_STAT.name(), new Player(reamainingPlayer)));
                return;
            }
        }
        socketHandlers.getListPlayerController().updateListPlayer();
        socketHandlers.getInviteRoomController().updateListFriend();
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
        // set status player
        socketHandlers.getLoginController().getPlayerLogin().setStatus(StatusPlayer.ONLINE.value);
        Player remainingPlayer = null;
        currentPlayer.setTotalGame(currentPlayer.getTotalGame() + 1);
        Game t = null;
        for(Game x : ServerController.games){
             if(x.getPlayer1().getId() == currentPlayer.getId() && x.getWinner() == null){
                System.out.println(x.getWinner());
                t = x;
                 socketHandlers.send(new ObjectWrapper(StreamData.Message.DRAW_GAME.name(), null));
                 for(SocketHandlers y : ServerController.getSocketHandlers()){
                     if(y.getLoginController().getPlayerLogin().getId() == x.getPlayer2().getId()){
                        remainingPlayer = x.getPlayer2();
                        remainingPlayer.setTotalGame(remainingPlayer.getTotalGame() + 1);
                        // set status player
                        y.getLoginController().getPlayerLogin().setStatus(StatusPlayer.ONLINE.value);
                         y.send(new ObjectWrapper(StreamData.Message.DRAW_GAME.name(), null));
                         break;
                     }
                 }
             }
        }


        socketHandlers.getListPlayerController().updateListPlayer();
        socketHandlers.getInviteRoomController().updateListFriend();
        if(t != null){
            t.setWinner(0L);
            gameDAO.insert(t);
            Long gameId = gameDAO.getLatestGameId();
            playerDAO.updatePlayer(currentPlayer);
            playerDAO.updatePlayer(remainingPlayer);
            System.out.println("Insert game Id: "+ gameId);
            joinPlaying.insert(gameId, currentPlayer.getId());
            joinPlaying.insert(gameId, remainingPlayer.getId());
        }
    }

    public void handleStartGameWithFriend(){
        Player currentPlayer = socketHandlers.getLoginController().getPlayerLogin();
        // set status player
        socketHandlers.getLoginController().getPlayerLogin().setStatus(StatusPlayer.IN_GAME.value);
        for(Room x : ServerController.rooms){
            if(x.getPlayers().get(0).getId() == currentPlayer.getId() || x.getPlayers().get(1).getId() == currentPlayer.getId()){
                    Game game = new Game(x);
                    Random random = new Random();
                    Long z = random.nextInt(2)*1L;
                    String key = new String(RandomString.generateRandomString());
                    game.setKeyword(new Keyword(1L, key));
                    System.out.println("Da tao phong cho ca 2 nguoi choi trong phong");
                    ServerController.games.add(game);
                    System.out.println("Da them phong vao danh sach game");
                    ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.START_GAME_FRIEND.name(), new Game(game));
                    System.out.println("Game object Wrapper" + game.toString());
                    for(SocketHandlers socketHandler : ServerController.socketHandlers ){
                        if(socketHandler.getLoginController().getPlayerLogin().equals(x.getPlayers().get(0)) || socketHandler.getLoginController().getPlayerLogin().equals(x.getPlayers().get(1))){
                            socketHandler.getLoginController().getPlayerLogin().setStatus(StatusPlayer.IN_GAME.value);
                            socketHandler.send(objectWrapper);
                        }
                    }
                    break;
                }
        }
        socketHandlers.getListPlayerController().updateListPlayer();
        socketHandlers.getInviteRoomController().updateListFriend();
    }
}
