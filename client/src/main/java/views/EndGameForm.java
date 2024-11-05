package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGameForm extends JFrame {
    private JButton backButton;

    public EndGameForm(String notice, Icon icon) {
        setTitle(notice);
        setSize(300, 150);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Tạo label thông báo
        JLabel victoryLabel = new JLabel();
        if(notice.equals("Win")) victoryLabel.setText("Chúc mừng! Bạn đã thắng!");
        else victoryLabel.setText("Bạn đã thua!");
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(victoryLabel, BorderLayout.CENTER);
        
        // Tạo panel chứa 2 nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // Nút "Quay lại"
        backButton = new JButton("Quay lại");

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

    public void addActionListener(ActionListener act) {
        backButton.addActionListener(act);
    }

    public static void main(String[] args) {
        new EndGameForm("", null);
    }

    public JButton getBackButton() {
        return backButton;
    }
}
