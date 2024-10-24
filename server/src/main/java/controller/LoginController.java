package controller;

import java.sql.Connection;

import dao.PlayerDAO;
import dao.UserDAO;
import models.ObjectWrapper;
import models.Player;
import models.User;
import utils.StreamData;
import views.ServerView;

public class LoginController {
    private ServerView view;
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
        message += (user2 != null && user2.getPassword().equals(user.getPassword())) ? ";success" : ";failed";
        if(user2!=null){
            playerDAO.setPlayerOnline(user2);
            System.out.println("UserName: "+ user2.getUsername()+ " Online");
        }
        Player player = playerDAO.findPlayerByUserId(user2.getId());
        ObjectWrapper objectWrapper;
        if(message.endsWith("success")){
          objectWrapper = new ObjectWrapper(message, player);
        }
        else {
             objectWrapper = new ObjectWrapper(message,null);
        }
        socketHandlers.send(objectWrapper);
        System.out.println("Sent login " + message);
    };
    

}
