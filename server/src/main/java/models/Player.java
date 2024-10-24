package models;

import java.io.Serializable;
import java.nio.DoubleBuffer;
import java.util.List;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long totalGame;
    private Long totalGameWon;
    private Double totalPoint;
    private String playerName;
    private String status;
    private List<PlayerFriend> listFriends;
    private User user;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(Long totalGame) {
        this.totalGame = totalGame;
    }

    public Long getTotalGameWon() {
        return totalGameWon;
    }

    public void setTotalGameWon(Long totalGameWon) {
        this.totalGameWon = totalGameWon;
    }

    public Double getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Double totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<PlayerFriend> getListFriends() {
        return listFriends;
    }

    public void setListFriends(List<PlayerFriend> listFriends) {
        this.listFriends = listFriends;
    }


    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
