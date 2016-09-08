package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import io.github.phantamanta44.discord4j.core.Discord;

import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public class Deferreds {

    public static NullaryDeferred call(Runnable func) {
        return call(func, Discord.getExecutorPool());
    }

    public static <A> UnaryDeferred<A> call(Supplier<A> func) {
        return call(func, Discord.getExecutorPool());
    }

    public static NullaryDeferred call(Runnable func, ExecutorService execServ) {
        NullaryDeferred def = new NullaryDeferred();
        execServ.submit(() -> {
            try {
                func.run();
                def.resolve();
            } catch (Exception e) {
                def.reject(e);
            }
        });
        return def;
    }

    public static <A> UnaryDeferred<A> call(Supplier<A> func, ExecutorService execServ) {
        UnaryDeferred<A> def = new UnaryDeferred<>();
        execServ.submit(() -> {
            try {
                def.resolve(func.get());
            } catch (Exception e) {
                def.reject(e);
            }
        });
        return def;
    }

}
