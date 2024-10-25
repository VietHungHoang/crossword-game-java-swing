package views.screen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import javax.swing.table.TableCellRenderer;

import models.User;
import utils.ImageHandler; 

public class WaitingForm extends JFrame {
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel controlPanel;
    
    private JButton newGameButton;
    private JButton exitRoomButton;
    private JLabel roomOwnerLabel;
    private JTable friendsTable;
    private DefaultTableModel friendsTableModel;
    private List<Friend> friendsList;

    public WaitingForm() {
        setTitle("Ứng dụng Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new GridLayout(2, 1));
        controlPanel = new JPanel();

        setupLeftPanel();
        setupControlPanel();

        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.EAST);

        add(mainPanel);

        // Khởi tạo danh sách bạn bè ảo
        initializeFriendsList();
    }

    private void setupLeftPanel() {
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Tạo người chơi mẫu
        User sampleUser = new User(1L, "duyne@gmail.com ", "123456", "0909090909", "user", "duyne");

        // Tạo hai khung cho người chơi
        JPanel playerPanel1 = createPlayerPanel(sampleUser);
        JPanel playerPanel2 = createPlayerPanel(null);

        leftPanel.add(playerPanel1);
        leftPanel.add(playerPanel2);
    }

    private JPanel createPlayerPanel(User user) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(350, 150));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.setBackground(new Color(240, 240, 255)); // Màu nền nhẹ

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        if (user != null) {
            JLabel nameLabel = new JLabel(user.getUsername());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
            nameLabel.setForeground(new Color(0, 0, 100));
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            panel.add(nameLabel, gbc);  // Thêm tên người chơi vào panel

            //Bên dưới lấy ra ảnh từ ./img/avatar1.png nhé
            //TODO FIX cứng sửa thành user.avatar sau khi cập nhật model User
            ImageIcon imageIcon = ImageHandler.createImageIcon("./img/avatar1.png");
            if (imageIcon != null) {
                Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridheight = 2;
                panel.add(imageLabel, gbc);
            }

            //TODO FIX cứng sửa thành player.score sau khi cập nhật model User
            JLabel scoreLabel = new JLabel("Score: 0");
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            scoreLabel.setForeground(new Color(100, 0, 0));
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(scoreLabel, gbc);
        } else {
            JLabel waitingLabel = new JLabel("Đang chờ người chơi...");
            waitingLabel.setFont(new Font("Arial", Font.ITALIC, 22));
            waitingLabel.setForeground(Color.GRAY);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            panel.add(waitingLabel, gbc);
        }

        return panel;
    }

   

    private void setupControlPanel() {
        controlPanel.setPreferredSize(new Dimension(250, getHeight()));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Phần chức năng
        JPanel functionPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        functionPanel.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        newGameButton = new JButton("Chơi mới");
        exitRoomButton = new JButton("Thoát phòng");
        newGameButton.setPreferredSize(new Dimension(200, 40));
        exitRoomButton.setPreferredSize(new Dimension(200, 40));
        functionPanel.add(newGameButton);
        functionPanel.add(exitRoomButton);

        // Phần chủ phòng
        //TODO : Lấy username từ user
        roomOwnerLabel = new JLabel("Chủ phòng: duyne");

        // Bảng bạn bè
        String[] columnNames = {"Tên ", "Trạng thái", "Mời"};
        friendsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Chỉ cho phép chỉnh sửa cột "Mời"
            }
        };
        friendsTable = new JTable(friendsTableModel);
        JScrollPane tableScrollPane = new JScrollPane(friendsTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Mời bạn bè"));

        // Tạo panel nền cho bảng bạn bè
        JPanel friendsPanel = new JPanel(new BorderLayout());
        friendsPanel.setBackground(new Color(240, 240, 240));
        friendsPanel.add(tableScrollPane, BorderLayout.NORTH);
        
        // Thêm khoảng trống phía dưới bảng
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(1, 200));
        emptyPanel.setBackground(new Color(240, 240, 240));
        friendsPanel.add(emptyPanel, BorderLayout.CENTER);

        // Thêm tất cả các phần vào control panel
        controlPanel.add(functionPanel);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(roomOwnerLabel);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(friendsPanel);
    }

    // Lớp đại diện cho bạn bè
    private class Friend {
        String username;
        boolean isOnline;

        Friend(String username, boolean isOnline) {
            this.username = username;
            this.isOnline = isOnline;
        }
    }


    // Khởi tạo danh sách bạn bè ảo
    private void initializeFriendsList() {
        friendsList = new ArrayList<>();
        friendsList.add(new Friend("nguyenvana", true));
        friendsList.add(new Friend("tranthib", false));
        friendsList.add(new Friend("levanc", true));
        friendsList.add(new Friend("phamthid", true));
        friendsList.add(new Friend("hoangvane", false));

        updateFriendsTable();
    }

    // Cập nhật bảng bạn bè
    private void updateFriendsTable() {
        friendsTableModel.setRowCount(0); // Xóa tất cả các hàng hiện tại
        for (Friend friend : friendsList) {
            Object[] rowData = {
                friend.username,
                friend.isOnline ? "Online" : "Offline",
                friend.isOnline ? "Mời" : ""
            };
            friendsTableModel.addRow(rowData);
        }

        // Thêm nút "Mời" vào cột cuối cùng cho bạn bè online
        friendsTable.getColumn("Mời").setCellRenderer(new ButtonRenderer());
        friendsTable.getColumn("Mời").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    // Renderer cho nút "Mời"
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null && !value.toString().isEmpty()) {
                setText(value.toString());
                setEnabled(true);
            } else {
                setText("");
                setEnabled(false);
            }
            return this;
        }
    }

    // Editor cho nút "Mời"
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (value != null && !value.toString().isEmpty()) {
                label = value.toString();
                button.setText(label);
                button.setEnabled(true);
            } else {
                label = "";
                button.setText("");
                button.setEnabled(false);
            }
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Xử lý khi nút "Mời" được nhấn
                JOptionPane.showMessageDialog(button, "Đã mời " + friendsList.get(friendsTable.getSelectedRow()).username);
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WaitingForm gui = new WaitingForm();
            gui.setVisible(true);
        });
    }
}