package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class SplashScreen extends JFrame {

    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel logoLabel;
    private JPanel buttonPanel;
    private JButton loginButton;
    private JButton registerButton;
    private Image backgroundImage;

    public SplashScreen() {
        setTitle("ALPHABET FIGHTING GAME");
        setSize(800, 600);
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);

        try {
            backgroundImage = new ImageIcon(new URL("https://media1.tenor.com/m/2roX3uxz_68AAAAC/cat-space.gif")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        initComponents();
        layoutComponents();
        addListeners();

        setVisible(true);
    }

    private void initComponents() {
        titleLabel = new JLabel("ALPHABET FIGHTING GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.YELLOW);

        try {
            Image logoImage = new ImageIcon(new URL("https://static.wikia.nocookie.net/fifa_esports_gamepedia_en/images/a/a2/T1logo_square.png/revision/latest?cb=20201205050647")).getImage();
            Image scaledLogo = logoImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaledLogo));
        } catch (Exception e) {
            e.printStackTrace();
            logoLabel = new JLabel("Logo");
        }

        buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        buttonPanel.setOpaque(false);

        loginButton = createStyledButton("Login", new Color(52, 152, 219));
        registerButton = createStyledButton("Register", new Color(46, 204, 113));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
    }

    private void layoutComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 20, 20);
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        centerPanel.add(buttonPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> System.exit(0));
        headerPanel.add(closeButton, BorderLayout.EAST);
    }

    private void addListeners() {
        loginButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        registerButton.addActionListener(e -> {
            new RegisterForm();
            dispose();
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                
                int fontSize = Math.min(width / 20, height / 15);
                titleLabel.setFont(new Font("Arial", Font.BOLD, fontSize));

                int logoSize = Math.min(width / 16, height / 12);
                Image scaledLogo = ((ImageIcon) logoLabel.getIcon()).getImage().getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaledLogo));

                int buttonWidth = width - 40;
                int buttonHeight = height / 10;
                loginButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                registerButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

                revalidate();
            }
        });
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g.setColor(color.brighter());
                } else {
                    g.setColor(color);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::new);
    }
}