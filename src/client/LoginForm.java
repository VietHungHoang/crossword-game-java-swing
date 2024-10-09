package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class LoginForm extends JFrame {

    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel logoLabel;
    private JPanel formPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton backButton;
    private Image backgroundImage;

    public LoginForm() {
        setTitle("Login - ALPHABET FIGHTING GAME");
        setSize(1000, 500);
        setMinimumSize(new Dimension(800, 400));
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
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.YELLOW);

        try {
            Image logoImage = new ImageIcon(new URL("https://static.wikia.nocookie.net/fifa_esports_gamepedia_en/images/a/a2/T1logo_square.png/revision/latest?cb=20201205050647")).getImage();
            Image scaledLogo = logoImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaledLogo));
        } catch (Exception e) {
            e.printStackTrace();
            logoLabel = new JLabel("Logo");
        }

        formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                g2d.setColor(new Color(0, 0, 0));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        usernameField = createStyledTextField("Username");
        passwordField = createStyledPasswordField("Password");
        loginButton = createStyledButton("Login", new Color(46, 204, 113));
        registerButton = createStyledButton("Register", new Color(52, 152, 219));
        backButton = createStyledButton("Back to Main Menu", new Color(231, 76, 60));
    }

    private void layoutComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        formPanel.add(createStyledLabel("USERNAME"), gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        formPanel.add(createStyledLabel("PASSWORD"), gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        formPanel.add(registerButton, gbc);

        gbc.gridy = 4;
        formPanel.add(backButton, gbc);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel);

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
            // Add login logic here
            JOptionPane.showMessageDialog(this, "Login button clicked");
        });

        registerButton.addActionListener(e -> {
            new RegisterForm();
            dispose();
        });

        backButton.addActionListener(e -> {
            new SplashScreen();
            dispose();
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                
                int fontSize = Math.min(width / 25, height / 15);
                titleLabel.setFont(new Font("Arial", Font.BOLD, fontSize));

                int logoSize = Math.min(width / 12, height / 6);
                Image scaledLogo = ((ImageIcon) logoLabel.getIcon()).getImage().getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaledLogo));

                revalidate();
            }
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(255, 215, 0)); // Gold color
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(Color.GRAY);
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    g2d.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
                    g2d.dispose();
                }
            }
        };
        textField.setOpaque(false);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textField.setPreferredSize(new Dimension(250, 35));
        return textField;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0 && !isFocusOwner()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(Color.GRAY);
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    g2d.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
                    g2d.dispose();
                }
            }
        };
        passwordField.setOpaque(false);
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        passwordField.setPreferredSize(new Dimension(250, 35));
        return passwordField;
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
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 40));

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
}