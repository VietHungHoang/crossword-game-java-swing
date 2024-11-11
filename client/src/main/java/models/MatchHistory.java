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

    public void setStatus(String status) {
        this.status = status;
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



    public static List<MatchHistory> generateMockData() {
        List<MatchHistory> matchHistories = new ArrayList<>();

        // Sample data creation
        MatchHistory match1 = new MatchHistory();
        match1.setOpponent("Player A");
        match1.setStartDate(new Date(2024-1900, 10, 1, 14, 0,1)); // 1st Nov 2024, 2:00 PM
        match1.setEndDate(new Date(2024-1900, 10, 1, 15, 0,1));   // 1st Nov 2024, 3:00 PM
        match1.setType("Ranked Match");
        match1.setStatus("WIN");

        MatchHistory match2 = new MatchHistory();
        match2.setOpponent("Player B");
        match2.setStartDate(new Date(2024-1900, 10, 2, 16, 30)); // 2nd Nov 2024, 4:30 PM
        match2.setEndDate(new Date(2024-1900, 10, 2, 17, 30));   // 2nd Nov 2024, 5:30 PM
        match2.setType("Friend Match");
        match2.setStatus("LOST");

        matchHistories.add(match1);
        matchHistories.add(match2);

        return matchHistories;
    }
}
