package views;

import utils.GetImage;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WaitingForGameForm extends JFrame {
    private JButton btnCancel, btnConfirm, btnDecline;
    private JLabel lblWaitingTime;

    private Font timeFont = new Font("Dialog", Font.BOLD, 24);

    public WaitingForGameForm() {
        setTitle("Đang chờ đối thủ...");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        lblWaitingTime = new JLabel("00:00", JLabel.CENTER);
        lblWaitingTime.setFont(timeFont);
        lblWaitingTime.setBorder(new EmptyBorder(20, 10, 10, 10));
        panel.add(lblWaitingTime, BorderLayout.NORTH);

        GetImage getImage = new GetImage();
        JLabel gif = new JLabel(new ImageIcon(getClass().getResource(
                "/assets/img/ezgif.gif")));

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnCancel = new JButton("Hủy tìm trận");
        btnCancel.setPreferredSize(new Dimension(120, 30));
        panel.add(gif, BorderLayout.CENTER);
        btnPanel.add(btnCancel);
        btnPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(panel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public void addActionListener(ActionListener act) {
        btnCancel.addActionListener(act);
        if (btnConfirm != null) btnConfirm.addActionListener(act);
        if (btnDecline != null) btnDecline.addActionListener(act);
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JButton getBtnConfirm() {
        return btnConfirm;
    }

    public JButton getBtnDecline() {
        return btnDecline;
    }

    public void updateWaitingTime(String time) {
        lblWaitingTime.setText(time);
    }

    public void showConfirmationOptions() {
        btnConfirm = new JButton("Vào trận");
        btnDecline = new JButton("Hủy");
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnDecline);
        
        add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new WaitingForGameForm();
    }
}
