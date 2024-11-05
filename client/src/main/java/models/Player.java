package models;

import java.io.Serializable;

public class Player implements Serializable {
    

    private Long id;
    private Long totalGame;
    private Long totalGameWon;
    private Integer totalPoint;
    private String playerName;
    private String status;
    private static final long serialVersionUID = 1L;
    //Constructor using playerName only
    public Player(String playerName, String status) {
        this.playerName = playerName;
        this.totalGame = 0L;
        this.totalGameWon = 0L;
        this.totalPoint = 0;
        this.status = status;
    }
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

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
          
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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


    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", totalGame=" + totalGame +
                ", totalGameWon=" + totalGameWon +
                ", totalPoint=" + totalPoint +
                ", playerName='" + playerName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
