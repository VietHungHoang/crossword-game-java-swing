package views;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
//Khởi tạo 2 cái label để lưu tên người chơi

public class GameForm extends JFrame {
  private JLabel labelPlayer1;
  private JLabel labelPlayer2;
    public GameForm(){
        setTitle("Game");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        labelPlayer1 = new JLabel();
        labelPlayer2 = new JLabel();

        setVisible(true);

    }

    public void showMessage(String msg){

    }
    public void addActionListener(ActionListener act){

    }

    public static void main(String[] args) {
        new GameForm();
    }
    public JLabel getLabelPlayer1(){
        return labelPlayer1;
    }
    public JLabel getLabelPlayer2(){
        return labelPlayer2;
    }
    public void setLabelPlayer1(JLabel labelPlayer1){
        this.labelPlayer1 = labelPlayer1;
    }
    public void setLabelPlayer2(JLabel labelPlayer2){
        this.labelPlayer2 = labelPlayer2;
    }
}