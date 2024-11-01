package dao.impl;

import models.Player;
import models.PlayerFriend;
import models.PlayerRanking;
import models.User;

import java.util.List;

public interface IPlayerDAO {
    Player findPlayerByUserId(Long id);
    void insertPlayer(User user);
    void setPlayerOnline(User user);
    public List<PlayerFriend> getListFriend(Long id );
    public List<PlayerRanking > getRanking();
}
