package client;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {

    public SplashScreen() {
        setTitle("ALPHABET FIGHTING GAME");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Thêm tiêu đề
        JLabel titleLabel = new JLabel("ALPHABET FIGHTING GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 0.2;
        add(titleLabel, gbc);

        // Thêm logo
        ImageIcon logoIcon = new ImageIcon("resources/logo1.png");
        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.weighty = 0.3;
        add(logoLabel, gbc);

        // Panel cho các nút
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(15, 0, 15, 0);
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;
        buttonGbc.anchor = GridBagConstraints.CENTER;

        // Thêm nút "Login"
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(350, 60));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonGbc.gridy = 0;
        buttonPanel.add(loginButton, buttonGbc);

        // Thêm nút "Register"
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(350, 60));
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonGbc.gridy = 1;
        buttonPanel.add(registerButton, buttonGbc);

        // Thêm panel chứa các nút vào frame
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        add(buttonPanel, gbc);

        // Sự kiện khi nhấn nút Login
        loginButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        // Sự kiện khi nhấn nút Register
        registerButton.addActionListener(e -> {
            new RegisterForm();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::new);
    }
}