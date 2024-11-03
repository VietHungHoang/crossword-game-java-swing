package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGameForm extends JFrame {

    public EndGameForm(String notice, Icon icon) {
        setTitle(notice);
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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi bấm nút "Quay lại"
                dispose(); // Đóng form thông báo
                // Thực hiện hành động quay lại hoặc mở menu chính
            }
        });
        
        // Nút "Mời đấu lại"
        JButton replayButton = new JButton("Mời đấu lại");
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi bấm nút "Mời đấu lại"
                // Thực hiện hành động mời đấu lại
            }
        });
        
        buttonPanel.add(backButton);
        buttonPanel.add(replayButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new EndGameForm("", null);
    }
}
