package views;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
public class WaitingForGameForm extends JFrame {
  private JButton btnCancel;
  //Tạo 1 giao diện Loading đơn giản
  public WaitingForGameForm() {
    JFrame frame = new JFrame("Loading");
    frame.setSize(300, 200);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    // Create button
    btnCancel = new JButton("Cancel");
    //Add thêm chiều dài và rộng vào
    btnCancel.setPreferredSize(new Dimension(100, 30));
    // Set visible
    setVisible(true);
  }

  public JButton getBtnCancel() {
    return btnCancel;
  }

  public void addActionListener(ActionListener act){
    btnCancel.addActionListener(act);
}

public void showMessage(String msg){
 
  }
}
