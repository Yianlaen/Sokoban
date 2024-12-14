package controller;

import model.GameInfo;
import model.MapMatrix;

public class GameController {
    private MapMatrix startMatrix, matrix;
    private int levelId;
    private view.stages.Game panel;
    private int startSteps, steps;
    private boolean finished;
    private PathFinder pathFinder;
    private boolean booted;

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
        init(startMatrix, levelId, startSteps);
    }

    public void init(MapMatrix startMatrix, int levelId, int startSteps) {
        booted = false;
        finished = false;
        matrix = startMatrix.copy();
        steps = startSteps;
        panel.setLevelId(levelId);
        panel.setSteps(steps);
        panel.initGrids(matrix.getHeight(), matrix.getWidth());
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                panel.paintGrid(i, j, matrix.isWall(i, j) ? 1 : matrix.isGoal(i, j) ? 2 : 0);
                if (matrix.hasBox(i, j)) {
                    panel.setBoxInGrid(i, j);
                }
            }
        }
        panel.setHeroInGrid(matrix.getHeroX(), matrix.getHeroY());
        if (checkVictory()) {
            panel.showVictory();
        } else if (checkDefeat()) {
            panel.showDefeat();
        }
        pathFinder = new PathFinder(startMatrix);
        booted = true;
    }

    public void doMove(int dRow, int dCol) {
        int row = matrix.getHeroX(), col = matrix.getHeroY();
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
                if (matrix.isGoal(i, j) && !matrix.hasBox(i, j)) {
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

    public int autoNextMove() {
        GameInfo current = new GameInfo(matrix);
        if (!booted) {
            return -2;
        }
        switch (pathFinder.nextMove(current)) {
            case 0:
                doMove(-1, 0);
                break;
            case 1:
                doMove(1, 0);
                break;
            case 2:
                doMove(0, -1);
                break;
            case 3:
                doMove(0, 1);
                break;
            default:
                return -1;
        }
        return 0;
    }
}
