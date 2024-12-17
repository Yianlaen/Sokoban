package model;

import java.util.ArrayList;

public class GameInfo {
    private int[] boxX, boxY;
    private int heroX, heroY;
    private MapMatrix matrix;

    public GameInfo(GameInfo game) {
        this.matrix = game.matrix;
        this.heroX = game.heroX;
        this.heroY = game.heroY;
        this.boxX = game.boxX.clone();
        this.boxY = game.boxY.clone();
    }

    public GameInfo(MapMatrix matrix) {
        this.matrix = matrix;
        int cnt = 0;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                cnt += matrix.hasBox(i, j) ? 1 : 0;
            }
        }
        boxX = new int[cnt];
        boxY = new int[cnt];
        cnt = 0;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                if (matrix.hasBox(i, j)) {
                    boxX[cnt] = i;
                    boxY[cnt++] = j;
                }
                if (matrix.hasHero(i, j)) {
                    heroX = i;
                    heroY = j;
                }
            }
        }
    }

    public GameInfo() {
    }

    public void bubble(int p) {
        while (p > 0
                && (boxX[p] < boxX[p - 1] || boxX[p] == boxX[p - 1] && boxY[p] < boxY[p - 1])) {
            int temp = boxX[p];
            boxX[p] = boxX[p - 1];
            boxX[p - 1] = temp;
            temp = boxY[p];
            boxY[p] = boxY[p - 1];
            boxY[p - 1] = temp;
            p--;
        }
        while (p + 1 < boxCnt()
                && (boxX[p] > boxX[p + 1] || boxX[p] == boxX[p + 1] && boxY[p] > boxY[p + 1])) {
            int temp = boxX[p];
            boxX[p] = boxX[p + 1];
            boxX[p + 1] = temp;
            temp = boxY[p];
            boxY[p] = boxY[p + 1];
            boxY[p + 1] = temp;
            p++;
        }
    }

    public int getHeroX() {
        return heroX;
    }

    public int getHeroY() {
        return heroY;
    }

    public int[] getBoxX() {
        return boxX;
    }

    public int[] getBoxY() {
        return boxY;
    }

    public GameInfo moveUp() {
        if (matrix.isWall(heroX - 1, heroY)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX - 1 && boxY[i] == heroY) {
                if (matrix.isWall(heroX - 2, heroY)) {
                    return null;
                }
                for (int j = 0; j < boxCnt(); j++) {
                    if (boxX[j] == heroX - 2 && boxY[j] == heroY) {
                        return null;
                    }
                }
                GameInfo newGame = new GameInfo(this);
                newGame.heroX--;
                newGame.boxX[i] = newGame.boxX[i] - 1;
                newGame.bubble(i);
                return newGame;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroX--;
        return newGame;
    }

    public GameInfo moveDown() {
        if (matrix.isWall(heroX + 1, heroY)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX + 1 && boxY[i] == heroY) {
                if (matrix.isWall(heroX + 2, heroY)) {
                    return null;
                }
                for (int j = 0; j < boxCnt(); j++) {
                    if (boxX[j] == heroX + 2 && boxY[j] == heroY) {
                        return null;
                    }
                }
                GameInfo newGame = new GameInfo(this);
                newGame.heroX++;
                newGame.boxX[i] = newGame.boxX[i] + 1;
                newGame.bubble(i);
                return newGame;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroX++;
        return newGame;
    }

    public GameInfo moveLeft() {
        if (matrix.isWall(heroX, heroY - 1)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX && boxY[i] == heroY - 1) {
                if (matrix.isWall(heroX, heroY - 2)) {
                    return null;
                }
                for (int j = 0; j < boxCnt(); j++) {
                    if (boxX[j] == heroX && boxY[j] == heroY - 2) {
                        return null;
                    }
                }
                GameInfo newGame = new GameInfo(this);
                newGame.heroY--;
                newGame.boxY[i] = newGame.boxY[i] - 1;
                return newGame;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroY--;
        return newGame;
    }

    public GameInfo moveRight() {
        if (matrix.isWall(heroX, heroY + 1)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX && boxY[i] == heroY + 1) {
                if (matrix.isWall(heroX, heroY + 2)) {
                    return null;
                }
                for (int j = 0; j < boxCnt(); j++) {
                    if (boxX[j] == heroX && boxY[j] == heroY + 2) {
                        return null;
                    }
                }
                GameInfo newGame = new GameInfo(this);
                newGame.heroY++;
                newGame.boxY[i] = newGame.boxY[i] + 1;
                return newGame;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroY++;
        return newGame;
    }

    public GameInfo reverseUpHero() {
        if (matrix.isWall(heroX + 1, heroY)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX + 1 && boxY[i] == heroY) {
                return null;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroX++;
        return newGame;
    }

    public GameInfo reverseDownHero() {
        if (matrix.isWall(heroX - 1, heroY)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX - 1 && boxY[i] == heroY) {
                return null;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroX--;
        return newGame;
    }

    public GameInfo reverseLeftHero() {
        if (matrix.isWall(heroX, heroY + 1)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX && boxY[i] == heroY + 1) {
                return null;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroY++;
        return newGame;
    }

    public GameInfo reverseRightHero() {
        if (matrix.isWall(heroX, heroY - 1)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX && boxY[i] == heroY - 1) {
                return null;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroY--;
        return newGame;
    }

    public int boxCnt() {
        return boxX.length;
    }

    public GameInfo reverseUpBox() {
        if (matrix.isWall(heroX + 1, heroY)) {
            return null;
        }
        int p = -1;
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX + 1 && boxY[i] == heroY) {
                return null;
            }
            if (boxX[i] == heroX - 1 && boxY[i] == heroY) {
                p = i;
            }
        }
        if (p == -1) {
            return null;
        }
        GameInfo newGame = new GameInfo(this);
        newGame.boxX[p] = newGame.boxX[p] + 1;
        newGame.bubble(p);
        newGame.heroX++;
        return newGame;
    }

    public GameInfo reverseDownBox() {
        if (matrix.isWall(heroX - 1, heroY)) {
            return null;
        }
        int p = -1;
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX - 1 && boxY[i] == heroY) {
                return null;
            }
            if (boxX[i] == heroX + 1 && boxY[i] == heroY) {
                p = i;
            }
        }
        if (p == -1) {
            return null;
        }
        GameInfo newGame = new GameInfo(this);
        newGame.boxX[p] = newGame.boxX[p] - 1;
        newGame.bubble(p);
        newGame.heroX--;
        return newGame;
    }

    public GameInfo reverseLeftBox() {
        if (matrix.isWall(heroX, heroY + 1)) {
            return null;
        }
        int p = -1;
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX && boxY[i] == heroY + 1) {
                return null;
            }
            if (boxX[i] == heroX && boxY[i] == heroY - 1) {
                p = i;
            }
        }
        if (p == -1) {
            return null;
        }
        GameInfo newGame = new GameInfo(this);
        newGame.boxY[p] = newGame.boxY[p] + 1;
        newGame.heroY++;
        return newGame;
    }

    public GameInfo reverseRightBox() {
        if (matrix.isWall(heroX, heroY - 1)) {
            return null;
        }
        int p = -1;
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX[i] == heroX && boxY[i] == heroY - 1) {
                return null;
            }
            if (boxX[i] == heroX && boxY[i] == heroY + 1) {
                p = i;
            }
        }
        if (p == -1) {
            return null;
        }
        GameInfo newGame = new GameInfo(this);
        newGame.boxY[p] =  newGame.boxY[p] - 1;
        newGame.heroY--;
        return newGame;
    }

    public static ArrayList<GameInfo> generateWins(MapMatrix matrix) {
        GameInfo base = new GameInfo();
        int cnt = 0;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                cnt += matrix.isGoal(i, j) ? 1 : 0;
            }
        }
        base.matrix = matrix;
        base.boxX = new int[cnt];
        base.boxY = new int[cnt];
        cnt = 0;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                if (matrix.isGoal(i, j)) {
                    base.boxX[cnt] = i;
                    base.boxY[cnt++] = j;
                }
            }
        }
        ArrayList<GameInfo> wins = new ArrayList<GameInfo>();
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                if ((matrix.isGoal(i + 1, j) || matrix.isGoal(i - 1, j) || matrix.isGoal(i, j + 1)
                        || matrix.isGoal(i, j - 1)) && !matrix.isWall(i, j) && !matrix.isGoal(i, j)) {
                    GameInfo win = new GameInfo(base);
                    win.heroX = i;
                    win.heroY = j;
                    wins.add(win);
                }
            }
        }
        return wins;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = result * 10 + heroX;
        result = result * 10 + heroY;
        for (int i = 0; i < boxCnt(); i++) {
            result = result * 10 + boxX[i];
            result = result * 10 + boxY[i];
        }
        return result;
    }

    public boolean equals(GameInfo game) {
        return hashCode() == game.hashCode();
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < boxCnt(); i++) {
            result += boxX[i] + " " + boxY[i] + "\n";
        }
        result += heroX + " " + heroY + "\n";
        result += hashCode();
        return result;
    }
}
