package controller;

import dao.PlayerDAO;
import dao.UserDAO;
import models.ObjectWrapper;
import models.User;
import utils.StreamData;
import views.ServerView;

import java.sql.Connection;
import java.sql.SQLException;

public class SignUpController {
    private ServerView view;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;

    public SignUpController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
    }

    public void signUp(User user){
        String message = StreamData.Message.SIGNUP.name();
        if(userDAO.findByUsername(user.getUsername())==null){
            userDAO.insert(user);
            User userRegister = userDAO.findByUsername(user.getUsername());
            playerDAO.insertPlayer(userRegister);
            message += ";success";
        }
        else {
            message +=";failed";
        }
        ObjectWrapper objectWrapper = new ObjectWrapper(message, null);
        socketHandlers.send(objectWrapper);
        System.out.println("Sent login " + message);
    };
}
