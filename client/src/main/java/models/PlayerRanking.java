package models;

import java.io.Serializable;

public class PlayerRanking implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private  String playerName;
    private  Double percentWin;
    private  Long totalWin;
    private  Long totalGame;
    private  Long totalPoint;

    public PlayerRanking( String playerName, Double percentWin, Long totalWin, Long totalGame, Long totalPoint) {
        this.playerName = playerName;
        this.percentWin = percentWin;
        this.totalWin = totalWin;
        this.totalGame = totalGame;
        this.totalPoint = totalPoint;
    }

    public Long getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(Long totalWin) {
        this.totalWin = totalWin;
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

    public Double getPercentWin() {
        return percentWin;
    }

    public void setPercentWin(Double percentWin) {
        this.percentWin = percentWin;
    }

    public Long getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(Long totalGame) {
        this.totalGame = totalGame;
    }

    public Long getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Long totalPoint) {
        this.totalPoint = totalPoint;
    }
}
