package model;

import java.util.ArrayList;

public class GameInfo {
    private ArrayList<Integer> boxX, boxY;
    private int heroX, heroY;
    private MapMatrix matrix;

    public GameInfo(GameInfo game) {
        this.matrix = game.matrix;
        this.heroX = game.heroX;
        this.heroY = game.heroY;
        this.boxX = new ArrayList<Integer>(game.boxX);
        this.boxY = new ArrayList<Integer>(game.boxY);
    }

    public GameInfo() {
        this.boxX = new ArrayList<Integer>();
        this.boxY = new ArrayList<Integer>();
    }

    public GameInfo(MapMatrix matrix) {
        this.matrix = matrix;
        boxX = new ArrayList<Integer>();
        boxY = new ArrayList<Integer>();
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                if (matrix.hasBox(i, j)) {
                    boxX.add(i);
                    boxY.add(j);
                }
                if (matrix.hasHero(i, j)) {
                    heroX = i;
                    heroY = j;
                }
            }
        }
    }

    public void bubble(int p) {
        while (p > 0
                && (boxX.get(p) < boxX.get(p - 1) || boxX.get(p) == boxX.get(p - 1) && boxY.get(p) < boxY.get(p - 1))) {
            int temp = boxX.get(p);
            boxX.set(p, boxX.get(p - 1));
            boxX.set(p - 1, temp);
            temp = boxY.get(p);
            boxY.set(p, boxY.get(p - 1));
            boxY.set(p - 1, temp);
            p--;
        }
        while (p + 1 < boxCnt()
                && (boxX.get(p) > boxX.get(p + 1) || boxX.get(p) == boxX.get(p + 1) && boxY.get(p) > boxY.get(p + 1))) {
            int temp = boxX.get(p);
            boxX.set(p, boxX.get(p + 1));
            boxX.set(p + 1, temp);
            temp = boxY.get(p);
            boxY.set(p, boxY.get(p + 1));
            boxY.set(p + 1, temp);
            p++;
        }
    }

    public int getHeroX() {
        return heroX;
    }

    public int getHeroY() {
        return heroY;
    }

    public ArrayList<Integer> getBoxX() {
        return boxX;
    }

    public ArrayList<Integer> getBoxY() {
        return boxY;
    }

    public GameInfo moveUp() {
        if (matrix.isWall(heroX - 1, heroY)) {
            return null;
        }
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX.get(i) == heroX - 1 && boxY.get(i) == heroY) {
                if (matrix.isWall(heroX - 2, heroY)) {
                    return null;
                }
                for (int j = 0; j < boxCnt(); j++) {
                    if (boxX.get(j) == heroX - 2 && boxY.get(j) == heroY) {
                        return null;
                    }
                }
                GameInfo newGame = new GameInfo(this);
                newGame.heroX--;
                newGame.boxX.set(i, newGame.boxX.get(i) - 1);
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
            if (boxX.get(i) == heroX + 1 && boxY.get(i) == heroY) {
                if (matrix.isWall(heroX + 2, heroY)) {
                    return null;
                }
                for (int j = 0; j < boxCnt(); j++) {
                    if (boxX.get(j) == heroX + 2 && boxY.get(j) == heroY) {
                        return null;
                    }
                }
                GameInfo newGame = new GameInfo(this);
                newGame.heroX++;
                newGame.boxX.set(i, newGame.boxX.get(i) + 1);
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
            if (boxX.get(i) == heroX && boxY.get(i) == heroY - 1) {
                if (matrix.isWall(heroX, heroY - 2)) {
                    return null;
                }
                for (int j = 0; j < boxCnt(); j++) {
                    if (boxX.get(j) == heroX && boxY.get(j) == heroY - 2) {
                        return null;
                    }
                }
                GameInfo newGame = new GameInfo(this);
                newGame.heroY--;
                newGame.boxY.set(i, newGame.boxY.get(i) - 1);
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
            if (boxX.get(i) == heroX && boxY.get(i) == heroY + 1) {
                if (matrix.isWall(heroX, heroY + 2)) {
                    return null;
                }
                for (int j = 0; j < boxCnt(); j++) {
                    if (boxX.get(j) == heroX && boxY.get(j) == heroY + 2) {
                        return null;
                    }
                }
                GameInfo newGame = new GameInfo(this);
                newGame.heroY++;
                newGame.boxY.set(i, newGame.boxY.get(i) + 1);
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
            if (boxX.get(i) == heroX + 1 && boxY.get(i) == heroY) {
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
            if (boxX.get(i) == heroX - 1 && boxY.get(i) == heroY) {
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
            if (boxX.get(i) == heroX && boxY.get(i) == heroY + 1) {
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
            if (boxX.get(i) == heroX && boxY.get(i) == heroY - 1) {
                return null;
            }
        }
        GameInfo newGame = new GameInfo(this);
        newGame.heroY--;
        return newGame;
    }

    public int boxCnt() {
        return boxX.size();
    }

    public GameInfo reverseUpBox() {
        if (matrix.isWall(heroX + 1, heroY)) {
            return null;
        }
        int p = -1;
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX.get(i) == heroX + 1 && boxY.get(i) == heroY) {
                return null;
            }
            if (boxX.get(i) == heroX - 1 && boxY.get(i) == heroY) {
                p = i;
            }
        }
        if (p == -1) {
            return null;
        }
        GameInfo newGame = new GameInfo(this);
        newGame.boxX.set(p, newGame.boxX.get(p) + 1);
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
            if (boxX.get(i) == heroX - 1 && boxY.get(i) == heroY) {
                return null;
            }
            if (boxX.get(i) == heroX + 1 && boxY.get(i) == heroY) {
                p = i;
            }
        }
        if (p == -1) {
            return null;
        }
        GameInfo newGame = new GameInfo(this);
        newGame.boxX.set(p, newGame.boxX.get(p) - 1);
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
            if (boxX.get(i) == heroX && boxY.get(i) == heroY + 1) {
                return null;
            }
            if (boxX.get(i) == heroX && boxY.get(i) == heroY - 1) {
                p = i;
            }
        }
        if (p == -1) {
            return null;
        }
        GameInfo newGame = new GameInfo(this);
        newGame.boxY.set(p, newGame.boxY.get(p) + 1);
        newGame.heroY++;
        return newGame;
    }

    public GameInfo reverseRightBox() {
        if (matrix.isWall(heroX, heroY - 1)) {
            return null;
        }
        int p = -1;
        for (int i = 0; i < boxCnt(); i++) {
            if (boxX.get(i) == heroX && boxY.get(i) == heroY - 1) {
                return null;
            }
            if (boxX.get(i) == heroX && boxY.get(i) == heroY + 1) {
                p = i;
            }
        }
        if (p == -1) {
            return null;
        }
        GameInfo newGame = new GameInfo(this);
        newGame.boxY.set(p, newGame.boxY.get(p) - 1);
        newGame.heroY--;
        return newGame;
    }

    public static ArrayList<GameInfo> generateWins(MapMatrix matrix) {
        GameInfo base = new GameInfo();
        base.matrix = matrix;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                if (matrix.isGoal(i, j)) {
                    base.boxX.add(i);
                    base.boxY.add(j);
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
            result = result * 10 + boxX.get(i);
            result = result * 10 + boxY.get(i);
        }
        return result;
    }

    public boolean equals(GameInfo game) {
        return hashCode() == game.hashCode();
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < boxCnt(); i++) {
            result += boxX.get(i) + " " + boxY.get(i) + "\n";
        }
        result += heroX + " " + heroY + "\n";
        result += hashCode();
        return result;
    }
}
