package io.github.phantamanta44.discord4j.util;

import java.util.Arrays;
import java.util.Collection;

public class CollUtils {

    public static boolean containsAll(Object[] container, Object... objects) {
        return Arrays.asList(container).containsAll(Arrays.asList(objects));
    }

    public static boolean containsAll(Collection<?> container, Object[] objects) {
        return container.containsAll(Arrays.asList(objects));
    }

    @SuppressWarnings("unchecked")
    public static <T> T random(Collection<T> container) {
        Object[] asArray = container.toArray();
        return (T)asArray[(int)Math.floor(Math.random() * asArray.length)];
    }

}
