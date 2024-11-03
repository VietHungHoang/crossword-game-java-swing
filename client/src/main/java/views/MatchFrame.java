package views; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer; 	
import java.util.TimerTask;

public class MatchFrame extends JFrame {
    JLabel player1Avatar, player2Avatar, timerLabel, keywordLabel;
    JTextField inputField;
    JButton okButton;
    JPanel topPanel, bottomPanel, centerPanel;
    int timeRemaining = 60; // Countdown from 60 seconds

    public MatchFrame() {
        setTitle("THI ĐẤU");
        setSize(1060, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top panel with just the timer
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 1)); // Only 1 element

        timerLabel = new JLabel("01:00", JLabel.CENTER);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 40));  // Larger font for the timer
        topPanel.add(timerLabel);

        add(topPanel, BorderLayout.NORTH);

        // Center panel with avatars and keyword
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new GridLayout(1, 2, 10, 10));

        // Reduce avatar size by half (both width and height)
        player1Avatar = new JLabel("BẠN", JLabel.CENTER);
        player1Avatar.setFont(new Font("Serif", Font.BOLD, 18)); // Reduced size font
        player1Avatar.setOpaque(true);
        player1Avatar.setBackground(Color.GRAY);
        player1Avatar.setPreferredSize(new Dimension(75, 75));  // Reduced both width and height

        player2Avatar = new JLabel("ĐỐI THỦ", JLabel.CENTER);
        player2Avatar.setFont(new Font("Serif", Font.BOLD, 18)); // Reduced size font
        player2Avatar.setOpaque(true);
        player2Avatar.setBackground(Color.GRAY);
        player2Avatar.setPreferredSize(new Dimension(75, 75));  // Reduced both width and height

        avatarPanel.add(player1Avatar);
        avatarPanel.add(player2Avatar);

        centerPanel.add(avatarPanel, BorderLayout.NORTH);

        // Keyword label
        keywordLabel = new JLabel("PROFESSIONAL", JLabel.CENTER);
        keywordLabel.setFont(new Font("Serif", Font.BOLD, 36));
        centerPanel.add(keywordLabel, BorderLayout.CENTER);

        // Text field for input
        inputField = new JTextField(20);  // Larger input field
        inputField.setFont(new Font("Serif", Font.PLAIN, 24));
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);

        okButton = new JButton("OK");
        okButton.setFont(new Font("Serif", Font.BOLD, 24));  // Larger button
        okButton.setPreferredSize(new Dimension(150, 50));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        inputPanel.add(okButton);

        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for letters grid
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 7, 5, 5));  // 4 rows, 7 columns for alphabet + special chars

        // Adding letters to grid
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-'".toCharArray();  // Added '-' and '\''
        for (char letter : letters) {
            JButton letterButton = new JButton(String.valueOf(letter));
            letterButton.setFont(new Font("Serif", Font.BOLD, 24));  // Adjust button size for better UI
            letterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Append the letter to the inputField
                    inputField.setText(inputField.getText() + letter);
                }
            });
            bottomPanel.add(letterButton);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        // Start countdown timer
        startTimer();

        setVisible(true);
    }

    // Timer countdown method
    private void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    int minutes = timeRemaining / 60;
                    int seconds = timeRemaining % 60;
                    timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                } else {
                    timer.cancel();
                    dispose();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000); // Update every second
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MatchFrame();
            }
        });
    }
}
