package models;

import java.io.Serializable;

public class PlayerFriend implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private String status;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
