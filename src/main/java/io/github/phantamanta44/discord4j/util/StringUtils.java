package io.github.phantamanta44.discord4j.util;

import java.util.Arrays;

public class StringUtils {

    public static String concat(String[] parts) {
        return concat(parts, " ");
    }

    public static String concat(String[] parts, String joiner) {
        return Arrays.stream(parts).reduce((a, b) -> a.concat(joiner).concat(b)).orElse("");
    }

}
