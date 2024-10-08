package view;  

import javax.swing.*;  
import java.awt.*;  

public class VictoryFrame extends JFrame {  
    public VictoryFrame() {  
        setTitle("CHIẾN THẮNG");  
        setSize(500, 300);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLayout(new BorderLayout());  
        
        // Congratulation Label  
        JLabel victoryLabel = new JLabel("CHÚC MỪNG BẠN ĐÃ THẮNG", JLabel.CENTER);  
        victoryLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Tăng kích thước chữ  
        victoryLabel.setOpaque(true);  
        victoryLabel.setBackground(new Color(200, 255, 200)); // Light green background  
        victoryLabel.setPreferredSize(new Dimension(400, 50)); // Đặt kích thước cho nhãn  
        
        add(victoryLabel, BorderLayout.NORTH);  
        
        // Buttons for next actions  
        JPanel buttonPanel = new JPanel();  
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Tăng khoảng cách giữa các nút  
        
        // Button 1 - Rematch  
        JButton rematchButton = new JButton("ĐẤU LẠI");  
        rematchButton.setPreferredSize(new Dimension(150, 50)); // Đặt kích thước cho nút  
        rematchButton.setBackground(Color.YELLOW);  
        rematchButton.setOpaque(true);  
        rematchButton.setFont(new Font("Serif", Font.BOLD, 16)); // Tăng kích thước chữ  
        
        // Button 2 - Return  
        JButton returnButton = new JButton("QUAY LẠI");  
        returnButton.setPreferredSize(new Dimension(150, 50)); // Đặt kích thước cho nút  
        returnButton.setBackground(Color.CYAN);  
        returnButton.setOpaque(true);  
        returnButton.setFont(new Font("Serif", Font.BOLD, 16)); // Tăng kích thước chữ  
        
        // Add buttons to panel  
        buttonPanel.add(rematchButton);  
        buttonPanel.add(returnButton);  
        
        add(buttonPanel, BorderLayout.CENTER);  
        
        setVisible(true);  
    }  
}