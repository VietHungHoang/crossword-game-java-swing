package server.models;


import java.util.List;

public class Player {
    private Long id;
    private int totalGame;
    private int totalGameWon;
    private int totalPoint;
    private String playerName;
    private List<Player> listFriends;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(int totalGame) {
        this.totalGame = totalGame;
    }

    public int getTotalGameWon() {
        return totalGameWon;
    }

    public void setTotalGameWon(int totalGameWon) {
        this.totalGameWon = totalGameWon;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Player> getListFriends() {
        return listFriends;
    }

    public void setListFriends(List<Player> listFriends) {
        this.listFriends = listFriends;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player(Long id, int totalGame, int totalGameWon, int totalPoint, String playerName, List<Player> listFriends, User user) {
        this.id = id;
        this.totalGame = totalGame;
        this.totalGameWon = totalGameWon;
        this.totalPoint = totalPoint;
        this.playerName = playerName;
        this.listFriends = listFriends;
        this.user = user;
    }
}
