package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

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
    private Map<String, Long> inviteButtonCooldowns = new HashMap<>();
    private static final long INVITE_COOLDOWN = 10000; 
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

    private static final Color STATUS_ONLINE = new Color(46, 204, 113);      // Xanh lá
    private static final Color STATUS_OFFLINE = new Color(189, 195, 199);    // Xám
    private static final Color STATUS_IN_ROOM = new Color(241, 196, 15);     // Vàng
    private static final Color STATUS_IN_GAME = new Color(231, 76, 60);      // Đỏ
    private static final Color STATUS_FINDING = new Color(52, 152, 219);     // Xanh dương

    private JButton inviteButton;
    private JPanel friendPanel;

    public InviteRoomForm(Room room) {
        this.currentRoom = room;
        initComponents();
        setupLayout();
        setFrameProperties();
    }
    public void updateInviteRoom(Room room) {
        SwingUtilities.invokeLater(() -> {
            try {
                this.currentRoom = room;
                
                // Cập nhật thông tin phòng
                if (currentRoom != null) {
                    // Cập nhật thông tin người chơi 1
                    if (!currentRoom.getPlayers().isEmpty()) {
                        updatePlayerPanel(player1Panel, currentRoom.getPlayers().get(0), "Người chơi 1", PLAYER1_COLOR);
                    }
                    
                    // Cập nhật thông tin người chơi 2
                    if (currentRoom.getPlayers().size() > 1) {
                        updatePlayerPanel(player2Panel, currentRoom.getPlayers().get(1), "Người chơi 2", PLAYER2_COLOR);
                    } else {
                        // Nếu chưa có người chơi 2, hiển thị panel trống
                        player2Panel.removeAll();
                        player2Panel.add(createEmptyPlayerPanel());
                    }
                }
                
                // Cập nhật trạng thái các nút
                updateButtonStates();
                
                // Refresh UI
                revalidate();
                repaint();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

        panel.setLayout(new GridLayout(2, 1, 0, 2));
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
        friendPanel = new JPanel(new BorderLayout());
        friendPanel.setBackground(BACKGROUND_COLOR);
        friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        friendTitle = new JLabel("DANH SÁCH BẠN BÈ", JLabel.CENTER);
        friendTitle.setOpaque(true);
        friendTitle.setBackground(FRIEND_LIST_COLOR);
        friendTitle.setForeground(Color.WHITE);
        friendTitle.setFont(new Font("Arial", Font.BOLD, 14));
        friendTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
     
        String[] columnNames = {"Tên", "Trạng thái"};
        friendTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        friendTable = new JTable(friendTableModel);
        friendTable.setRowHeight(30);
        friendTable.setFont(new Font("Arial", Font.PLAIN, 14));
        friendTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Căn giữa tất cả các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        friendTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
        // Custom renderer cho cột status
        friendTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = value.toString();
                setHorizontalAlignment(SwingConstants.CENTER);
                
                switch (status) {
                    case "Online":
                        setForeground(new Color(46, 204, 113));
                        break;
                    case "In Room":
                        setForeground(new Color(52, 152, 219));
                        break;
                    case "Finding game":
                        setForeground(new Color(241, 196, 15));
                        break;
                    case "In Game":
                        setForeground(new Color(231, 76, 60));
                        break;
                    default:
                        setForeground(Color.BLACK);
                        break;
                }
                return cell;
            }
        });

        // Thêm JScrollPane
        JScrollPane scrollPane = new JScrollPane(friendTable);
        
        // Tạo nút mời
        JButton inviteButton = createStyledButton("Mời", new Color(52, 152, 219));
        inviteButton.setEnabled(false); // Mặc định disable
        
        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(inviteButton);
        
        // Thêm vào friendPanel
        friendListPanel.setLayout(new BorderLayout());
        friendListPanel.add(friendTitle, BorderLayout.NORTH);
        friendListPanel.add(scrollPane, BorderLayout.CENTER);
        friendListPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Cấu hình độ rộng cột
        TableColumnModel columnModel = friendTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Tên
        columnModel.getColumn(1).setPreferredWidth(100); // Trạng thái
        
        // Thêm selection listener
        friendTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = friendTable.getSelectedRow();
                if (selectedRow != -1) {
                    String status = friendTableModel.getValueAt(selectedRow, 1).toString();
                    inviteButton.setEnabled("Online".equals(status));
                } else {
                    inviteButton.setEnabled(false);
                }
            }
        });
        
        // Lưu nút mời để sử dụng trong controller
        this.inviteButton = inviteButton;
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
        SwingUtilities.invokeLater(() -> {
            try {
                friendTableModel.setRowCount(0);
                for (PlayerFriend friend : friends) {
                    friendTableModel.addRow(new Object[]{
                        friend.getPlayerName(),
                        friend.getStatus()
                    });
                }
                // Reset selection
                friendTable.clearSelection();
                inviteButton.setEnabled(false);
                
                // Refresh UI
                friendTable.revalidate();
                friendTable.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateButtonStates() {
        if (currentRoom == null) return;
        
        boolean isRoomFull = currentRoom.getPlayers().size() == 2;
        String loginPlayerName = ClientController.getSocketHandler().getReceiveMessages()
            .getLoginController().getPlayerLogin().getPlayerName();
        
        boolean isPlayer2 = currentRoom.getPlayers().size() > 1 && 
            currentRoom.getPlayers().get(1).getPlayerName().equals(loginPlayerName);
        
        startButton.setText(isPlayer2 ? "CHỜ CHỦ PHÒNG BẮT ĐẦU TRÒ CHƠI" : "BẮT ĐẦU TRÒ CHƠI");
        startButton.setEnabled(!isPlayer2 && isRoomFull);
        startButton.setBackground(isPlayer2 ? HEADER_ORANGE : 
            (isRoomFull ? new Color(52, 152, 219) : BUTTON_DISABLED));
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
          
          // Lưu thời điểm bấm nút
          inviteButtonCooldowns.put(button.getName(), System.currentTimeMillis());
          
          // Tạo timer để reset nút sau 10 giây
          Timer timer = new Timer(10000, e -> {
              button.setText("Mời");
              button.setEnabled(true);
              button.setBackground(ONLINE_INVITE_COLOR);
              inviteButtonCooldowns.remove(button.getName());
          });
          timer.setRepeats(false);
          timer.start();
      }
  }
  // Thêm phương thức để kiểm tra cooldown
private boolean isButtonOnCooldown(String playerName) {
  Long lastInviteTime = inviteButtonCooldowns.get(playerName);
  if (lastInviteTime == null) return false;
  
  long currentTime = System.currentTimeMillis();
  return (currentTime - lastInviteTime) < INVITE_COOLDOWN;
}
    public void addActionListener(ActionListener act) {
        // Xóa tất cả các listeners cũ trước khi thêm listener mới
        for (ActionListener al : startButton.getActionListeners()) {
            startButton.removeActionListener(al);
        }
        for (ActionListener al : leaveButton.getActionListeners()) {
            leaveButton.removeActionListener(al);
        }
        for (ActionListener al : inviteButton.getActionListeners()) {
            inviteButton.removeActionListener(al);
        }
        
        // Thêm listeners mới
        startButton.addActionListener(act);
        leaveButton.addActionListener(act);
        inviteButton.addActionListener(act);
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

  private JButton createStyledButton(String text, Color backgroundColor) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 20, 20);
            FontMetrics fm = g2.getFontMetrics();
            Rectangle stringBounds = fm.getStringBounds(this.getText(), g2).getBounds();
            int textX = (getWidth() - stringBounds.width) / 2;
            int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
            g2.setColor(getForeground());
            g2.drawString(getText(), textX, textY);
            g2.dispose();
        }
    };
    
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBackground(backgroundColor);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setPreferredSize(new Dimension(120, 40));

    // Thêm hiệu ứng hover
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(brighten(backgroundColor, 0.2f));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(backgroundColor);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            button.setBackground(darken(backgroundColor, 0.2f));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            button.setBackground(backgroundColor);
        }
    });

    return button;
}

// Thêm các phương thức hỗ trợ
private Color brighten(Color color, float fraction) {
    int red = Math.min(255, (int)(color.getRed() * (1 + fraction)));
    int green = Math.min(255, (int)(color.getGreen() * (1 + fraction)));
    int blue = Math.min(255, (int)(color.getBlue() * (1 + fraction)));
    return new Color(red, green, blue);
}

private Color darken(Color color, float fraction) {
    int red = Math.max(0, (int)(color.getRed() * (1 - fraction)));
    int green = Math.max(0, (int)(color.getGreen() * (1 - fraction)));
    int blue = Math.max(0, (int)(color.getBlue() * (1 - fraction)));
    return new Color(red, green, blue);
}
  //Create get and set method for button
public JButton getInviteButton() {
  return inviteButton;
}
}