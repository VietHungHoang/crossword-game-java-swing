package controller;

import models.PlayerRanking;
import views.RankingForm;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RankingController {
    private RankingForm rankingForm;

    public RankingForm getRankingForm() {
        return rankingForm;
    }

    public RankingController(RankingForm rankingForm) {
        this.rankingForm = rankingForm;
        this.rankingForm.addActionListener(new RankingListener());
    }
    public RankingController() {

    }

    class RankingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == rankingForm.getBackButton()) {
                ClientController.openFrame(ClientController.FrameName.HOME);
                ClientController.closeFrame(ClientController.FrameName.RANKING);
            }
        }
    }

    public void rankingHandler(Object obj) {
        rankingForm.setPlayerRankings((List<PlayerRanking>) obj);
        int cnt = 1;
        DefaultTableModel model = (DefaultTableModel) rankingForm.getTable().getModel();
        model.setRowCount(0);

        // Populate the table model with data from playerRankings
        for (PlayerRanking player : rankingForm.getPlayerRankings()) {
            model.addRow(new Object[]{
                    cnt,
                    player.getPlayerName(),
                    String.format("%.2f%%", player.getPercentWin()),
                    player.getTotalWin(),
                    player.getTotalGame(),
                    player.getTotalPoint()
            });
            cnt++;
        }
        ClientController.closeFrame(ClientController.FrameName.HOME);
    }
}
