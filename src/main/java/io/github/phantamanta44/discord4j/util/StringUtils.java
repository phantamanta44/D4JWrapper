package io.github.phantamanta44.discord4j.util;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class StringUtils {

    public static String concat(String[] parts) {
        return concat(parts, " ");
    }

    public static String concat(String[] parts, String joiner) {
        return Arrays.stream(parts).reduce((a, b) -> a.concat(joiner).concat(b)).orElse("");
    }

    public static String formatTimeElapsed(long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        StringBuilder b = new StringBuilder();
        if (days > 0)
            b.append(days).append(" Days, ");
        if (hours > 0)
            b.append(hours).append(" Hours, ");
        if (minutes > 0)
            b.append(minutes).append(" Minutes, ");
        return b.append(seconds).append(" Seconds").toString();
    }

}
