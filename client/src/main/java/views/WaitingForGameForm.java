package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingForGameForm extends JFrame {
    private JButton btnCancel, btnConfirm, btnDecline;
    private JLabel lblWaitingTime;

    public WaitingForGameForm() {
        setTitle("Đang chờ đối thủ...");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        lblWaitingTime = new JLabel("Đã chờ: 0 phút 0 giây", JLabel.CENTER);
        panel.add(lblWaitingTime, BorderLayout.CENTER);

        btnCancel = new JButton("Hủy tìm trận");
        btnCancel.setPreferredSize(new Dimension(100, 30));
        panel.add(btnCancel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
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
}
