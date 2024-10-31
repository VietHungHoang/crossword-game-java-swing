package views;

import models.PlayerRanking;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankingForm extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private JButton backButton;
    private List<PlayerRanking> playerRankings=new ArrayList<>();

    public List<PlayerRanking> getPlayerRankings() {
        return playerRankings;
    }

    public void setPlayerRankings(List<PlayerRanking> playerRankings) {
        this.playerRankings = playerRankings;
    }

    public RankingForm() {
        setTitle("Bảng Xếp Hạng");
        setSize(800, 500);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Define column names
        String[] columnNames = {"STT", "TÊN PLAYER", "TỈ LỆ THẮNG", "SỐ TRẬN THẮNG", "SỐ TRẬN ĐÃ ĐẤU", "ĐIỂM"};

        // Create a table model with column names
        model = new DefaultTableModel(columnNames, 0);

        int cnt = 1;
        // Populate the table model with data from playerRankings
        for (PlayerRanking player : playerRankings) {
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

        // Create a table
        table = new JTable(model);
        table.setAutoCreateRowSorter(true); // Enable sorting
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering

        // Center align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        backButton = new JButton("Quay lại");
        buttonPanel.add(backButton);

        // Add the scroll pane and button panel to the frame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new RankingForm();
    }

    public void addActionListener(ActionListener act) {
        backButton.addActionListener(act);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }
}
