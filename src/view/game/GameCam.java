package view.game;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameCam extends JPanel {
    private Grid[][] grids;
    private Box[][] boxes;
    private Hero hero;
    private int size;

    public GameCam(int rows, int cols) {
        setSize(400, 400);
        setLayout(null);
        size = 400 / Math.max(rows, cols);
        grids = new Grid[rows][cols];
        boxes = new Box[rows][cols];
        hero = new Hero(size - 16);
        add(hero);
    }

    public void paintGrid(int i, int j, int type) {
        if (grids[i][j] == null) {
            grids[i][j] = new Grid(size, type);
            grids[i][j].setLocation(j * size, (getHeight() - grids.length * size) / 2 + i * size);
            add(grids[i][j]);
        } else {
            grids[i][j].setType(type);
        }
    }

    public void setBoxInGrid(int i, int j) {
        if (boxes[i][j] != null) {
            throw new RuntimeException("Box already exists in this grid");
        }
        boxes[i][j] = new Box(size - 10);
        boxes[i][j].setLocation(j * size + 5, (getHeight() - grids.length * size) / 2 + i * size + 5);
        add(boxes[i][j]);
        setComponentZOrder(boxes[i][j], 1);
    }

    public void setHeroInGrid(int i, int j) {
        setComponentZOrder(hero, 1);
        hero.setLocation(j * size + 8, (getHeight() - grids.length * size) / 2 + i * size + 8);
    }

    public void moveObj(JComponent obj, int dRow, int dCol) {
        final int[] count = { 0 };
        Timer timer = new Timer(15, e -> {
            if (count[0] < size) {
                int delta = Math.min(size - count[0], 15);
                obj.setLocation(obj.getX() + dCol * delta, obj.getY() + dRow * delta);
                count[0] += delta;
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    public void moveHero(int row, int col, int dRow, int dCol) {
        moveObj(hero, dRow, dCol);
    }

    public void moveHeroBox(int row, int col, int dRow, int dCol) {
        Box box = boxes[row + dRow][col + dCol];
        moveObj(hero, dRow, dCol);
        moveObj(box, dRow, dCol);
        boxes[row + 2 * dRow][col + 2 * dCol] = box;
        boxes[row + dRow][col + dCol] = null;
    }
}
