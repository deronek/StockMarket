package stockmarket.util;

import java.util.concurrent.ThreadLocalRandom;

public final class MyRandom {

    private MyRandom() {
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double getRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

    public static int[] getUniqueRandomInts(int size, int min, int max) {
        return ThreadLocalRandom.current()
                .ints(min, max + 1)
                .distinct()
                .limit(size)
                .toArray();
    }

    public static double getGaussianRandomDouble(double max) {
        return ThreadLocalRandom.current().nextGaussian() * max;
    }
}