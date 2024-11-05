package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.ClientController;
import models.Player;
import models.PlayerFriend;
import models.Room;

public class InviteRoomForm extends JFrame {

    private JPanel player1Panel, player2Panel, buttonPanel, friendListPanel, roomInfoPanel;
    private JButton startButton, leaveButton;
    private JTable friendTable;
    private DefaultTableModel friendTableModel;
    private JLabel friendTitle;
    private Room currentRoom;

    // Enhanced color scheme
    private static final Color HEADER_DARK = new Color(52, 73, 94);
    private static final Color HEADER_PURPLE = new Color(155, 89, 182);
    private static final Color HEADER_ORANGE = new Color(230, 126, 34);
    private static final Color HEADER_GREEN = new Color(46, 204, 113);
    private static final Color PLAYER1_COLOR = new Color(26, 188, 156);
    private static final Color PLAYER2_COLOR = new Color(241, 196, 15);
    private static final Color EMPTY_PLAYER_COLOR = new Color(189, 195, 199);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color FRIEND_LIST_COLOR = new Color(52, 152, 219);
    private static final Color BUTTON_DISABLED = new Color(149, 165, 166);
    private static final Color ONLINE_INVITE_COLOR = new Color(41, 128, 185);
    public InviteRoomForm(Room room) {
        this.currentRoom = room;
        initComponents();
        setupLayout();
        setFrameProperties();
    }
    public void updateInviteRoom(Room room) {
      // Update room information
      currentRoom = room;

      ((JLabel) roomInfoPanel.getComponent(0)).setText("ID Phòng: " + room.getId());
      ((JLabel) roomInfoPanel.getComponent(1)).setText("Trạng thái: " + room.getStatus());
      ((JLabel) roomInfoPanel.getComponent(2)).setText(room.isRanking() ? "Xếp hạng" : "Chơi vui vẻ");
      ((JLabel) roomInfoPanel.getComponent(3)).setText("Tạo bởi: " + room.getCreateBy().getPlayerName());
      
      // Update Player 1 information
      updatePlayerPanel(player1Panel, room.getPlayers().get(0), "Người chơi 1", PLAYER1_COLOR);
  
      // Update Player 2 information or set to empty if no player
      if (room.getPlayers().size() > 1) {
          System.out.println("update nguoi thu 2");
          updatePlayerPanel(player2Panel, room.getPlayers().get(1), "Người chơi 2", PLAYER2_COLOR);
      } else {
        player2Panel.removeAll();
        player2Panel.setLayout(new GridLayout(2, 1, 0, 2));
        player2Panel.setBackground(BACKGROUND_COLOR);
        player2Panel.add(createEmptyPlayerPanel());
      }
  
      // Refresh the state of the start button and friend list
      updateButtonStates();
      revalidate();
      repaint();
  }
  
  private void updatePlayerPanel(JPanel panel, Player player, String title, Color color) {
    // Xóa tất cả components cũ
    panel.removeAll();
    
    // Tạo lại panel với thông tin mới
    JLabel titleLabel = new JLabel(title, JLabel.CENTER);
    titleLabel.setOpaque(true);
    titleLabel.setBackground(color);
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

    JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 2));
    infoPanel.setBackground(color);
    infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel nameLabel = new JLabel("Tên: " + player.getPlayerName(), JLabel.LEFT);
    JLabel pointsLabel = new JLabel("Điểm: " + player.getTotalPoint(), JLabel.LEFT);
    JLabel gamesLabel = new JLabel("Số trận thắng: " + player.getTotalGameWon(), JLabel.LEFT);

    nameLabel.setForeground(Color.WHITE);
    pointsLabel.setForeground(Color.WHITE);
    gamesLabel.setForeground(Color.WHITE);

    infoPanel.add(nameLabel);
    infoPanel.add(pointsLabel);
    infoPanel.add(gamesLabel);

    panel.setLayout(new GridLayout(2, 1, 0, 2));
    panel.add(titleLabel);
    panel.add(infoPanel);
}
  
    private void initComponents() {
        initPanels();
        initButtons();
        initFriendList();
        initRoomInfo();
    }

    private void initPanels() {
        roomInfoPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        roomInfoPanel.setBackground(BACKGROUND_COLOR);

        player1Panel = createPlayerPanel(currentRoom.getPlayers().get(0), "Người chơi 1", PLAYER1_COLOR);
        
        if (currentRoom.getPlayers().size() > 1) {
            player2Panel = createPlayerPanel(currentRoom.getPlayers().get(1), "Người chơi 2", PLAYER2_COLOR);
        } else {
            player2Panel = createEmptyPlayerPanel();
        }

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        friendListPanel = new JPanel(new BorderLayout(0, 10));
        friendListPanel.setPreferredSize(new Dimension(250, 500));
        friendListPanel.setBackground(BACKGROUND_COLOR);
        friendListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void initRoomInfo() {
        JLabel idLabel = createHeaderLabel("ID Phòng: " + currentRoom.getId(), HEADER_DARK);
        JLabel statusLabel = createHeaderLabel("Trạng thái: " + currentRoom.getStatus(), HEADER_PURPLE);
        JLabel typeLabel = createHeaderLabel(currentRoom.isRanking() ? "Xếp hạng" : "Chơi vui vẻ", HEADER_ORANGE);
        JLabel creatorLabel = createHeaderLabel("Tạo bởi: " + currentRoom.getCreateBy().getPlayerName(), HEADER_GREEN);
        
        roomInfoPanel.add(idLabel);
        roomInfoPanel.add(statusLabel);
        roomInfoPanel.add(typeLabel);
        roomInfoPanel.add(creatorLabel);
    }

    private JPanel createPlayerPanel(Player player, String title, Color color) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 2));
        panel.setBackground(BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(color);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 2));
        infoPanel.setBackground(color);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Tên: " + player.getPlayerName(), JLabel.LEFT);
        JLabel pointsLabel = new JLabel("Điểm: " + player.getTotalPoint(), JLabel.LEFT);
        JLabel gamesLabel = new JLabel("Số trận thắng: " + player.getTotalGameWon(), JLabel.LEFT);

        nameLabel.setForeground(Color.WHITE);
        pointsLabel.setForeground(Color.WHITE);
        gamesLabel.setForeground(Color.WHITE);

        infoPanel.add(nameLabel);
        infoPanel.add(pointsLabel);
        infoPanel.add(gamesLabel);

        panel.add(titleLabel);
        panel.add(infoPanel);

        return panel;
    }

    private JPanel createEmptyPlayerPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 2));
        panel.setBackground(BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("Người chơi 2", JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(PLAYER2_COLOR);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel emptyLabel = new JLabel("Trống (Mời)", JLabel.CENTER);
        emptyLabel.setOpaque(true);
        emptyLabel.setBackground(EMPTY_PLAYER_COLOR);
        emptyLabel.setForeground(Color.WHITE);
        emptyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emptyLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panel.add(titleLabel);
        panel.add(emptyLabel);

        return panel;
    }

    private JLabel createHeaderLabel(String text, Color bgColor) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private void initButtons() {
        startButton = createButton("BẮT ĐẦU TRÒ CHƠI", new Color(52, 152, 219));
        leaveButton = createButton("RỜI PHÒNG", new Color(231, 76, 60));
        updateButtonStates();
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void initFriendList() {
        friendTitle = new JLabel("DANH SÁCH BẠN BÈ", JLabel.CENTER);
        friendTitle.setOpaque(true);
        friendTitle.setBackground(FRIEND_LIST_COLOR);
        friendTitle.setForeground(Color.WHITE);
        friendTitle.setFont(new Font("Arial", Font.BOLD, 14));
        friendTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        String[] columnNames = {"Tên", "Trạng thái", ""};
        friendTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only the button column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? JButton.class : Object.class;
            }
        };
        friendTable = new JTable(friendTableModel);
        friendTable.setRowHeight(30);
        friendTable.setFont(new Font("Arial", Font.PLAIN, 12));
        friendTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        friendTable.getTableHeader().setBackground(FRIEND_LIST_COLOR);
        friendTable.getTableHeader().setForeground(Color.WHITE);
        friendTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        friendTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        friendTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));

        friendTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = friendTable.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / friendTable.getRowHeight();

                if (row < friendTable.getRowCount() && row >= 0 && column == 2) {
                    Object value = friendTable.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                    }
                }
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout(5, 5));
        
        add(roomInfoPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(player1Panel);
        centerPanel.add(player2Panel);
        add(centerPanel, BorderLayout.CENTER);

        friendListPanel.add(friendTitle, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(friendTable);
        friendListPanel.add(scrollPane, BorderLayout.CENTER);
        add(friendListPanel, BorderLayout.EAST);

        buttonPanel.add(startButton);
        buttonPanel.add(leaveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setFrameProperties() {
        setTitle("Giao diện phòng mời");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setVisible(true);
    }

    public void updateFriendList(List<PlayerFriend> friends) {
      friendTableModel.setRowCount(0);
      for (PlayerFriend friend : friends) {
          JButton inviteButton = new JButton("Mời");
          inviteButton.setName(friend.getPlayerName());
          boolean isOnline = friend.getStatus().equalsIgnoreCase("Online");
          inviteButton.setEnabled(isOnline);
          inviteButton.setText(isOnline ? "Mời" : "");
          // Sử dụng màu đậm hơn cho nút mời khi online
          inviteButton.setBackground(isOnline ? ONLINE_INVITE_COLOR : BUTTON_DISABLED);
          inviteButton.setForeground(Color.WHITE);
          friendTableModel.addRow(new Object[]{friend.getPlayerName(), friend.getStatus(), inviteButton});
      }
      updateButtonStates();
  }

    // ... existing code ...

  private void updateButtonStates() {
    boolean isRoomFull = currentRoom.getStatus().equals("2/2");
    String loginPlayerName = ClientController.getSocketHandler().getReceiveMessages().getLoginController().getPlayerLogin().getPlayerName();
    
    // Check if player 2 is the logged-in player
    boolean isPlayer2LoggedIn = currentRoom.getPlayers().size() > 1 && 
        currentRoom.getPlayers().get(1).getPlayerName().equals(loginPlayerName);
    
    if (isPlayer2LoggedIn) {
        startButton.setText("CHỜ CHỦ PHÒNG BẮT ĐẦU TRÒ CHƠI");
        startButton.setEnabled(false);
        startButton.setBackground(HEADER_ORANGE); // Using the orange color defined earlier
    } else {
        startButton.setText("BẮT ĐẦU TRÒ CHƠI");
        startButton.setEnabled(isRoomFull);
        startButton.setBackground(isRoomFull ? new Color(52, 152, 219) : BUTTON_DISABLED);
    }
    
    if (friendTable != null) {
        for (int i = 0; i < friendTable.getRowCount(); i++) {
            updateFriendButtonState(i);
        }
        }
    }

// ... existing code ...

    private void updateFriendButtonState(int row) {
      String status = (String) friendTable.getValueAt(row, 1);
      JButton inviteButton = (JButton) friendTable.getValueAt(row, 2);
      boolean isOnline = status.equalsIgnoreCase("Online");
      inviteButton.setEnabled(isOnline);
      inviteButton.setText(isOnline ? "Mời" : "");
      // Sử dụng màu đậm hơn cho nút mời khi online
      inviteButton.setBackground(isOnline ? ONLINE_INVITE_COLOR : BUTTON_DISABLED);
      inviteButton.setForeground(Color.WHITE);
  }

    public JTable getFriendTable() {
        return friendTable;
    }

    public JButton getInviteButtonAt(int row) {
        if (row >= 0 && row < friendTable.getRowCount()) {
            return (JButton) friendTable.getValueAt(row, 2);
        }
        return null;
    }

    private void handleInviteButtonClick(JButton button) {
      if (!button.getText().equals("ĐÃ MỜI")) {
          button.setText("ĐÃ MỜI");
          button.setEnabled(false);
          button.setBackground(BUTTON_DISABLED);
      }
  }

    public void addActionListener(ActionListener act) {
      for (ActionListener al : startButton.getActionListeners()) {
        startButton.removeActionListener(al);
      }
      for (ActionListener al : leaveButton.getActionListeners()) {
        leaveButton.removeActionListener(al);
      }
      startButton.addActionListener(act);
      leaveButton.addActionListener(act);
      // Chỉ thêm action listener cho các nút mời một lần
      for (int i = 0; i < friendTable.getRowCount(); i++) {
          JButton inviteButton = (JButton) friendTable.getValueAt(i, 2);
          if (inviteButton != null) {
              inviteButton.addActionListener(act);
          }
      }
  }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value;
            }
            return this;
        }
    }
    public JButton getStartButton() {
      return startButton;
    }
    public JButton getLeaveButton() {
      return leaveButton;
    }
    private class ButtonEditor extends DefaultCellEditor {
      private JButton button;
      private boolean isPushed;
  
      public ButtonEditor(JCheckBox checkBox) {
          super(checkBox);
          button = new JButton();
          button.setOpaque(true);
          // Đơn giản hóa xử lý sự kiện button
          button.addActionListener(e -> fireEditingStopped());
      }
  
      @Override
      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
          if (value instanceof JButton) {
              button = (JButton) value;
          }
          isPushed = true;
          return button;
      }
  
      @Override
      public Object getCellEditorValue() {
          isPushed = false;
          return button;
      }
  
      @Override
      public boolean stopCellEditing() {
          isPushed = false;
          return super.stopCellEditing();
      }
  }
}