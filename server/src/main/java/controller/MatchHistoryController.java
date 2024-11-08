package controller;

import dao.PlayerDAO;
import dao.UserDAO;
import models.MatchHistory;
import models.ObjectWrapper;
import models.PlayerRanking;
import utils.StreamData;
import views.ServerView;

import java.sql.Connection;
import java.util.List;

public class MatchHistoryController {
    private ServerView view;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private SocketHandlers socketHandlers;

    public MatchHistoryController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.userDAO = new UserDAO(conn);
        this.playerDAO = new PlayerDAO(conn);
        this.socketHandlers = socketHandlers;
    }
    public void matchHistory(){
        List<MatchHistory> matchHistories =  playerDAO.getMatchHistory(socketHandlers.getLoginController().getPlayerLogin().getId());
        System.out.println("Gửi lich sử đấu của "+ socketHandlers.getLoginController().getPlayerLogin().getPlayerName());
        ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.MATCH_HISTORY.name(), matchHistories);
        socketHandlers.send(objectWrapper);
    }
}
