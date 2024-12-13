package model;

import java.util.Random;

public class RandGen {
    private static Random random = new Random();

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
