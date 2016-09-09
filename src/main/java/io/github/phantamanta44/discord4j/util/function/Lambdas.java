package io.github.phantamanta44.discord4j.util.function;

import io.github.phantamanta44.discord4j.data.wrapper.GuildUser;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;

public class Lambdas {

    public static Runnable noopNullary() {
        return () -> {};
    }

    public static <A> Consumer<A> noopUnary() {
        return a -> {};
    }

    public static <A, B> BiConsumer<A, B> noopBinary() {
        return (a, b) -> {};
    }

    public static Runnable compose(Runnable a, Runnable b) {
        return () -> {
            a.run();
            b.run();
        };
    }

    public static IntBinaryOperator bitOr() {
        return (a, b) -> a | b;
    }

    public static IntBinaryOperator bitXor() {
        return (a, b) -> a ^ b;
    }

    public static IntBinaryOperator bitAnd() {
        return (a, b) -> a & b;
    }

    public static <T> Predicate<T> acceptAll() {
        return x -> true;
    }

    public static <T> Predicate<T> acceptNone() {
        return x -> false;
    }

    public static <T> Predicate<T> nonNull() {
        return x -> x != null;
    }
}
