package controller;

import model.MapMatrix;

public class GameController {
    private MapMatrix startMatrix, matrix;
    private int levelId;
    private view.stages.Game panel;
    private int startSteps, steps;

    public GameController() {
    }

    public void setStartMatrix(MapMatrix matrix) {
        startMatrix = matrix.copy();
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
    }

    public void doMove(int dRow, int dCol) {
        int row = matrix.getPlayerRow(), col = matrix.getPlayerCol();
        if (!matrix.inside(row + dRow, col + dCol)) {
            return;
        }
    }
}
