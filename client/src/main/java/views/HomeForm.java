package views;

import utils.RoundedBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

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
        setTitle("ALPHABET FIGHTING");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 700, 600, 20, 20));

        // Custom title bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(33, 150, 243));
        JLabel titleLabel = new JLabel("ALPHABET FIGHTING");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titleBar.add(titleLabel, BorderLayout.WEST);

        // Main panel with vertical layout (Y_AXIS)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        // User Information Panel
        JPanel userPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        userPanel.setOpaque(false);

        // User label with rounded border
        userLabel = new JLabel("Xin chào Binh dang", SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setOpaque(true);
        userLabel.setBackground(new Color(179, 229, 252));
        userLabel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, new Color(33, 150, 243)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Score panel
        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.setOpaque(false);
        JLabel scoreTextLabel = new JLabel("", SwingConstants.RIGHT);
        scoreTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreTextLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        
        scoreLabel = new JLabel("0.0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(255, 255, 0));
        scoreLabel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, new Color(255, 200, 0)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        scorePanel.add(scoreTextLabel, BorderLayout.WEST);
        scorePanel.add(scoreLabel, BorderLayout.CENTER);

        // Add components to userPanel
        userPanel.add(userLabel);
        userPanel.add(scorePanel);

      // Thay đổi text khi tạo button
        rankButton = createStyledButton("ĐÁNH RANK ", new Color(255, 50, 50));
        friendButton = createStyledButton("CHƠI CÙNG BẠN BÈ ", new Color(85, 167, 247));
        customButton = createStyledButton("DANH SÁCH NGƯỜI CHƠI ", new Color(0, 204, 102));
        rankingButton = createStyledButton("XEM BẢNG XẾP HẠNG ", new Color(255, 153, 51));
        logoutButton = createStyledButton("ĐĂNG XUẤT ", new Color(158, 158, 158));

        // Footer label
        JLabel footerLabel = new JLabel("Chọn một mục để bắt đầu chơi!");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        footerLabel.setForeground(new Color(100, 100, 100));

        // Button panel with centered alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setOpaque(false);

        // Add buttons with consistent spacing
        addButtonWithSpacing(buttonPanel, rankButton);
        addButtonWithSpacing(buttonPanel, friendButton);
        addButtonWithSpacing(buttonPanel, customButton);
        addButtonWithSpacing(buttonPanel, rankingButton);
        addButtonWithSpacing(buttonPanel, logoutButton);

        // Center align the buttons
        rankButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        friendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rankingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setOpaque(false);
        footerPanel.add(footerLabel);

        // Add all components to main panel
        mainPanel.add(userPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(footerPanel);

        // Add title bar and main panel to frame
        add(titleBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void addButtonWithSpacing(JPanel panel, JButton button) {
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(button);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
                
                // Draw button background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 20, 20);
                
                // Draw text
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
        button.setPreferredSize(new Dimension(500, 50));
        button.setMaximumSize(new Dimension(500, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
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
        return logoutButton;
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
        logoutButton.addActionListener(act);
    }
}