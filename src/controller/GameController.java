package controller;

import model.MapMatrix;

public class GameController {
    private MapMatrix startMatrix, currentMatrix;
    private int levelId;

    public GameController() {
    }

    public void setStartMatrix(MapMatrix matrix) {
        startMatrix = matrix.copy();
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }
}
