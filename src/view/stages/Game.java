package view.stages;

import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameController;
import view.GameWindow;
import view.game.Box;
import view.game.Grid;
import view.game.Hero;

public class Game extends JPanel {
    private GameController controller;
    private Grid[][] grids;
    private Box[][] boxes;
    private Hero hero;
    private int size;
    private JLabel levelId, steps;

    public Game(int width, int height) {
        enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
        setSize(width, height);
        setLayout(null);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                controller.init();
                requestFocus();
            }
        });

        JButton saveBtn = new JButton("Save");
        saveBtn.setSize(100, 40);
        saveBtn.setLocation(600, 250);
        add(saveBtn);

        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(100, 40);
        restartBtn.setLocation(600, 300);
        add(restartBtn);

        JButton loadBtn = new JButton("Load");
        loadBtn.setSize(100, 40);
        loadBtn.setLocation(600, 350);
        add(loadBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setSize(100, 40);
        backBtn.setLocation(600, height - 110);
        add(backBtn);
        backBtn.addActionListener(_ -> {
            GameWindow.hideGame();
            GameWindow.showLevels();
        });
    }

    public void initGrids(int rows, int cols) {
        if (this.grids != null) {
            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[0].length; j++) {
                    if (grids[i][j] != null) {
                        remove(grids[i][j]);
                    }
                    if (boxes[i][j] != null) {
                        remove(boxes[i][j]);
                    }
                }
            }
        }
        if (hero != null) {
            remove(hero);
        }
        repaint();
        size = getWidth() / 2 / Math.max(rows, cols);
        grids = new Grid[rows][cols];
        boxes = new Box[rows][cols];
        hero = new Hero(size - 16);
        add(hero);
        setComponentZOrder(hero, 1);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            System.out.println("Key pressed: " + e.getKeyCode());
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> doMoveRight();
                case KeyEvent.VK_LEFT -> doMoveLeft();
                case KeyEvent.VK_UP -> doMoveUp();
                case KeyEvent.VK_DOWN -> doMoveDown();
            }
        }
    }

    public void setGameController(GameController controller) {
        this.controller = controller;
    }

    public void setLevelId(int levelId) {
        if (this.levelId == null) {
            this.levelId = new JLabel("Level: " + levelId);
            this.levelId.setSize(100, 40);
            this.levelId.setLocation(600, 150);
            add(this.levelId);
        } else {
            this.levelId.setText("Level: " + levelId);
        }
    }

    public void setSteps(int steps) {
        if (this.steps == null) {
            this.steps = new JLabel("Steps: " + steps);
            this.steps.setSize(100, 40);
            this.steps.setLocation(600, 200);
            add(this.steps);
        } else {
            this.steps.setText("Steps: " + steps);
        }
    }

    private void doMoveRight() {
        controller.doMove(0, 1);
    }

    private void doMoveLeft() {
        controller.doMove(0, -1);
    }

    private void doMoveUp() {
        controller.doMove(-1, 0);
    }

    private void doMoveDown() {
        controller.doMove(1, 0);
    }

    public void paintGrid(int i, int j, int type) {
        if (grids[i][j] == null) {
            grids[i][j] = new Grid(size, type);
            grids[i][j].setLocation(100 + j * size, (getHeight() - grids.length * size) / 2 + i * size);
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
        boxes[i][j].setLocation(100 + j * size + 5, (getHeight() - grids.length * size) / 2 + i * size + 5);
        add(boxes[i][j]);
        setComponentZOrder(boxes[i][j], 1);
    }

    public void setHeroInGrid(int i, int j) {
        hero.setLocation(100 + j * size + 8, (getHeight() - grids.length * size) / 2 + i * size + 8);
    }
}
