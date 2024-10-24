package views;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSwap;

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
        txtUsername = new JTextField(15);
        formPanel.add(txtUsername, gbc);

        // Ô nhập Password
        gbc.gridy = 1;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        formPanel.add(txtPassword, gbc);

        // Nút Login
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnLogin = new JButton("Login");
        formPanel.add(btnLogin, gbc);

        // Nút "Register Here"
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnSwap = new JButton("Register Here");
        btnSwap.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSwap.setForeground(Color.BLUE);  // Màu xanh để làm nổi bật
        btnSwap.setBorderPainted(false);  // Loại bỏ viền nút
        btnSwap.setContentAreaFilled(false);  // Không tô nền nút
        formPanel.add(btnSwap, gbc);

        add(formPanel, BorderLayout.SOUTH);

    }

    public JButton getBtnLogin(){
        return this.btnLogin;
    }

    public JButton getBtnSwap(){
        return this.btnSwap;
    }


    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void addActionListener(ActionListener act){
        btnLogin.addActionListener(act);
        btnSwap.addActionListener(act);
    }

    public void showMessage(String msg){
        JOptionPane.showConfirmDialog(this, msg);
    }
    
}
