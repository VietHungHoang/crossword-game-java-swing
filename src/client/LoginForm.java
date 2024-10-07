package client;

import javax.swing.*;
import java.awt.*;
public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("Login - ALPHABET FIGHTING GAME");
        setSize(500, 500);  // Tăng kích thước cửa sổ để giao diện hiển thị tốt hơn
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Căn giữa màn hình
        setLayout(new BorderLayout());

        // Thêm tiêu đề và logo
        JLabel titleLabel = new JLabel("ALPHABET FIGHTING GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        // Thêm logo
        ImageIcon logoIcon = new ImageIcon("resources/logo1.png");
        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        add(logoLabel, BorderLayout.CENTER);

        // Tạo form đăng nhập
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

        // Nút Login
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        formPanel.add(loginButton, gbc);

        // Nút "Register Here"
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton swapButton = new JButton("Register Here");
        swapButton.setFont(new Font("Arial", Font.PLAIN, 12));
        swapButton.setForeground(Color.BLUE);  // Màu xanh để làm nổi bật
        swapButton.setBorderPainted(false);  // Loại bỏ viền nút
        swapButton.setContentAreaFilled(false);  // Không tô nền nút
        formPanel.add(swapButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // Sự kiện khi nhấn nút "Register Here"
        swapButton.addActionListener(e -> {
            new RegisterForm();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
