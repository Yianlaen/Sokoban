package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import model.GameInfo;
import model.MapMatrix;
import model.RandGen;

public class PathFinder {
    private MapMatrix matrix;
    private HashMap<Integer, Integer> distance = new HashMap<Integer, Integer>();
    private ArrayList<GameInfo> wins = new ArrayList<GameInfo>();
    private GameInfo start;

    public PathFinder(MapMatrix matrix) {
        this.matrix = matrix;
        wins = GameInfo.generateWins(matrix);
        Queue<GameInfo> queue = new LinkedList<GameInfo>();
        for (GameInfo win : wins) {
            queue.add(win);
            distance.put(win.hashCode(), 0);
        }
        start = wins.get(0).reverseUpHero();
        while (!queue.isEmpty()) {
            GameInfo current = queue.poll();
            int currentDistance = distance.get(current.hashCode());
            if (RandGen.nextInt(10000) < 5000) {
                start = current;
            }
            GameInfo next;
            next = current.reverseUpHero();
            if (next != null && !distance.containsKey(next.hashCode())) {
                distance.put(next.hashCode(), currentDistance + 1);
                queue.add(next);
            }
            next = current.reverseDownHero();
            if (next != null && !distance.containsKey(next.hashCode())) {
                distance.put(next.hashCode(), currentDistance + 1);
                queue.add(next);
            }
            next = current.reverseLeftHero();
            if (next != null && !distance.containsKey(next.hashCode())) {
                distance.put(next.hashCode(), currentDistance + 1);
                queue.add(next);
            }
            next = current.reverseRightHero();
            if (next != null && !distance.containsKey(next.hashCode())) {
                distance.put(next.hashCode(), currentDistance + 1);
                queue.add(next);
            }
            next = current.reverseUpBox();
            if (next != null && !distance.containsKey(next.hashCode())) {
                distance.put(next.hashCode(), currentDistance + 1);
                queue.add(next);
            }
            next = current.reverseDownBox();
            if (next != null && !distance.containsKey(next.hashCode())) {
                distance.put(next.hashCode(), currentDistance + 1);
                queue.add(next);
            }
            next = current.reverseLeftBox();
            if (next != null && !distance.containsKey(next.hashCode())) {
                distance.put(next.hashCode(), currentDistance + 1);
                queue.add(next);
            }
            next = current.reverseRightBox();
            if (next != null && !distance.containsKey(next.hashCode())) {
                distance.put(next.hashCode(), currentDistance + 1);
                queue.add(next);
            }
        }
    }

    public MapMatrix generateStart() {
        return new MapMatrix(matrix, start);
    }
}
