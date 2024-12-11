package controller;

import model.MapMatrix;

public class GameController {
    private MapMatrix startMatrix, matrix;
    private int levelId;
    private view.stages.Game panel;
    private int startSteps, steps;
    private boolean finished;

    public GameController() {
    }

    public MapMatrix getMatrix() {
        return matrix;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getSteps() {
        return steps;
    }

    public void setStartMatrix(MapMatrix matrix) {
        startMatrix = matrix;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public void setPanel(view.stages.Game panel) {
        this.panel = panel;
    }

    public void setStartSteps(int steps) {
        this.startSteps = steps;
    }

    public void init() {
        finished = false;
        matrix = startMatrix.copy();
        steps = startSteps;
        panel.setLevelId(levelId);
        panel.setSteps(steps);
        panel.initGrids(matrix.getHeight(), matrix.getWidth());
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                panel.paintGrid(i, j, matrix.isWall(i, j) ? 1 : matrix.isDestination(i, j) ? 2 : 0);
                if (matrix.hasBox(i, j)) {
                    panel.setBoxInGrid(i, j);
                }
            }
        }
        panel.setHeroInGrid(matrix.getPlayerRow(), matrix.getPlayerCol());
        if (checkVictory()) {
            panel.showVictory();
        } else if (checkDefeat()) {
            panel.showDefeat();
        }
    }

    public void init(MapMatrix startMatrix, int levelId, int startSteps) {
        finished = false;
        matrix = startMatrix.copy();
        steps = startSteps;
        panel.setLevelId(levelId);
        panel.setSteps(steps);
        panel.initGrids(matrix.getHeight(), matrix.getWidth());
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                panel.paintGrid(i, j, matrix.isWall(i, j) ? 1 : matrix.isDestination(i, j) ? 2 : 0);
                if (matrix.hasBox(i, j)) {
                    panel.setBoxInGrid(i, j);
                }
            }
        }
        panel.setHeroInGrid(matrix.getPlayerRow(), matrix.getPlayerCol());
        if (checkVictory()) {
            panel.showVictory();
        } else if (checkDefeat()) {
            panel.showDefeat();
        }
    }

    public void doMove(int dRow, int dCol) {
        int row = matrix.getPlayerRow(), col = matrix.getPlayerCol();
        if (!matrix.inside(row + dRow, col + dCol)) {
            return;
        }
        if (matrix.isWall(row + dRow, col + dCol)) {
            return;
        }
        panel.setSteps(++steps);
        if (matrix.hasBox(row + dRow, col + dCol)) {
            if (!matrix.inside(row + 2 * dRow, col + 2 * dCol)) {
                return;
            }
            if (matrix.isBlocked(row + 2 * dRow, col + 2 * dCol)) {
                return;
            }
            matrix.move(row + dRow, col + dCol, dRow, dCol);
            matrix.move(row, col, dRow, dCol);
            panel.moveHeroBox(row, col, dRow, dCol);
        } else {
            matrix.move(row, col, dRow, dCol);
            panel.moveHero(row, col, dRow, dCol);
        }
        if (checkVictory()) {
            panel.showVictory();
        } else if (checkDefeat()) {
            panel.showDefeat();
        }
    }

    public boolean checkVictory() {
        if (finished)
            return false;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                if (matrix.isDestination(i, j) && !matrix.hasBox(i, j)) {
                    return false;
                }
            }
        }
        finished = true;
        return true;
    }

    public boolean checkDefeat() {
        if (finished)
            return false;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                if (matrix.hasBox(i, j)) {
                    if (!(matrix.isBlocked(i - 1, j) && matrix.isBlocked(i, j - 1) ||
                            matrix.isBlocked(i - 1, j) && matrix.isBlocked(i, j + 1) ||
                            matrix.isBlocked(i + 1, j) && matrix.isBlocked(i, j - 1) ||
                            matrix.isBlocked(i + 1, j) && matrix.isBlocked(i, j + 1))) {
                        return false;
                    }
                }
            }
        }
        finished = true;
        return true;
    }
}
