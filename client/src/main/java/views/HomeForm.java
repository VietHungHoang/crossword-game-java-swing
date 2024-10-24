package views;

import utils.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.SwingUtilities.invokeLater;

public class HomeForm extends JFrame {
    private JPanel mainPanel;
    private JLabel userLabel, scoreLabel;
    private JButton rankButton, friendButton, customButton, rankingButton, logoutButton;

    public HomeForm() {
        initComponents();
    }

    private void initComponents() {
        // Set up the JFrame
        setTitle("ALPHABET FIGHTING GAME");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setLayout(new BorderLayout());  // Use BorderLayout for better component placement

        // Main panel with vertical layout (Y_AXIS)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Add padding around main panel

        // User Information Panel
        JPanel userPanel = new JPanel(new BorderLayout());  // Use BorderLayout to position components
        JPanel leftUserInfo = new JPanel(new FlowLayout(FlowLayout.LEADING));  // Use FlowLayout for horizontal alignment

        // User label
        String playerName = "Binh dang";
        userLabel = new JLabel();
        userLabel.setText("Binh dang");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

//        // Placeholder for avatar image
        JLabel avatarLabel = new JLabel();
//        avatarLabel.setIcon(new ImageIcon(new ImageIcon("src/client/img/avatar.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

        // Add avatar and user label to the left panel
        leftUserInfo.add(avatarLabel);
        leftUserInfo.add(Box.createRigidArea(new Dimension(10, 0)));  // Add horizontal space between avatar and label
        leftUserInfo.add(userLabel);

        String Score = "0.0";
        // Create a panel for the score label
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  // Align score label to the right
        scoreLabel = new JLabel(Score);
        scoreLabel.setText(Score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setPreferredSize(new Dimension(150, 30));  // Set height equal to avatar
        scoreLabel.setBorder(new RoundedBorder(15, Color.RED));  // Set radius to 15 for rounded border
        scoreLabel.setOpaque(true);  // Ensure background is visible
        scoreLabel.setBackground(new Color(241, 227, 31));  // Set background color if needed

        // Add score label to the score panel
        scorePanel.add(scoreLabel);

        // Add left info (avatar + name) to the userPanel
        userPanel.add(leftUserInfo, BorderLayout.WEST);  // Add to WEST
        userPanel.add(scorePanel, BorderLayout.EAST);  // Add to EAST

        // Buttons
        rankButton = new JButton("ĐÁNH RANK 🔥");
        rankButton.setBackground(Color.RED);
        rankButton.setForeground(Color.WHITE);
        rankButton.setPreferredSize(new Dimension(250, 50));  // Set button size

        friendButton = new JButton("CHƠI CÙNG BẠN BÈ 🐻");
        friendButton.setBackground(new Color(85, 167, 247));
        friendButton.setForeground(Color.WHITE);
        friendButton.setPreferredSize(new Dimension(250, 50));  // Set button size

        customButton = new JButton("TÙY CHỈNH ⚙️");
        customButton.setBackground(new Color(0, 204, 102));
        customButton.setForeground(Color.WHITE);
        customButton.setPreferredSize(new Dimension(250, 50));  // Set button size

        rankingButton = new JButton("XEM BẢNG XẾP HẠNG 🍭");
        rankingButton.setBackground(new Color(255, 153, 51));
        rankingButton.setForeground(Color.WHITE);
        rankingButton.setPreferredSize(new Dimension(250, 50));  // Set button size

        // New Logout button
        logoutButton = new JButton("ĐĂNG XUẤT 🚪");
        logoutButton.setBackground(Color.GRAY);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(250, 50));  // Set button size

        // Footer label
        JLabel footerLabel = new JLabel("Chọn một mục để bắt đầu chơi!");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // Create a panel to hold the footer label and set its alignment
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS)); // Vertical alignment
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.add(footerLabel);
        footerLabel.setPreferredSize(new Dimension(250, 60)); // Set width to match buttons
        footerLabel.setOpaque(true); // Make sure the label doesn't have a background

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(rankButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add some space between buttons
        buttonPanel.add(friendButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(customButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(rankingButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Space before logout
        buttonPanel.add(logoutButton);  // Add logout button

        // Add components to the main panel
        mainPanel.add(userPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));  // Add space between sections
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));  // Add space before footer
        mainPanel.add(footerPanel); // Add footerPanel instead of footerLabel directly

        // Add the main panel to the center of the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        invokeLater(new Runnable() {
            public void run() {
                new HomeForm().setVisible(true);
            }
        });
    }

    // Getter methods to access components
    public JButton getRankButton() {
        return rankButton;
    }

    public JButton getFriendButton() {
        return friendButton;
    }

    public JButton getCustomButton() {
        return customButton;
    }

    public JButton getRankingButton() {
        return rankingButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;  // Getter for logout button
    }

    public JLabel getUserLabel() {
        return userLabel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public void addActionListener(ActionListener act) {
        rankButton.addActionListener(act);
        friendButton.addActionListener(act);
        customButton.addActionListener(act);
        rankingButton.addActionListener(act);
        logoutButton.addActionListener(act);  // Add action listener for logout
    }
}
