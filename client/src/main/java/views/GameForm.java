package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameForm extends JFrame {
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JLabel keywordLabel;
    private JLabel countdownLabel;
    private JTextField enteredWordsField;
    private JButton submitButton;
    private JPanel keyboardPanel;
    private JButton deleteButton;
    private Timer countdownTimer;
    private int timeLeft = 60; // Đếm ngược từ 60 giây
    private String keyword;
    private List<JButton> keyboardButtons = new ArrayList<>();
    private StringBuilder enteredWords = new StringBuilder();

    public GameForm(String player1, String player2, String keyword, int player1Score, int player2Score) {
        this.keyword = keyword;

        // Cấu hình cửa sổ chính
        setTitle("Game Form");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo phần thông tin trên cùng
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        player1Label = new JLabel(player1);
        countdownLabel = new JLabel("60");
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        player2Label = new JLabel(player2);

        topPanel.add(player1Label);
        topPanel.add(countdownLabel);
        topPanel.add(player2Label);
        add(topPanel, BorderLayout.NORTH);

        // Tạo phần hiển thị điểm
        JPanel scorePanel = new JPanel(new GridLayout(1, 2));
        player1ScoreLabel = new JLabel("Điểm: " + player1Score);
        player2ScoreLabel = new JLabel("Điểm: " + player2Score);

        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);
        add(scorePanel, BorderLayout.CENTER);

        // Hiển thị từ khóa
        keywordLabel = new JLabel("Keyword: " + keyword);
        keywordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(keywordLabel, BorderLayout.CENTER);

        // Phần hiển thị các từ đã nhập
        JPanel enteredWordsPanel = new JPanel(new BorderLayout());
        enteredWordsField = new JTextField();
        enteredWordsField.setEditable(false);

        enteredWordsPanel.add(new JLabel("Các từ đã nhập:"), BorderLayout.NORTH);
        enteredWordsPanel.add(enteredWordsField, BorderLayout.CENTER);

        deleteButton = new JButton("Xóa");
        deleteButton.addActionListener(e -> deleteLastCharacter());
        enteredWordsPanel.add(deleteButton, BorderLayout.EAST);

        add(enteredWordsPanel, BorderLayout.CENTER);

        // Nút xác nhận kết quả
        submitButton = new JButton("Xác nhận kết quả");
        submitButton.addActionListener(e -> submitAnswer());
        add(submitButton, BorderLayout.SOUTH);

        // Bàn phím
        keyboardPanel = new JPanel(new GridLayout(6, 6));
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(String.valueOf(c));
            button.addActionListener(new KeyboardButtonListener());
            keyboardButtons.add(button);
            keyboardPanel.add(button);
        }
        add(keyboardPanel, BorderLayout.SOUTH);

        // Thiết lập đếm ngược
        countdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (timeLeft > 0) {
                    timeLeft--;
                    countdownLabel.setText(String.valueOf(timeLeft));
                } else {
                    countdownTimer.stop();
                    JOptionPane.showMessageDialog(null, "Hết thời gian!");
                }
            }
        });
        countdownTimer.start();
        setVisible(true);
    }

    // Phương thức xử lý khi người chơi bấm vào nút bàn phím
    private class KeyboardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            enteredWords.append(button.getText());
            enteredWordsField.setText(enteredWords.toString());
            button.setEnabled(false);
        }
    }

    // Phương thức để xóa ký tự cuối cùng khỏi từ đã nhập
    private void deleteLastCharacter() {
        if (enteredWords.length() > 0) {
            char lastChar = enteredWords.charAt(enteredWords.length() - 1);
            enteredWords.deleteCharAt(enteredWords.length() - 1);
            enteredWordsField.setText(enteredWords.toString());

            // Tìm nút tương ứng và bật lại
            for (JButton button : keyboardButtons) {
                if (button.getText().equalsIgnoreCase(String.valueOf(lastChar))) {
                    button.setEnabled(true);
                    break;
                }
            }
        }
    }

    // Xử lý khi bấm nút "Xác nhận kết quả"
    private void submitAnswer() {
        if (enteredWords.toString().equalsIgnoreCase(keyword)) {
            JOptionPane.showMessageDialog(this, "Kết quả chính xác!");
            // Ở đây sẽ gọi controller để cập nhật kết quả lên server
        } else {
            JOptionPane.showMessageDialog(this, "Kết quả sai, hãy thử lại!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameForm gameForm = new GameForm("Người chơi 1", "Người chơi 2", "KEYWORD", 0, 0);
            gameForm.setVisible(true);
        });
    }
    public JLabel getLabelPlayer1() {
      return player1Label;
  }
  
  public void setLabelPlayer1(JLabel player1Label) {
      this.player1Label = player1Label;
  }
  
  public JLabel getLabelPlayer2() {
      return player2Label;
  }
  
  public void setLabelPlayer2(JLabel player2Label) {
      this.player2Label = player2Label;
  }
  
  public JLabel getPlayer1ScoreLabel() {
      return player1ScoreLabel;
  }
  
  public void setPlayer1ScoreLabel(JLabel player1ScoreLabel) {
      this.player1ScoreLabel = player1ScoreLabel;
  }
  
  public JLabel getPlayer2ScoreLabel() {
      return player2ScoreLabel;
  }
  
  public void setPlayer2ScoreLabel(JLabel player2ScoreLabel) {
      this.player2ScoreLabel = player2ScoreLabel;
  }
  
  public JLabel getKeywordLabel() {
      return keywordLabel;
  }
  
  public void setKeywordLabel(JLabel keywordLabel) {
      this.keywordLabel = keywordLabel;
  }
  
  public JLabel getCountdownLabel() {
      return countdownLabel;
  }
  
  public void setCountdownLabel(JLabel countdownLabel) {
      this.countdownLabel = countdownLabel;
  }
}
