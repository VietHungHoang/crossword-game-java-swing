package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.ClientController;
import models.PlayerStatus;
import utils.RoundedBorder;
public class ListPlayerForm extends JFrame {
    private JTextField searchField;
    private JTable playerTable;
    private DefaultTableModel tableModel;
    private JButton searchButton, makeFriendButton, backButton;
    private Point initialClick;
    public ListPlayerForm() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Tìm kiếm bạn bè - ALPHABET FIGHTING GAME");
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
        JLabel titleLabel = new JLabel("TÌM KIẾM BẠN BÈ");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBar.add(titleLabel, BorderLayout.CENTER);
        titleBar.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
              initialClick = e.getPoint();
          }
      });
  
      titleBar.addMouseMotionListener(new MouseAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
              int thisX = getLocation().x;
              int thisY = getLocation().y;
  
              int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
              int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);
  
              int X = thisX + xMoved;
              int Y = thisY + yMoved;
              setLocation(X, Y);
          }
      });
  
        /// ... existing code ...

        // Search panel - more compact
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setOpaque(false);
        
        // Thêm padding phía trên và dưới
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        searchField = createStyledTextField(20);
        // Điều chỉnh kích thước của searchField
        searchField.setPreferredSize(new Dimension(400, 35));
        searchField.setMinimumSize(new Dimension(200, 35));
        
        searchButton = createStyledButton("Tìm kiếm", new Color(52, 152, 219));
        searchButton.setPreferredSize(new Dimension(100, 35));
        
        // Thêm rigid area để giữ khoảng cách cố định
        searchPanel.add(Box.createHorizontalStrut(20));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(20));

        // Table setup
        String[] columnNames = {"Tên người chơi", "Trạng thái", "Bạn bè"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        playerTable = new JTable(tableModel);
        playerTable.setDefaultRenderer(Object.class, new PlayerTableCellRenderer());
        playerTable.setRowHeight(30);
        playerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        playerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerTable.setFillsViewportHeight(true);

        // Style table header
        JTableHeader header = playerTable.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Set column widths
        playerTable.getColumnModel().getColumn(0).setPreferredWidth(400);
        playerTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        playerTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        playerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Create a fixed-height scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(playerTable);
        scrollPane.setPreferredSize(new Dimension(750, 350));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scrollPane.getViewport().setBackground(new Color(240, 248, 255));

        // Button panel with fixed height
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setPreferredSize(new Dimension(getWidth(), 80));
        buttonPanel.setOpaque(false);
        
        makeFriendButton = createStyledButton("Kết bạn", new Color(46, 204, 113));
        backButton = createStyledButton("Trở lại", new Color(231, 76, 60));
        
        buttonPanel.add(makeFriendButton);
        buttonPanel.add(backButton);

        // Main content panel with fixed heights
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        contentPanel.add(searchPanel);
        contentPanel.add(scrollPane);
        contentPanel.add(buttonPanel);

        // Add components to frame
        add(titleBar, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JTextField createStyledTextField(int columns) {
      JTextField textField = new JTextField(columns);
      textField.setFont(new Font("Arial", Font.PLAIN, 14));
      textField.setBackground(Color.WHITE);
      textField.setForeground(Color.BLACK);
      textField.setOpaque(true);
      
      // Tăng padding lên một chút và giảm độ dày của border
      textField.setBorder(BorderFactory.createCompoundBorder(
          new RoundedBorder(4, new Color(180, 180, 180)), // Giảm độ bo tròn từ 15 xuống 8
          BorderFactory.createEmptyBorder(1, 4, 1, 4)  // Tăng padding để text hiển thị rõ hơn
      ));
      
      // Đặt margin để text không bị dính sát vào border
      textField.setMargin(new java.awt.Insets(2, 6, 2, 6));
      return textField;
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
        button.setPreferredSize(new Dimension(120, 40));

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

    private class PlayerTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            PlayerStatus player = ClientController.players.get(row);
            setHorizontalAlignment(SwingConstants.CENTER);
            if (column == 1) {
                switch (player.getStatus()) {
                    case "Online":
                        cell.setForeground(new Color(46, 204, 113));
                        break;
                    case "In Room":
                        cell.setForeground(new Color(52, 152, 219));
                        break;
                    case "Finding Game":
                        cell.setForeground(new Color(241, 196, 15));
                        break;
                    case "In Game":
                        cell.setForeground(new Color(231, 76, 60));
                        break;
                    default:
                        cell.setForeground(Color.BLACK);
                        break;
                }
            } else {
                cell.setForeground(Color.BLACK);
            }
            return cell;
        }
    }

    public void addActionListener(ActionListener act) {
        searchButton.addActionListener(act);
        makeFriendButton.addActionListener(act);
        backButton.addActionListener(act);
    }

    // Getters and setters
    public JTextField getSearchField() {
        return searchField;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public JTable getPlayerTable() {
        return playerTable;
    }

    public void setPlayerTable(JTable playerTable) {
        this.playerTable = playerTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    public JButton getMakeFriendButton() {
        return makeFriendButton;
    }

    public void setMakeFriendButton(JButton makeFriendButton) {
        this.makeFriendButton = makeFriendButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }
}