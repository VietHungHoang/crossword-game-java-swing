package controller;

import models.*;
import utils.StreamData;
import views.ListPlayerForm;
import views.MatchHistoryForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MatchHistoryController {
    private MatchHistoryForm matchHistoryForm;

    public MatchHistoryForm getMatchHistoryForm() {
        return matchHistoryForm;
    }

    public MatchHistoryController(MatchHistoryForm matchHistoryForm){
        this.matchHistoryForm = matchHistoryForm;
        this.matchHistoryForm.addActionListener(new MatchHistoryListener());
    }
    public MatchHistoryController(){
    }
    class MatchHistoryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==matchHistoryForm.getBackButton()){
                ClientController.closeFrame(ClientController.FrameName.MATCH_HISTORY);
                ClientController.openFrame(ClientController.FrameName.HOME);
            }
        }
    }

    public void updateMatchHistory(List<MatchHistory> matchHistoryList) {
        this.matchHistoryForm.getModel().setRowCount(0); // Clear existing rows
        int cnt = 1;

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

        for (MatchHistory matchHistory : matchHistoryList) {
            // Format startDate and endDate
            String formattedStartDate = dateFormat.format(matchHistory.getStartDate());
            String formattedEndDate = dateFormat.format(matchHistory.getEndDate());

            // Add the formatted date to the table
            this.matchHistoryForm.getModel().addRow(new Object[]{
                    cnt,
                    matchHistory.getOpponent(),
                    formattedStartDate,
                    formattedEndDate,
                    matchHistory.getType(),
                    matchHistory.getStatus()
            });
            cnt++;
        }
    }
}
