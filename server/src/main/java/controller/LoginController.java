package controller;

import java.sql.Connection;

import dao.UserDAO;
import models.ObjectWrapper;
import models.User;
import utils.StreamData;
import views.ServerView;

public class LoginController {
    private ServerView view;
    private UserDAO userDAO;
    private SocketHandlers socketHandlers;

    public LoginController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.socketHandlers = socketHandlers;
    }
    public void checkLogin(User user){
        System.out.println("Received username=" + user.getUsername() + "?password=" + user.getPassword());
        User user2 = userDAO.findByUsername(user.getUsername());
        System.out.println(user2.getUsername());
        System.out.println(user2.getPassword());
        String message = StreamData.Message.LOGIN.name();
        message += (user2 != null && user2.getPassword().equals(user.getPassword())) ? ";success" : ";failed";
        
        ObjectWrapper objectWrapper = new ObjectWrapper(message, null);
        socketHandlers.send(objectWrapper);
        System.out.println("Sent login " + message);
    };
    

}
