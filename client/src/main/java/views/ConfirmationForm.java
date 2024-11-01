

package views;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfirmationForm extends JFrame {
    private JButton btnConfirm;
    private JButton btnDecline;
    private JLabel lblConfirmationMessage;

    public ConfirmationForm() {
        setTitle("Xác nhận vào trận");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        lblConfirmationMessage = new JLabel("", JLabel.CENTER);
        panel.add(lblConfirmationMessage, BorderLayout.CENTER);

        // Tạo nút xác nhận và hủy
        btnConfirm = new JButton("Vào trận");
        btnDecline = new JButton("Hủy");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnDecline);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public void addActionListener(ActionListener act) {
        btnConfirm.addActionListener(act);
        btnDecline.addActionListener(act);
    }

    public JButton getBtnConfirm() {
        return btnConfirm;
    }

    public JButton getBtnDecline() {
        return btnDecline;
    }

    public void showConfirmationMessage(String message) {
        lblConfirmationMessage.setText(message);
    }
}
