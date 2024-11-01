package controller;

import dao.PlayerDAO;
import dao.UserDAO;
import models.ObjectWrapper;
import models.PlayerRanking;
import utils.StreamData;
import views.ServerView;

import java.sql.Connection;
import java.util.List;

public class RankingController {
    private ServerView view;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;

    public RankingController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
    }
    public void ranking(){
       List<PlayerRanking> playerRankingList =  playerDAO.getRanking();
        ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.RANKING.name(), playerRankingList);
        socketHandlers.send(objectWrapper);
    }
}
