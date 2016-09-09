package io.github.phantamanta44.discord4j.core;

import com.github.fge.lambdas.Throwing;
import io.github.phantamanta44.discord4j.data.wrapper.Bot;
import io.github.phantamanta44.discord4j.util.concurrent.ThreadPoolFactory;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.Deferreds;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import io.github.phantamanta44.discord4j.util.reflection.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class Discord {

    private static final ScheduledExecutorService globalExecutorPool = new ThreadPoolFactory()
            .withPool(ThreadPoolFactory.PoolType.SCHEDULED)
            .withQueue(ThreadPoolFactory.QueueType.CACHED)
            .construct();
    private static final Logger logger = LoggerFactory.getLogger("D4JWrapper");

    public static ScheduledExecutorService getExecutorPool() {
        return globalExecutorPool;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static IUnaryPromise<Bot> authenticate(String token) {
        return Deferreds.call(Throwing.supplier(() -> new Bot(token))).promise();
    }

    public static void runStaticInitializers(Bot bot) {
        Reflect.methods().mask(Modifier.STATIC | Modifier.PUBLIC).params(Bot.class).tagged(StaticInit.class).find().forEach(m -> {
            try {
                m.invoke(null, bot);
            } catch (Exception e) {
                logger.warn("Static initializer failed!");
                e.printStackTrace();
            }
        });
    }

}
