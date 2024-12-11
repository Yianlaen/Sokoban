package view.game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class Box extends JComponent {
    public Box(int size) {
        this.setSize(size, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
        Border border = BorderFactory.createLineBorder(Color.black, 1);
        this.setBorder(border);
    }
}
