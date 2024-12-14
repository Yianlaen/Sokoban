package controller;

import java.util.ArrayList;

import model.MapMatrix;
import model.RandGen;

public class MapGenerator {
    private int height, width;
    private int[][] map;
    private PathFinder path;
    private MapMatrix matrix;

    public boolean generateGrids() {
        int goalCnt = 0;
        ArrayList<Integer> heroXs = new ArrayList<Integer>(), heroYs = new ArrayList<Integer>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = i == 0 || j == 0 || i == height - 1 || j == width - 1 ? 1 : 0;
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i != 0 && j != 0 && i != height - 1 && j != width - 1 && RandGen.nextInt(height * width) < 5
                        && goalCnt < 3) {
                    map[i][j] = 6;
                    goalCnt++;
                    if (map[i - 1][j] == 0) {
                        heroXs.add(i - 1);
                        heroYs.add(j);
                    }
                    if (map[i + 1][j] == 0) {
                        heroXs.add(i + 1);
                        heroYs.add(j);
                    }
                    if (map[i][j - 1] == 0) {
                        heroXs.add(i);
                        heroYs.add(j - 1);
                    }
                    if (map[i][j + 1] == 0) {
                        heroXs.add(i);
                        heroYs.add(j + 1);
                    }
                }
            }
        }
        if (heroXs.isEmpty()) {
            return false;
        }
        int p = RandGen.nextInt(heroXs.size()), heroX = heroXs.get(p), heroY = heroYs.get(p);
        map[heroX][heroY] = 8;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[i][j] == 0 && RandGen.nextInt(10000) < 3000) {
                    map[i][j] = 1;
                }
            }
        }
        return true;
    }

    public MapGenerator(int minimumSteps) {
        int counter = 1;
        do {
            height = RandGen.nextInt(6) + 5;
            width = RandGen.nextInt(6) + 5;
            map = new int[height][width];
            while (!generateGrids())
                ++counter;
            matrix = new MapMatrix(map);
            path = new PathFinder(matrix);
        } while (path.distanceStart() <= minimumSteps || path.distanceStart() >= minimumSteps + 30);
        System.out.println("Map generated in " + counter + " tries.");
    }

    public MapMatrix generateStart() {
        return path.generateStart();
    }
}
