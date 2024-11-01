package views;

import controller.ClientController;
import models.PlayerStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ListPlayerForm extends JFrame {
    private JTextField searchField;
    private JTable playerTable;
    private DefaultTableModel tableModel;// List to store Player objects
    private JButton searchButton, makeFriendButton, backButton;

    public ListPlayerForm() {
        setTitle("Tìm kiếm bạn bè");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        searchField = new JTextField(20);
        String[] columnNames = {"Tên người chơi", "Trạng thái","Bạn bè"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        playerTable = new JTable(tableModel);
        playerTable.setDefaultRenderer(Object.class, new PlayerTableCellRenderer()); // Custom cell renderer
        searchButton = new JButton("Tìm kiếm");
        makeFriendButton = new JButton("Kết bạn");
        backButton = new JButton("Trở lại");


        // Panel setup
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(searchField, BorderLayout.NORTH);
        panel.add(new JScrollPane(playerTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(searchButton);
        buttonPanel.add(makeFriendButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }


    // Custom cell renderer to change text color based on player status
    // Custom cell renderer to center text and set colors based on player status
    private class PlayerTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            PlayerStatus player = ClientController.players.get(row);
            // Center the content in each cell
            setHorizontalAlignment(SwingConstants.CENTER);
            if (column == 1) { // Apply color only to the "Status" column
                if ("Trực tuyến".equals(player.getStatus())) {
                    cell.setForeground(Color.GREEN); // Green for "Online"
                } else if ("Đang trong phòng".equals(player.getStatus())) {
                    cell.setForeground(Color.GREEN); // Blue for "In Room"
                }else if ("Đang tìm trận".equals(player.getStatus())) {
                    cell.setForeground(Color.ORANGE); // Orange for "Find Match"
                }else if ("Trong trận".equals(player.getStatus())) {
                    cell.setForeground(Color.BLUE); // Red for "In Game"
                }
                else {
                    cell.setForeground(Color.BLACK); // Default color
                }
            } else {
                cell.setForeground(Color.BLACK); // Default color for other columns
            }
            return cell;
        }
    }

    public void addActionListener(ActionListener act) {
        makeFriendButton.addActionListener(act);
        backButton.addActionListener(act);
        searchButton.addActionListener(act);
    }
    public static void main(String[] args) {
        new ListPlayerForm();
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public JTable getPlayerTable() {
        return playerTable;
    }

    public void setPlayerTable(JTable playerTable) {
        this.playerTable = playerTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }


    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    public JButton getMakeFriendButton() {
        return makeFriendButton;
    }

    public void setMakeFriendButton(JButton makeFriendButton) {
        this.makeFriendButton = makeFriendButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }
}
