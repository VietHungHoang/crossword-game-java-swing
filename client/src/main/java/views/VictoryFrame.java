package views; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VictoryFrame extends JFrame {
    public VictoryFrame() {
        setTitle("CHIẾN THẮNG");
        setSize(1060, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel victoryLabel = new JLabel("CHÚC MỪNG BẠN ĐÃ THẮNG", JLabel.CENTER);
        victoryLabel.setFont(new Font("Serif", Font.BOLD, 24));
        victoryLabel.setOpaque(true);
        victoryLabel.setBackground(new Color(200, 255, 200));
        victoryLabel.setPreferredSize(new Dimension(800, 100)); // Set width to 800, height to 100

        add(victoryLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));

        JButton rematchButton = new JButton("ĐẤU LẠI");
        rematchButton.setPreferredSize(new Dimension(300, 100));
        rematchButton.setBackground(Color.YELLOW);
        rematchButton.setOpaque(true);
        rematchButton.setFont(new Font("Serif", Font.BOLD, 16));

        JButton returnButton = new JButton("QUAY LẠI");
        returnButton.setPreferredSize(new Dimension(300, 100));
        returnButton.setBackground(Color.CYAN);
        returnButton.setOpaque(true);
        returnButton.setFont(new Font("Serif", Font.BOLD, 16));

        rematchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MatchFrame();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InviteRoomFrame();
            }
        });

        buttonPanel.add(rematchButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
