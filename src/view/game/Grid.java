package view.game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class Grid extends JComponent {
    static Color color = new Color(246, 246, 229);
    private int type;

    public Grid(int gridSize, int type) {
        this.setSize(gridSize, gridSize);
        setType(type);
    }

    public void setType(int type) {
        this.type = type;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        Color borderColor = color;
        switch (type) {
            case 0:
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                break;
            case 1:
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
                borderColor = Color.DARK_GRAY;
                break;
            case 2:
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.GREEN);
                int[] xPoints = { getWidth() / 2, getWidth(), getWidth() / 2, 0 };
                int[] yPoints = { 0, getHeight() / 2, getHeight(), getHeight() / 2 };
                g.fillPolygon(xPoints, yPoints, 4);
                g.setColor(Color.BLACK);
                g.drawPolygon(xPoints, yPoints, 4);
                break;
        }
        Border border = BorderFactory.createLineBorder(borderColor, 1);
        this.setBorder(border);
    }
}
