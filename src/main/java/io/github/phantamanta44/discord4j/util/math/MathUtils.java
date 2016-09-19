package io.github.phantamanta44.discord4j.util.math;

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

	public static boolean bounds(int n, int lower, int upper) {
		return lower <= n && n < upper;
	}
	
	public static boolean bounds(float n, float lower, float upper) {
		return lower <= n && n < upper;
	}
	
	public static boolean bounds(double n, double lower, double upper) {
		return lower <= n && n < upper;
	}

}
