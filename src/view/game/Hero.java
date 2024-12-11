package view.game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Hero extends JComponent {
    private static Color color = new Color(87, 171, 220);

    public Hero(int size) {
        this.setSize(size, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, getWidth(), getHeight());
        g.setColor(color);
        g.fillOval(1, 1, getWidth() - 2, getHeight() - 2);
    }
}
