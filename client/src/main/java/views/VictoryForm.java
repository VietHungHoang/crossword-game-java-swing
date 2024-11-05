package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VictoryForm extends JFrame {

    public VictoryForm() {
        // Thiết lập tiêu đề cho form
        setTitle("Thông báo chiến thắng");
        
        // Thiết lập layout và kích thước của form
        setSize(300, 150);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Tạo label thông báo
        JLabel victoryLabel = new JLabel("Chúc mừng! Bạn đã thắng!", JLabel.CENTER);
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(victoryLabel, BorderLayout.CENTER);
        
        // Tạo panel chứa 2 nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // Nút "Quay lại"
        JButton backButton = new JButton("Quay lại");
        
        // Nút "Mời đấu lại"
        JButton replayButton = new JButton("Mời đấu lại");
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi bấm nút "Mời đấu lại"
                // Thực hiện hành động mời đấu lại
            }
        });
        
        // Thêm các nút vào panel
        buttonPanel.add(backButton);
        buttonPanel.add(replayButton);
        
        // Thêm panel nút vào form
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Hiển thị form
        setLocationRelativeTo(null); // Đặt form ở giữa màn hình
        setVisible(true);
    }

    public static void main(String[] args) {
        new VictoryForm();
    }
}
