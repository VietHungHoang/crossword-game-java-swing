package models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    private String opponent;
    private Date startDate;
    private Date endDate;
    private String type;
    private String status;


    public String getOpponent() {
        return opponent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public  void setStatusS(String winner){
        if(winner.equals("0".strip())){
            setStatus("DRAW");
        }
        else if(opponent.equals(winner)){
            setStatus("LOST");
        }
        else {
            setStatus("WIN");
        }
        System.out.println(winner);
    }

}
