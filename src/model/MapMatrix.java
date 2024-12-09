package model;

public class MapMatrix implements java.io.Serializable {
    private int[][] matrix;
    private int playerRow, playerCol;

    public MapMatrix(int[][] matrix) {
        this.matrix = matrix;
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

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
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

    public boolean isDestination(int row, int col) {
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

    public boolean hasPlayer(int row, int col) {
        if (!inside(row, col)) {
            return false;
        }
        return (matrix[row][col] & 8) > 0;
    }

    public boolean isValid() {
        int playerCnt = 0, destinationCnt = 0, boxCnt = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int cell = matrix[i][j];
                if ((cell & 1) > 0 && cell != 1 || (cell & 12) == 12) {
                    return false;
                }
                if ((cell & 8) > 0) {
                    playerCnt++;
                    playerRow = i;
                    playerCol = j;
                }
                if ((cell & 4) > 0) {
                    boxCnt++;
                }
                if ((cell & 2) > 0) {
                    destinationCnt++;
                }
            }
        }
        return playerCnt == 1 && destinationCnt == boxCnt;
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
        if (hasPlayer(row + dRow, col + dCol)) {
            throw new IllegalArgumentException("Blocked by player");
        }
        if (isWall(row, col)) {
            throw new IllegalArgumentException("Cannot move wall");
        }
        if (playerRow == row && playerCol == col) {
            playerRow += dRow;
            playerCol += dCol;
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
}
