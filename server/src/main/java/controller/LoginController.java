package controller;

import java.sql.Connection;
import java.util.List;

import dao.PlayerDAO;
import dao.UserDAO;
import models.ObjectWrapper;
import models.Player;
import models.User;
import utils.StreamData;
import views.ServerView;

public class LoginController {
    private ServerView view;
    private Player playerLogin;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;

    public LoginController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
    }

    public void checkLogin(User user){
        System.out.println("Received username=" + user.getUsername() + "?password=" + user.getPassword());
        User user2 = userDAO.findByUsername(user.getUsername());
        System.out.println(user2.getUsername());
        System.out.println(user2.getPassword());
        String message = StreamData.Message.LOGIN.name();
        message += (user2.getId()!=null && user2.getPassword().equals(user.getPassword())) ? ";success" : ";failed";
        Player player = playerDAO.findPlayerByUserId(user2.getId());
        player.setStatus("Online");
        ObjectWrapper objectWrapper;
        if(user2.getId()!=null){
            List<SocketHandlers> clients = ServerController.getSocketHandlers();
            for (SocketHandlers clientHandler : clients) {
                if (clientHandler.getLoginController()!= null && clientHandler.getLoginController().getPlayerLogin()!=null &&clientHandler.getLoginController().getPlayerLogin().getPlayerName().equalsIgnoreCase(player.getPlayerName())) {
                    objectWrapper = new ObjectWrapper(StreamData.Message.LOGIN.name() + ";" + "falied", null);
                    socketHandlers.send(objectWrapper);
                    return;
                }
            }
        }
        if(message.endsWith("success")){
          objectWrapper = new ObjectWrapper(message, player);
            playerLogin=player;
            System.out.println(playerLogin);
        }
        else {
             objectWrapper = new ObjectWrapper(message,null);
        }
        socketHandlers.send(objectWrapper);
        System.out.println("Sent login " + message);
        
    };
    public void logOut(){
        this.playerLogin = null;
        ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.LOGOUT.name(), null);
        socketHandlers.send(objectWrapper);
    }

    public Player getPlayerLogin() {
        return playerLogin;
    }
}
