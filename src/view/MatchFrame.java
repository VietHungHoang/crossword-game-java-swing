package view;  

import javax.swing.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  

public class MatchFrame extends JFrame {  
    JLabel player1Avatar, player2Avatar, timerLabel, keywordLabel;  
    JTextField inputField;  
    JButton okButton;  
    JPanel topPanel, bottomPanel, centerPanel;  
    
    public MatchFrame() {  
        setTitle("THI ĐẤU");  
        setSize(400, 600);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLayout(new BorderLayout(10, 10)); 

      
        topPanel = new JPanel();  
        topPanel.setLayout(new GridLayout(1, 3, 10, 10)); // Padding between items  
        
        player1Avatar = new JLabel("BẠN", JLabel.CENTER);
		player1Avatar.setFont(new Font("Serif", Font.BOLD, 18));
        player1Avatar.setOpaque(true);
        player1Avatar.setBackground(Color.GRAY);   
        player1Avatar.setPreferredSize(new Dimension(80, 80));  
        player1Avatar.setHorizontalAlignment(JLabel.CENTER);  
 
        player2Avatar = new JLabel("ĐỐI THỦ", JLabel.CENTER); 
		player2Avatar.setFont(new Font("Serif", Font.BOLD, 18));
        player2Avatar.setOpaque(true);  
        player2Avatar.setBackground(Color.GRAY); 
        player2Avatar.setPreferredSize(new Dimension(80, 80));  
        player2Avatar.setHorizontalAlignment(JLabel.CENTER);  

        // Player labels   
        
        // Timer Label  
        timerLabel = new JLabel("02:49", JLabel.CENTER);  
        
        // Adding components to top panel  
        topPanel.add(player1Avatar);  
        topPanel.add(timerLabel);  
        topPanel.add(player2Avatar);  
        
        add(topPanel, BorderLayout.NORTH);

        // Center panel for keyword and input  
        centerPanel = new JPanel();  
        centerPanel.setLayout(new BorderLayout());  

        keywordLabel = new JLabel("PROFESSIONAL", JLabel.CENTER);  
        keywordLabel.setFont(new Font("Serif", Font.BOLD, 24));  
        centerPanel.add(keywordLabel, BorderLayout.NORTH);  

        // Text field for input  
        inputField = new JTextField(10);  
        JPanel inputPanel = new JPanel();  
        inputPanel.add(inputField);  
        
        okButton = new JButton("OK");  
        okButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                dispose();  
                new VictoryFrame();  
            }  
        });  
        inputPanel.add(okButton);  

        centerPanel.add(inputPanel, BorderLayout.SOUTH);  
        add(centerPanel, BorderLayout.CENTER); // Center section with keyword and input  

        // Bottom panel for letters grid (resize smaller)  
        bottomPanel = new JPanel();  
        bottomPanel.setLayout(new GridLayout(4, 4, 5, 5)); // Smaller padding to make the grid smaller  
        
        // Adding letters to grid (you can adjust letter list)  
        String[] letters = {"B", "C", "_", "F", "H", "I", "J", "L", "N", "O", "P", "Q", "S", "T", "W", "Y"};  
        for (String letter : letters) {  
            JButton letterButton = new JButton(letter);  
            bottomPanel.add(letterButton);  
        }  

        // Reduce the size of the bottom grid by wrapping it with a panel and adjusting its height  
        JPanel bottomWrapper = new JPanel(new BorderLayout());  
        bottomWrapper.add(bottomPanel, BorderLayout.CENTER);  
        bottomWrapper.setPreferredSize(new Dimension(400, 150)); // Adjust height for smaller grid  

        add(bottomWrapper, BorderLayout.SOUTH); // Bottom section with grid  

        setVisible(true);  
    }  

    // Main method to start the game  
    public static void main(String[] args) {  
        SwingUtilities.invokeLater(new Runnable() {  
            public void run() {  
                new MatchFrame();  
            }  
        });  
    }  
}  