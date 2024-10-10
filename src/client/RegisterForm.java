package client;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {

    public RegisterForm() {
        setTitle("Register - ALPHABET FIGHTING GAME");
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
        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridy = 1;
        gbc.weighty = 0.3;
        add(logoLabel, gbc);

        // Tạo form đăng ký
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 0, 10, 0);
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.anchor = GridBagConstraints.CENTER;

        // Ô nhập Username
        JLabel usernameLabel = new JLabel("Username:");
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formPanel.add(usernameLabel, formGbc);

        JTextField usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(250, 40));
        formGbc.gridy = 1;
        formPanel.add(usernameField, formGbc);

        // Ô nhập Password
        JLabel passwordLabel = new JLabel("Password:");
        formGbc.gridy = 2;
        formPanel.add(passwordLabel, formGbc);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(250, 40));
        formGbc.gridy = 3;
        formPanel.add(passwordField, formGbc);

        // Ô nhập Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        formGbc.gridy = 4;
        formPanel.add(confirmPasswordLabel, formGbc);

        JPasswordField confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setPreferredSize(new Dimension(250, 40));
        formGbc.gridy = 5;
        formPanel.add(confirmPasswordField, formGbc);

        // Nút Register
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(250, 50));
        formGbc.gridy = 6;
        formGbc.insets = new Insets(20, 0, 10, 0);
        formPanel.add(registerButton, formGbc);

        // Nút "Already have an account? Login here"
        JButton loginButton = new JButton("Already have an account? Login here");
        loginButton.setPreferredSize(new Dimension(250, 50));
        formGbc.gridy = 7;
        formGbc.insets = new Insets(10, 0, 10, 0);
        formPanel.add(loginButton, formGbc);

        // Nút "Back to Main Menu"
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(250, 50));
        formGbc.gridy = 8;
        formPanel.add(backButton, formGbc);

        gbc.gridy = 2;
        gbc.weighty = 0.5;
        add(formPanel, gbc);

        // Sự kiện khi nhấn nút "Already have an account? Login here"
        loginButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        // Sự kiện khi nhấn nút "Back to Main Menu"
        backButton.addActionListener(e -> {
            new SplashScreen();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterForm::new);
    }
}