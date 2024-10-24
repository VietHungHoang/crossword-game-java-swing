package dao.impl;

import models.Player;
import models.User;

public interface IPlayerDAO {
    Player findPlayerByUserId(Long id);
    void insertPlayer(User user);
    void setPlayerOnline(User user);
}
