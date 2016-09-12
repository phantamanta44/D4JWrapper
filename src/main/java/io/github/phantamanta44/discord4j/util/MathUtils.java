package io.github.phantamanta44.discord4j.util;

public class MathUtils {

    public static int clamp(int n, int lower, int upper) {
        return Math.min(Math.max(n, lower), upper);
    }

    public static float clamp(float n, float lower, float upper) {
        return Math.min(Math.max(n, lower), upper);
    }

    public static double clamp(double n, double lower, double upper) {
        return Math.min(Math.max(n, lower), upper);
    }

}
