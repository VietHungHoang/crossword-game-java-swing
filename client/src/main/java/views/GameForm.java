package views;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import utils.GetImage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameForm extends JFrame {

    private Font lblFont;
    private Font timeFont;
    private Font keybFont;
    private Font keywFont;
    private Font keywBtnFont;
    private GetImage getImage;
    private JPanel pnlGameInfo;
    private JPanel pnlKeyword;
    private JPanel pnlTextbox;
    private JPanel pnlKeyboard;
    private JLabel imgPlayer1;
    private JLabel imgPlayer2;
    private JLabel lblPlayer1Name;
    private JLabel lblPlayer2Name;
    private JLabel lblPlayer1Score;
    private JLabel lblPlayer2Score;
    private JLabel lblKeyword;
    private JLabel lblCountdown;
    private Timer countdownTimer;
    private int timeLeft = 60;
    private String keyword;
    private List<JButton> keyboardButtons = new ArrayList<>();
    private List<JButton> listKeywordBtns = new ArrayList<>();

    public Timer getCountdownTimer() {
        return countdownTimer;
    }

    public GameForm(){

    }

    public GameForm(String player1Name, String player2Name, String keyword, int player1Score, int player2Score) {
        getImage = new GetImage();
        this.keyword = keyword;
        lblFont = new Font("Dialog", Font.CENTER_BASELINE, 17);
        timeFont = new Font("Dialog", Font.CENTER_BASELINE, 20);
        keybFont = new Font("Dialog", Font.CENTER_BASELINE, 12);
        keywFont = new Font("Dialog", Font.CENTER_BASELINE, 26);
        keywBtnFont = new Font("Dialog", Font.CENTER_BASELINE, 16);

        // Config JFrame
        setTitle("Alphabet Challenge");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/assets/img/logo.png")).getImage());

        // PANEL1: Game info
        pnlGameInfo = new JPanel();
        pnlGameInfo.setLayout(null);
        pnlGameInfo.setBounds(0, 0, 500, 100);

        imgPlayer1 = new JLabel(getImage.get("1.png", 60, 60));
        imgPlayer2 = new JLabel(getImage.get("2.png", 60, 60));
        lblPlayer1Name = new JLabel(player1Name);
        lblPlayer2Name = new JLabel(player2Name);
        lblPlayer1Score = new JLabel("Điểm: " + player1Score);
        lblPlayer2Score = new JLabel("Điểm: " + player2Score);
        lblCountdown = new JLabel("60");

        // Player1 image
        imgPlayer1.setBounds(25, 30, 60, 60);

        // Player2 image
        imgPlayer2.setBounds(410, 30, 60, 60);

        // Player1 info
        lblPlayer1Name.setFont(lblFont);
        lblPlayer1Name.setBounds(85, 50, 110, 20);

        // Player2 info
        lblPlayer2Name.setFont(lblFont);
        lblPlayer2Name.setBounds(300, 50, 110, 20);

        // Player1 score

        // Player2 score

        // Countdown
        lblCountdown.setFont(lblFont);
        lblCountdown.setFont(timeFont);
        lblCountdown.setHorizontalAlignment(SwingConstants.CENTER);
        lblCountdown.setBounds(220, 20, 60, 60);

        // Add to panel
        pnlGameInfo.add(imgPlayer1);
        pnlGameInfo.add(imgPlayer2);
        pnlGameInfo.add(lblPlayer1Name);
        pnlGameInfo.add(lblCountdown);
        pnlGameInfo.add(lblPlayer2Name);
        pnlGameInfo.add(lblPlayer1Score);
        pnlGameInfo.add(lblPlayer2Score);

        // PANEL2: Keyword panel
        pnlKeyword = new JPanel();
        pnlKeyword.setLayout(null);
        pnlKeyword.setBounds(0, 100, 500, 170);

        // init component
        lblKeyword = new JLabel(keyword);
        lblKeyword.setBounds(50, 50, 400, 100);
        lblKeyword.setBorder(new LineBorder(Color.BLACK, 2));
        lblKeyword.setHorizontalAlignment(SwingConstants.CENTER);
        lblKeyword.setFont(keywFont);

        pnlKeyword.add(lblKeyword);

        // PANEL3: Textbox panel
        pnlTextbox = new JPanel(new FlowLayout());
        pnlTextbox.setBounds(25, 270, 440, 100);
        for (int i = 0; i < keyword.length(); i++) {
            JButton x = new JButton();
            x.setFont(keywBtnFont);
            x.setPreferredSize(new Dimension(50, 50));
            x.addActionListener(new KeywordListener());
            listKeywordBtns.add(x);
            pnlTextbox.add(x);
        }

        // PANEL4: Bàn phím
        pnlKeyboard = new JPanel(new GridLayout(3, 8, 10, 10));
        pnlKeyboard.setBounds(25, 440, 440, 180);
        String[] key = getKeyboard(keyword);
        for (String c : key) {
            JButton button = new JButton(c);
            button.setFont(keybFont);
            // button.addActionListener(new KeyboardButtonListener());
            keyboardButtons.add(button);
            pnlKeyboard.add(button);
        }

        // Thiết lập đếm ngược

        add(pnlGameInfo);
        add(pnlKeyword);
        add(pnlTextbox);
        add(pnlKeyboard);
    }

    private class KeywordListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            String text = btn.getText();
            btn.setText("");
            for (JButton button : keyboardButtons) {
                if (button.getText().equalsIgnoreCase(text)) {
                    if (!button.isEnabled()) {
                        button.setEnabled(true);
                        break;
                    }
                }
            }
        }
    }

    public void addActionListener(ActionListener act){
        for(JButton x : keyboardButtons)
            x.addActionListener(act);
    }

    public String[] getKeyboard(String keyword) {
        String[] res = new String[24];
        HashMap<Character, Integer> map = new HashMap<>();
        for (char x : keyword.toCharArray())
            map.put(x, map.containsKey(x) ? map.get(x) + 1 : 1);
        Random random = new Random();
        int i;
        for (i = 0; i < 24 - map.size(); i++)
            res[i] = Character.toString(65 + random.nextInt(26));
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            int n = entry.getValue();
            while (n-- > 0) {
                res[i] = entry.getKey().toString();
                i++;
                n--;
            }
        }
        return res;
    }

    
    public JLabel getLblPlayer1Name() {
        return lblPlayer1Name;
    }


    public void setLblPlayer1Name(JLabel lblPlayer1Name) {
        this.lblPlayer1Name = lblPlayer1Name;
    }


    public JLabel getLblPlayer2Name() {
        return lblPlayer2Name;
    }


    public void setLblPlayer2Name(JLabel lblPlayer2Name) {
        this.lblPlayer2Name = lblPlayer2Name;
    }


    public String getKeyword() {
        return keyword;
    }


    public List<JButton> getKeyboardButtons() {
        return keyboardButtons;
    }


    public List<JButton> getListKeywordBtns() {
        return listKeywordBtns;
    }

    public JLabel getLblCountdown() {
        return lblCountdown;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameForm gameForm = new GameForm("Người chơi 1", "Người chơi 2", "KEYWORD", 0, 0);
            gameForm.setVisible(true);
        });
    }

}
