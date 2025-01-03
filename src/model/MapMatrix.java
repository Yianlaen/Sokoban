package model;

public class MapMatrix implements java.io.Serializable {
    private int[][] matrix;
    private int heroX, heroY;

    public MapMatrix(int[][] matrix) {
        this.matrix = matrix;
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid map");
        }
    }

    public MapMatrix(MapMatrix map, GameInfo gameInfo) {
        matrix = new int[map.getHeight()][map.getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                matrix[i][j] = map.matrix[i][j]&3;
            }
        }
        heroX = gameInfo.getHeroX();
        heroY = gameInfo.getHeroY();
        for (int i = 0; i < gameInfo.boxCnt(); i++) {
            int x = gameInfo.getBoxX()[i];
            int y = gameInfo.getBoxY()[i];
            matrix[x][y] |= 4;
        }
        matrix[heroX][heroY] |= 8;
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid map");
        }
    }

    public int getWidth() {
        return matrix[0].length;
    }

    public int getHeight() {
        return matrix.length;
    }

    public int getHeroX() {
        return heroX;
    }

    public int getHeroY() {
        return heroY;
    }

    public int setHeroX(int row) {
        return heroX = row;
    }

    public int setHeroY(int col) {
        return heroY = col;
    }

    public boolean inside(int row, int col) {
        return row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length;
    }

    public boolean isWall(int row, int col) {
        if (!inside(row, col)) {
            return true;
        }
        return (matrix[row][col] & 1) > 0;
    }

    public boolean isGoal(int row, int col) {
        if (!inside(row, col)) {
            return false;
        }
        return (matrix[row][col] & 2) > 0;
    }

    public boolean hasBox(int row, int col) {
        if (!inside(row, col)) {
            return false;
        }
        return (matrix[row][col] & 4) > 0;
    }

    public boolean hasHero(int row, int col) {
        if (!inside(row, col)) {
            return false;
        }
        return (matrix[row][col] & 8) > 0;
    }

    public boolean isBlocked(int row, int col) {
        return isWall(row, col) || hasBox(row, col);
    }

    public boolean isValid() {
        int heroCnt = 0, goalCnt = 0, boxCnt = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int cell = matrix[i][j];
                if ((cell & 1) > 0 && cell != 1 || (cell & 12) == 12) {
                    return false;
                }
                if ((cell & 8) > 0) {
                    heroCnt++;
                    heroX = i;
                    heroY = j;
                }
                if ((cell & 4) > 0) {
                    boxCnt++;
                }
                if ((cell & 2) > 0) {
                    goalCnt++;
                }
            }
        }
        return heroCnt == 1 && goalCnt == boxCnt;
    }

    public MapMatrix copy() {
        int[][] a = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                a[i][j] = matrix[i][j];
            }
        }
        return new MapMatrix(a);
    }

    public void move(int row, int col, int dRow, int dCol) {
        if (!inside(row, col) || !inside(row + dRow, col + dCol)) {
            throw new IllegalArgumentException("Invalid location");
        }
        if (isWall(row + dRow, col + dCol)) {
            throw new IllegalArgumentException("Blocked by wall");
        }
        if (hasBox(row + dRow, col + dCol)) {
            throw new IllegalArgumentException("Blocked by box");
        }
        if (hasHero(row + dRow, col + dCol)) {
            throw new IllegalArgumentException("Blocked by hero");
        }
        if (isWall(row, col)) {
            throw new IllegalArgumentException("Cannot move wall");
        }
        if (heroX == row && heroY == col) {
            heroX += dRow;
            heroY += dCol;
        }
        int bits = matrix[row][col] & 12;
        matrix[row][col] ^= bits;
        matrix[row += dRow][col += dCol] |= bits;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                sb.append("" + matrix[i][j]);
                if (j < matrix[0].length - 1) {
                    sb.append(" ");
                }
            }
            if (i < matrix.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public int[][] getRawMatrix() {
        return matrix;
    }
}
