package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import models.PlayerRanking;

public class RankingForm extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private JButton backButton;
    private List<PlayerRanking> playerRankings = new ArrayList<>();

    public RankingForm() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Bảng Xếp Hạng - ALPHABET FIGHTING GAME");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 800, 600, 20, 20));

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

        // Custom title bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(33, 150, 243));
        titleBar.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel titleLabel = new JLabel("BẢNG XẾP HẠNG");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBar.add(titleLabel, BorderLayout.CENTER);

        // Table setup
        String[] columnNames = {"STT", "TÊN PLAYER", "TỈ LỆ THẮNG", "SỐ TRẬN THẮNG", "SỐ TRẬN ĐÃ ĐẤU", "ĐIỂM"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        int totalWidth = 780; // Để lại margin 10px mỗi bên (800 - 20)
        table.getColumnModel().getColumn(0).setPreferredWidth((int)(totalWidth * 0.08));  // STT - 8%
        table.getColumnModel().getColumn(1).setPreferredWidth((int)(totalWidth * 0.25));  // TÊN PLAYER - 25%
        table.getColumnModel().getColumn(2).setPreferredWidth((int)(totalWidth * 0.17));  // TỈ LỆ THẮNG - 17%
        table.getColumnModel().getColumn(3).setPreferredWidth((int)(totalWidth * 0.17));  // SỐ TRẬN THẮNG - 17%
        table.getColumnModel().getColumn(4).setPreferredWidth((int)(totalWidth * 0.17));  // SỐ TRẬN ĐÃ ĐẤU - 17%
        table.getColumnModel().getColumn(5).setPreferredWidth((int)(totalWidth * 0.16));  // ĐIỂM - 16%

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Center align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Create a scroll pane for the table with custom styling
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
        scrollPane.getViewport().setBackground(new Color(240, 248, 255));

        // Thêm các cấu hình mới
        table.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(790, 400));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        backButton = createStyledButton("Quay lại", new Color(231, 76, 60));
        buttonPanel.add(backButton);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to frame
        add(titleBar, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
                
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 20, 20);
                
                FontMetrics fm = g2.getFontMetrics();
                Rectangle stringBounds = fm.getStringBounds(this.getText(), g2).getBounds();
                int textX = (getWidth() - stringBounds.width) / 2;
                int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
                
                g2.setColor(getForeground());
                g2.drawString(getText(), textX, textY);
                g2.dispose();
            }
        };
        
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));

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

        return button;
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

    public void updateRankings() {
        model.setRowCount(0);
        int cnt = 1;
        for (PlayerRanking player : playerRankings) {
            model.addRow(new Object[]{
                    cnt,
                    player.getPlayerName(),
                    String.format("%.2f%%", player.getPercentWin()),
                    player.getTotalWin(),
                    player.getTotalGame(),
                    player.getTotalPoint()
            });
            cnt++;
        }
    }

    public void addActionListener(ActionListener act) {
        backButton.addActionListener(act);
    }

    // Getters and setters

    public List<PlayerRanking> getPlayerRankings() {
        return playerRankings;
    }

    public void setPlayerRankings(List<PlayerRanking> playerRankings) {
        this.playerRankings = playerRankings;
        updateRankings();
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }
}