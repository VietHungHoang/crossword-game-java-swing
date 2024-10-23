package models;

import java.util.Date;
import java.util.List;

public class Game {
    private Long id;
    private Date endDate;
    private Date startDate;
    private String status;
    private String type;
    private Long winner;
    private List<GameRound> gameRoundList;

    public Game(Long id, Date endDate, Date startDate, String status, String type, Long winner) {
        this.id = id;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.type = type;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }
}
