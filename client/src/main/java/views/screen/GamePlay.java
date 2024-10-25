package views.screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GamePlay extends JFrame {
    // Khai báo các thành phần của giao diện
    private JLabel timerLabel, keywordLabel, inputLabel;
    private JButton[][] letterButtons;
    private JLabel player1Avatar, player2Avatar;
    private JLabel player1Score, player2Score;
    private Timer timer;
    private int timeLeft = 169; // 2:49 trong giây
    private String keyword = "PROFESSIONAL";
    private StringBuilder input = new StringBuilder();
    private char[] letters = {
        'P', 'R', 'O', 'F', 'E', 'S',
        'S', 'I', 'O', 'N', 'A', 'L',
        'B', 'C', 'D', 'G', 'H', 'J',
        'K', 'M', 'Q', 'T', 'U', 'V',
        'W', 'X', 'Y', 'Z', 'M', 'K',
        'Q', 'R', 'B', 'A', 'T', 'H'
    };

    public GamePlay() {
        // Thiết lập cơ bản cho cửa sổ game
        setTitle("Trò chơi đoán từ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLayout(new BorderLayout());

        // Tạo panel trên cùng chứa đồng hồ và avatar người chơi
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // Tạo và thiết lập avatar cho người chơi 1 và 2
        player1Avatar = createAvatarLabel("./img/avatar1.png");
        player2Avatar = createAvatarLabel("./img/avatar2.png");
        
        // Tạo và thiết lập nhãn điểm số cho người chơi 1 và 2
        player1Score = new JLabel("2 Điểm");
        player2Score = new JLabel("4 Điểm");
        
        // Tạo và thiết lập nhãn đồng hồ đếm ngược
        timerLabel = new JLabel("02:49", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Tạo panel cho người chơi 1 (bên trái)
        JPanel player1Panel = new JPanel(new BorderLayout());
        player1Panel.add(player1Avatar, BorderLayout.WEST);
        player1Panel.add(player1Score, BorderLayout.CENTER);

        // Tạo panel cho người chơi 2 (bên phải)
        JPanel player2Panel = new JPanel(new BorderLayout());
        player2Panel.add(player2Score, BorderLayout.CENTER);
        player2Panel.add(player2Avatar, BorderLayout.EAST);

        // Thêm các thành phần vào panel trên cùng
        topPanel.add(player1Panel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.CENTER);
        topPanel.add(player2Panel, BorderLayout.EAST);

        // Thêm panel trên cùng vào cửa sổ chính
        add(topPanel, BorderLayout.NORTH);

        // Tạo panel giữa chứa từ khóa và ô nhập
        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        
        // Tạo và thiết lập nhãn "KEYWORD"
        keywordLabel = new JLabel("TỪ KHÓA", SwingConstants.CENTER);
        keywordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Tạo và thiết lập nhãn hiển thị từ khóa cần đoán
        JLabel keywordValueLabel = new JLabel(keyword, SwingConstants.CENTER);
        keywordValueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        
        // Tạo và thiết lập nhãn hiển thị từ đang nhập
        inputLabel = new JLabel("", SwingConstants.CENTER);
        inputLabel.setFont(new Font("Arial", Font.BOLD, 24));
        inputLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Thêm các thành phần vào panel giữa
        centerPanel.add(keywordLabel);
        centerPanel.add(keywordValueLabel);
        centerPanel.add(inputLabel);

        // Thêm panel giữa vào cửa sổ chính
        add(centerPanel, BorderLayout.CENTER);

        // Tạo panel dưới cùng chứa các nút chữ cái
        JPanel bottomPanel = new JPanel(new GridLayout(6, 6, 5, 5));
        letterButtons = new JButton[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int index = i * 6 + j;
                letterButtons[i][j] = new JButton(String.valueOf(letters[index]));
                letterButtons[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                letterButtons[i][j].addActionListener(new LetterButtonListener());
                bottomPanel.add(letterButtons[i][j]);
            }
        }

        // Thêm panel dưới cùng vào cửa sổ chính
        add(bottomPanel, BorderLayout.SOUTH);

        // Khởi tạo và bắt đầu đồng hồ đếm ngược
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                updateTimerLabel();
                if (timeLeft <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(GamePlay.this, "Hết giờ!");
                }
            }
        });
        timer.start();

        // Hiển thị cửa sổ game
        setVisible(true);
    }

    // Phương thức tạo nhãn avatar từ file hình ảnh
    private JLabel createAvatarLabel(String path) {
        try {
            Image img = ImageIO.read(new File(path)).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
            return new JLabel("Avatar");
        }
    }

    // Phương thức cập nhật hiển thị đồng hồ đếm ngược
    private void updateTimerLabel() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    // Lớp xử lý sự kiện khi nhấn nút chữ cái
    private class LetterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (!button.getText().equals("-")) {
                // Thêm chữ cái vào ô nhập
                input.append(button.getText());
                inputLabel.setText(input.toString());
                // Vô hiệu hóa nút đã nhấn
                button.setEnabled(false);

                // Kiểm tra nếu từ nhập vào khớp với từ khóa
                if (input.toString().equals(keyword)) {
                    timer.stop();
                    JOptionPane.showMessageDialog(GamePlay.this, "Chúc mừng! Bạn đã đoán đúng từ khóa!");
                }
            }
        }
    }

    // Phương thức main để chạy game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GamePlay();
            }
        });
    }
}