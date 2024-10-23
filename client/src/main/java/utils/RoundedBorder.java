package utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private int radius;
    private Color color;  // Add color field

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;  // Initialize the color
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);  // Set the border color
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}