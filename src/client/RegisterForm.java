package client;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {

    public RegisterForm() {
        setTitle("Register - ALPHABET FIGHTING GAME");
        setSize(500, 500);  // Tăng kích thước cửa sổ để logo và form hiển thị đẹp hơn
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Căn giữa màn hình
        setLayout(new BorderLayout());

        // Thêm tiêu đề và logo
        JLabel titleLabel = new JLabel("ALPHABET FIGHTING GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        // Thêm logo
        ImageIcon logiIcon = new ImageIcon("resources/logo1.png");
        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        add(logoLabel, BorderLayout.CENTER);

        // Tạo form đăng ký
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());  // Sử dụng GridBagLayout để căn giữa form

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Thiết lập khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ô nhập Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JTextField(15), gbc);

        // Ô nhập Password
        gbc.gridy = 1;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JPasswordField(15), gbc);

        // Ô nhập Confirm Password
        gbc.gridy = 2;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JPasswordField(15), gbc);

        // Nút Register
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton registerButton = new JButton("Register");
        formPanel.add(registerButton, gbc);

        // Nút "Already have an account? Login here"
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton swapButton = new JButton("Already have an account? Login here");
        swapButton.setFont(new Font("Arial", Font.PLAIN, 12));
        swapButton.setForeground(Color.BLUE);  // Màu xanh để làm nổi bật
        swapButton.setBorderPainted(false);
        swapButton.setContentAreaFilled(false);
        formPanel.add(swapButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // Sự kiện nút "Already have an account? Login here"
        swapButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterForm();
    }
}
