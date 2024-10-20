package client.views;  

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {

    public SplashScreen() {
        setTitle("ALPHABET FIGHTING GAME");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Căn giữa màn hình
        setLayout(new GridBagLayout());  // Sử dụng GridBagLayout để căn giữa

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Thiết lập khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;  // Căn giữa mọi thứ

        // Thêm tiêu đề
        JLabel titleLabel = new JLabel("ALPHABET FIGHTING GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));  // Tăng kích thước font
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Thêm logo
        ImageIcon logoIcon = new ImageIcon("resources/logo1.png");

        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(logoLabel, gbc);

        // Thêm nút "Login"
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JButton loginButton = new JButton("Login");
        add(loginButton, gbc);

        // Thêm nút "Register"
        gbc.gridx = 1;
        JButton registerButton = new JButton("Register");
        add(registerButton, gbc);

        // Sự kiện khi nhấn nút Login
        loginButton.addActionListener(e -> {
            new LoginForm();  // Mở màn hình đăng nhập
            dispose();  // Đóng màn hình chờ
        });

        // Sự kiện khi nhấn nút Register
        registerButton.addActionListener(e -> {
            new RegisterForm();  // Mở màn hình đăng ký
            dispose();  // Đóng màn hình chờ
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new SplashScreen();
    }
}
