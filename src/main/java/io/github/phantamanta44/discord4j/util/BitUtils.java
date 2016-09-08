package io.github.phantamanta44.discord4j.util;

import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.Arrays;

public class BitUtils {

    public static boolean hasFlags(int mask, int flags) {
        return (mask & flags) == flags;
    }

    public static int foldFlags(int[] flags) {
        return Arrays.stream(flags).reduce(0, Lambdas.bitOr());
    }

}
