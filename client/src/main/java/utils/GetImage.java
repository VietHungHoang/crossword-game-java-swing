package utils;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class GetImage {

        public GetImage(){

        }

        public Icon get(String nameImg, int width, int height) {
        Image image = new ImageIcon(getClass().getResource(
                "/assets/img/" + nameImg)).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height,
                image.SCALE_SMOOTH));
        return icon;
    }
}
