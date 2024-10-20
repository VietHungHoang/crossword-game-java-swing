package client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import client.model.User;

public class ViewScoreBoard extends JFrame {

    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final Color BUTTON_COLOR = new Color(52, 152, 219);
    private static final Color PLAYER_PANEL_COLOR = Color.WHITE;
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font PLAYER_FONT = new Font("Arial", Font.PLAIN, 14);

    public ViewScoreBoard() {
        setTitle("Bảng Xếp Hạng");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.NORTH);

        // Scoreboard Panel
        JPanel scoreBoardPanel = createScoreBoardPanel();
        JScrollPane scrollPane = new JScrollPane(scoreBoardPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        // Nút quay lại (đã thêm vào nhưng chưa có logic xử lý)
        JButton backButton = createStyledButton("QUAY LẠI", new Color(108, 117, 125));
        buttonPanel.add(backButton);

        JButton viewRankingButton = createStyledButton("XEM BẢNG XẾP HẠNG", new Color(40, 167, 69));
        buttonPanel.add(viewRankingButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    private JPanel createScoreBoardPanel() {
        JPanel scoreBoardPanel = new JPanel();
        scoreBoardPanel.setLayout(new BoxLayout(scoreBoardPanel, BoxLayout.Y_AXIS));
        scoreBoardPanel.setBackground(BACKGROUND_COLOR);
        scoreBoardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "HOÀNG", "password1", "1234567890", "admin", "user11"));
        userList.add(new User(2L, "MINH", "password2", "1234567891", "user", "user2"));
        userList.add(new User(3L, "LAN", "password3", "1234567892", "user", "user3"));
        userList.add(new User(4L, "HÀ", "password4", "1234567893", "user", "user4"));

        int rank = 1;
        for (User user : userList) {
            JPanel playerPanel = createPlayerPanel(user, rank);
            scoreBoardPanel.add(playerPanel);
            scoreBoardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            rank++;
        }

        return scoreBoardPanel;
    }

    private JPanel createPlayerPanel(User user, int rank) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 0));
        panel.setBackground(PLAYER_PANEL_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Avatar Label
        JLabel avatarLabel = createAvatarLabel(user.getUserName());
        panel.add(avatarLabel, BorderLayout.WEST);

        // User Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(PLAYER_PANEL_COLOR);

        JLabel nameLabel = new JLabel(user.getUserName());
        nameLabel.setFont(PLAYER_FONT.deriveFont(Font.BOLD));
        infoPanel.add(nameLabel);

        JLabel scoreLabel = new JLabel("ĐIỂM: " + (2201 - (rank - 1) * 221)); // Example scores
        scoreLabel.setFont(PLAYER_FONT);
        infoPanel.add(scoreLabel);

        panel.add(infoPanel, BorderLayout.CENTER);

        // Rank Label
        JLabel rankLabel = new JLabel(String.valueOf(rank), SwingConstants.CENTER);
        rankLabel.setFont(new Font("Arial", Font.BOLD, 18));
        rankLabel.setForeground(BUTTON_COLOR);
        panel.add(rankLabel, BorderLayout.EAST);

        return panel;
    }

    private JLabel createAvatarLabel(String userName) {
        JLabel avatarLabel = new JLabel(userName.substring(0, 1).toUpperCase(), SwingConstants.CENTER);
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(BUTTON_COLOR);
        avatarLabel.setForeground(Color.WHITE);
        avatarLabel.setFont(new Font("Arial", Font.BOLD, 20));
        avatarLabel.setPreferredSize(new Dimension(40, 40));
        avatarLabel.setBorder(new LineBorder(BUTTON_COLOR, 2, true));
        return avatarLabel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewScoreBoard());
    }
}