package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignUpForm extends JFrame {
    private JTextField txtUsername;
    private  JPasswordField txtPassword,txtConfirmPassword;
    private JButton swapButton;
    private JButton registerButton;
    public SignUpForm() {
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
        ImageIcon logoIcon = new ImageIcon("resources/logo1.png");
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
        txtUsername = new JTextField(15);
        formPanel.add(txtUsername, gbc);

        // Ô nhập Password
        gbc.gridy = 1;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        formPanel.add(txtPassword, gbc);

        // Ô nhập Confirm Password
        gbc.gridy = 2;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        txtConfirmPassword = new JPasswordField(15);
        formPanel.add(txtConfirmPassword, gbc);

        // Nút Register
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        registerButton = new JButton("Register");
        formPanel.add(registerButton, gbc);

        // Nút "Already have an account? Login here"
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        swapButton = new JButton("Already have an account? Login here");
        swapButton.setFont(new Font("Arial", Font.PLAIN, 12));
        swapButton.setForeground(Color.BLUE);  // Màu xanh để làm nổi bật
        swapButton.setBorderPainted(false);
        swapButton.setContentAreaFilled(false);
        formPanel.add(swapButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

//        // Sự kiện nút "Already have an account? Login here"
//        swapButton.addActionListener(e -> {
//            new LoginForm();
//            dispose();
//        });

        setVisible(true);
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JPasswordField getTxtConfirmPassword() {
        return txtConfirmPassword;
    }

    public void setTxtConfirmPassword(JPasswordField txtConfirmPassword) {
        this.txtConfirmPassword = txtConfirmPassword;
    }
    public void addActionListener(ActionListener act){
        registerButton.addActionListener(act);
        swapButton.addActionListener(act);
    }

    public JButton getSwapButton() {
        return swapButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public static void main(String[] args) {
        new SignUpForm();
    }
}
