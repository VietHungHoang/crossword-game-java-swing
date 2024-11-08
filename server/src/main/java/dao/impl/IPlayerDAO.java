package dao.impl;

import models.*;

import java.sql.Connection;
import java.util.List;

public interface IPlayerDAO {
    Player findPlayerByUserId(Long id);
    void insertPlayer(User user);
    public List<PlayerFriend> getListFriend(Long id );
    public List<PlayerRanking > getRanking();
    public void makeFriend(Long id, Long friendId);
    public boolean isFriend(Long id, Long friendId);
    public List<MatchHistory> getMatchHistory(Long id);
}
