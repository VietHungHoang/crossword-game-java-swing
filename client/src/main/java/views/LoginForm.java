package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utils.RoundedBorder;
public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSwap;
    private Point initialClick;
    public LoginForm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - ALPHABET FIGHTING GAME");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 700, 600, 20, 20));

        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(240, 248, 255);
                Color color2 = new Color(230, 230, 250);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        });

        // Custom title bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(33, 150, 243));
        titleBar.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel titleLabel = new JLabel("ALPHABET FIGHTING GAME");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBar.add(titleLabel, BorderLayout.CENTER);
        titleBar.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
              initialClick = e.getPoint();
          }
      });
  
      titleBar.addMouseMotionListener(new MouseAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
              int thisX = getLocation().x;
              int thisY = getLocation().y;
  
              int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
              int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);
  
              int X = thisX + xMoved;
              int Y = thisY + yMoved;
              setLocation(X, Y);
          }
      });
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        mainPanel.setOpaque(false);

        // Login form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(usernameLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        txtUsername = createStyledTextField(20);
        txtUsername.setPreferredSize(new Dimension(525, 50));
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(passwordLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        txtPassword = createStyledPasswordField(20);
        txtPassword.setPreferredSize(new Dimension(525, 50));
        formPanel.add(txtPassword, gbc);

        // Login button
        gbc.gridy = 4;
        btnLogin = createStyledButton("Login", new Color(70, 130, 180));
        btnLogin.setPreferredSize(new Dimension(525, 50));
        formPanel.add(btnLogin, gbc);

        // Register button
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 0, 0);
        btnSwap = createStyledButton("Register Here", new Color(60, 179, 113));
        btnSwap.setPreferredSize(new Dimension(525, 50));
        formPanel.add(btnSwap, gbc);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalGlue());

        // Add components to frame
        add(titleBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(1, 15, 2, 15)
        ));
        return textField;
    }

    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(1, 15, 1, 15)
        ));
        return passwordField;
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

    public JButton getBtnLogin() {
        return this.btnLogin;
    }

    public JButton getBtnSwap() {
        return this.btnSwap;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void addActionListener(ActionListener act) {
        btnLogin.addActionListener(act);
        btnSwap.addActionListener(act);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}