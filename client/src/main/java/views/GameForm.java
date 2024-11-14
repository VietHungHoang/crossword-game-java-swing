package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import utils.GetImage;
import utils.RoundedBorder;

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
    private Point initialClick;

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

        // Set up JFrame with custom design
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 500, 700, 20, 20));

        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(240, 248, 255);
                Color color2 = new Color(230, 230, 250);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        });

        // Add mouse listeners for dragging the window
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);
                setLocation(thisX + xMoved, thisY + yMoved);
            }
        });

        // Maintain original layout
        setLayout(null);

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

        // Player1 image and name
        imgPlayer1.setBounds(50, 10, 60, 60);
        lblPlayer1Name.setBounds(50, 70, 100, 20);
        lblPlayer1Name.setFont(new Font("Arial", Font.BOLD, 14));

        // Player2 image and name
        imgPlayer2.setBounds(390, 10, 60, 60);
        lblPlayer2Name.setBounds(390, 70, 100, 20);
        lblPlayer2Name.setFont(new Font("Arial", Font.BOLD, 14));
// Set background gradient
pnlGameInfo.setBackground(new Color(240, 248, 255)); 
        // Time display with styled rectangle
        JLabel timeLabel = getLblCountdown();
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setOpaque(true);
        timeLabel.setBackground(new Color(255, 255, 255));
        timeLabel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        timeLabel.setBounds(150, 10, 200, 40);
        pnlGameInfo.add(timeLabel);
        // Add gradient background to main panel


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
        lblKeyword.setHorizontalAlignment(SwingConstants.CENTER);
        lblKeyword.setFont(keywFont);

        pnlKeyword.add(lblKeyword);

        // PANEL3: Textbox panel
        pnlTextbox = new JPanel(new FlowLayout());
        pnlTextbox.setBounds(25, 300, 440, 100);
        for (int i = 0; i < keyword.length(); i++) {
            JButton x = new JButton();
            x.setFont(keywBtnFont);
            x.setPreferredSize(new Dimension(50, 50));
            x.addActionListener(new KeywordListener());
            listKeywordBtns.add(x);
            pnlTextbox.add(x);
        }

        // PANEL4: Keyboard panel
        pnlKeyboard = new JPanel(new GridLayout(3, 8, 5, 5));
        pnlKeyboard.setBounds(25, 450, 440, 180);
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

        // Style buttons similarly to other forms
        for (JButton button : keyboardButtons) {
            styleButton(button, new Color(70, 130, 180));
        }

        // Initialize countdown timer
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    lblCountdown.setText(String.valueOf(timeLeft));
                } else {
                    countdownTimer.stop();
                    // Handle end of countdown (e.g., disable buttons, show message)
                }
            }
        });
        countdownTimer.start();
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

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(5, 5, 5, 5));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(brighten(backgroundColor, 0.2f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(darken(backgroundColor, 0.2f));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        // Add shadow effect
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(2, 2, 2, 2),
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50))
        ));
    }

    private Color brighten(Color color, float fraction) {
        int red = Math.min(255, (int)(color.getRed() * (1 + fraction)));
        int green = Math.min(255, (int)(color.getGreen() * (1 + fraction)));
        int blue = Math.min(255, (int)(color.getBlue() * (1 + fraction)));
        return new Color(red, green, blue);
    }

    private Color darken(Color color, float fraction) {
        int red = Math.max(0, (int)(color.getRed() * (1 - fraction)));
        int green = Math.max(0, (int)(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, (int)(color.getBlue() * (1 - fraction)));
        return new Color(red, green, blue);
    }

}