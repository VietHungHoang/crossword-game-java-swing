package views;

import models.MatchHistory;
import models.Player;
import models.PlayerRanking;


import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.List;

public class MatchHistoryForm extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private JButton backButton;

    public MatchHistoryForm() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Lịch sử Đấu - ALPHABET FIGHTING GAME");
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

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(33, 150, 243));
        titleBar.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel titleLabel = new JLabel("Lịch sử Đấu");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBar.add(titleLabel, BorderLayout.CENTER);

// Make the window draggable by adding MouseListener and MouseMotionListener to the title bar
        final Point[] initialClick = new Point[1];
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick[0] = e.getPoint();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int offsetX = e.getX() - initialClick[0].x;
                int offsetY = e.getY() - initialClick[0].y;
                Point newLocation = getLocationOnScreen();
                setLocation(newLocation.x + offsetX, newLocation.y + offsetY);
            }
        });

        // Table setup
        String[] columnNames = {"STT", "Đối thủ", "Thời Gian Bắt Đầu", "Thời Gian Kết Thúc", "Loại Trận", " "};
        model = new DefaultTableModel(columnNames, 0);

        updateMatchHistorys(MatchHistory.generateMockData());

        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        int totalWidth = 780;
        table.getColumnModel().getColumn(0).setPreferredWidth((int)(totalWidth * 0.08));  // STT - 8%
        table.getColumnModel().getColumn(1).setPreferredWidth((int)(totalWidth * 0.25)); // VS Player - 25%
        table.getColumnModel().getColumn(2).setPreferredWidth((int)(totalWidth * 0.2));  // Thời Gian Bắt Đầu - 20%
        table.getColumnModel().getColumn(3).setPreferredWidth((int)(totalWidth * 0.2));  // Thời Gian Kết Thúc - 20%
        table.getColumnModel().getColumn(4).setPreferredWidth((int)(totalWidth * 0.15)); // Loại Trận - 15%
        table.getColumnModel().getColumn(5).setPreferredWidth((int)(totalWidth * 0.12)); // Winner - 12%

        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Center align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(4).setCellRenderer(new TypeRenderer());
        table.getColumnModel().getColumn(5).setCellRenderer(new WinnerRenderer());
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // Create a scroll pane for the table with custom styling
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
        scrollPane.getViewport().setBackground(new Color(240, 248, 255));

        table.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(790, 400));

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

        return button;
    }
    public void updateMatchHistorys(List<MatchHistory> matchHistoryList) {
        model.setRowCount(0);
        int cnt = 1;
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

        for (MatchHistory matchHistory : matchHistoryList) {
            // Format startDate and endDate
            String formattedStartDate = dateFormat.format(matchHistory.getStartDate());
            String formattedEndDate = dateFormat.format(matchHistory.getEndDate());
            model.addRow(new Object[]{
                    cnt,
                    matchHistory.getOpponent(),
                    formattedStartDate,
                    formattedEndDate,
                    matchHistory.getType(),
                    matchHistory.getStatus()
            });
            cnt++;
        }
    }

    private class TypeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

            // Switch case for different match types
            switch (value != null ? value.toString() : "") {
                case "Ranked Match":
                    setForeground(Color.ORANGE); // Color for Ranked Match
                    break;
                case "Friend Match":
                    setForeground(new Color(135, 206, 250)); // Light blue for Friend Match
                    break;
                default:
                    setForeground(Color.WHITE); // Default color for other values
                    break;
            }

            return this;
        }
    }


    // Custom renderer for "Winner" column
    // Custom renderer for "Winner" column
    private class WinnerRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

            // Switch case for different outcomes
            switch (value != null ? value.toString() : "") {
                case "LOST":
                    setForeground(Color.RED); // Color for opponent lost
                    break;
                case "DRAW":
                    setForeground(Color.ORANGE); // Color for draw
                    break;
                default:
                    setForeground(Color.GREEN); // Color for winner
                    break;
            }
            return this;
        }
    }


    public void addActionListener(ActionListener act) {

        backButton.addActionListener(act);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getBackButton() {
        return backButton;
    }
    public static void  main(String args[]){
        new MatchHistoryForm();
    }

}
