package view.stages;

import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.GameController;
import model.Accounts;
import model.Save;
import view.GameWindow;
import view.game.GameCam;

public class Game extends JPanel {
    private GameController controller;
    private JLabel levelId, steps;
    private GameCam gameCam;
    private JPanel gamePanel;

    public Game(int width, int height) {
        enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
        setSize(width, height);
        setLayout(null);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                controller.init();
            }
        });

        JButton saveBtn = new JButton("Save");
        saveBtn.setSize(100, 40);
        saveBtn.setLocation(600, 150);
        add(saveBtn);
        saveBtn.addActionListener(_ -> {
            if (Accounts.getCurrentUser() == null) {
                JOptionPane.showMessageDialog(null, "Please login first");
            } else {
                Accounts.getCurrentUser().save(controller.getMatrix().copy(), controller.getLevelId(),
                        controller.getSteps());
            }
            requestFocus();
        });

        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(100, 40);
        restartBtn.setLocation(600, 200);
        add(restartBtn);
        restartBtn.addActionListener(_ -> controller.init());

        JButton loadBtn = new JButton("Load");
        loadBtn.setSize(100, 40);
        loadBtn.setLocation(600, 250);
        add(loadBtn);
        loadBtn.addActionListener(_ -> {
            if (Accounts.getCurrentUser() == null) {
                JOptionPane.showMessageDialog(null, "Please login first");
            } else {
                Save save = Accounts.getCurrentUser().getSave();
                controller.init(save.getSavedMap(), save.getSavedLevelId(), save.getSavedSteps());
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setSize(100, 40);
        backBtn.setLocation(600, 300);
        add(backBtn);
        backBtn.addActionListener(_ -> {
            GameWindow.hideGame();
            GameWindow.showLevels();
        });

        JButton upBtn = new JButton("↑");
        upBtn.setSize(50, 50);
        upBtn.setLocation(625, 350);
        add(upBtn);
        upBtn.addActionListener(_ -> {
            doMoveUp();
            requestFocus();
        });

        JButton downBtn = new JButton("↓");
        downBtn.setSize(50, 50);
        downBtn.setLocation(625, 450);
        add(downBtn);
        downBtn.addActionListener(_ -> {
            doMoveDown();
            requestFocus();
        });

        JButton leftBtn = new JButton("←");
        leftBtn.setSize(50, 50);
        leftBtn.setLocation(575, 400);
        add(leftBtn);
        leftBtn.addActionListener(_ -> {
            doMoveLeft();
            requestFocus();
        });

        JButton rightBtn = new JButton("→");
        rightBtn.setSize(50, 50);
        rightBtn.setLocation(675, 400);
        add(rightBtn);
        rightBtn.addActionListener(_ -> {
            doMoveRight();
            requestFocus();
        });

        JButton autoBtn = new JButton("🤖");
        autoBtn.setSize(50, 50);
        autoBtn.setLocation(625, 400);
        add(autoBtn);
        autoBtn.addActionListener(_ -> {
            switch (controller.autoNextMove()) {
                case 0:
                    break;
                case -1:
                    JOptionPane.showMessageDialog(null, "No winning move!");
                    break;
                case -2:
                    JOptionPane.showMessageDialog(null, "Engine not loaded!");
                    break;
                default:
                    break;
            }
            requestFocus();
        });

        gamePanel = new JPanel();
        gamePanel.setSize(400, 400);
        gamePanel.setLocation(100, (getHeight() - 400) / 2);
        gamePanel.setLayout(null);
        add(gamePanel);
    }

    public void initGrids(int rows, int cols) {
        requestFocus();
        if (gameCam != null)
            gamePanel.remove(gameCam);
        gameCam = new GameCam(rows, cols);
        gamePanel.add(gameCam);
        gameCam.setLocation(-100, -100);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getID() == KeyEvent.KEY_PRESSED) {
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
            this.levelId.setLocation(600, 50);
            add(this.levelId);
        } else {
            this.levelId.setText("Level: " + levelId);
        }
    }

    public void setSteps(int steps) {
        if (this.steps == null) {
            this.steps = new JLabel("Steps: " + steps);
            this.steps.setSize(100, 40);
            this.steps.setLocation(600, 100);
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
        gameCam.paintGrid(i, j, type);
    }

    public void setBoxInGrid(int i, int j) {
        gameCam.setBoxInGrid(i, j);
    }

    public void setHeroInGrid(int i, int j) {
        gameCam.setHeroInGrid(i, j);
    }

    public void moveHero(int row, int col, int dRow, int dCol) {
        gameCam.moveHero(row, col, dRow, dCol);
    }

    public void moveHeroBox(int row, int col, int dRow, int dCol) {
        gameCam.moveHeroBox(row, col, dRow, dCol);
    }

    public void showVictory() {
        JOptionPane.showMessageDialog(null, "Victory!");
    }

    public void showDefeat() {
        JOptionPane.showMessageDialog(null, "Defeat!");
    }
}
