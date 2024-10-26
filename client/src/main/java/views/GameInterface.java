package views;

import javax.swing.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;  

public class GameInterface {
	public static void main(String[] args) {  
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new InviteRoomFrame();
			}
		});
    }  
}
