package models;

import java.io.Serializable;

public class PlayerFriend implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String playerName;
    private String status;

    public PlayerFriend(Long id, String playerName, String status) {
        this.id = id;
        this.playerName = playerName;
        this.status = status;
    }
    public PlayerFriend() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public String toString() {
      return "PlayerFriend{" +
              "id=" + id +
              ", playerName='" + playerName + '\'' +
              ", status='" + status + '\'' +
              '}';
  } 
}
