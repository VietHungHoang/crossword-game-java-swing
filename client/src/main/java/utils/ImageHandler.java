package utils;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class ImageHandler {
    // Change the visibility to public
    public static ImageIcon createImageIcon(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.err.println("File không tồn tại: " + path);
                return null;
            }
        
            Image image = new ImageIcon(path).getImage();
        
            if (image.getWidth(null) == -1) {
                System.err.println("Không thể tải hình ảnh: " + path);
                return null;
            }
        
            Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Lỗi khi tải hình ảnh: " + path);
            e.printStackTrace();
            return null;
        }
    }
}