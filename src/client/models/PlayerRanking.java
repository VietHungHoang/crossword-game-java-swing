package client.models;

public class PlayerRanking {
    private Long id;
    private  String playerName;
    private  Double percentWin;
    private  Long totalPoint;

    public PlayerRanking(Long id, String playerName, Double percentWin, Long totalPoint) {
        this.id = id;
        this.playerName = playerName;
        this.percentWin = percentWin;
        this.totalPoint = totalPoint;
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

    public Long getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Long totalPoint) {
        this.totalPoint = totalPoint;
    }
}
