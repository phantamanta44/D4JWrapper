package io.github.phantamanta44.discord4j.core;

import com.github.fge.lambdas.Throwing;
import io.github.phantamanta44.discord4j.data.wrapper.User;
import io.github.phantamanta44.discord4j.data.wrapper.Wrapper;
import io.github.phantamanta44.discord4j.util.concurrent.ThreadPoolFactory;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.Deferreds;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import io.github.phantamanta44.discord4j.util.reflection.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.lang.reflect.Modifier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class Discord { // TODO Way to leave a guild without kicking self

    private static final ScheduledExecutorService globalExecutorPool = new ThreadPoolFactory()
            .withPool(ThreadPoolFactory.PoolType.SCHEDULED)
            .withQueue(ThreadPoolFactory.QueueType.CACHED)
            .construct();
    private static final Logger logger = LoggerFactory.getLogger("D4JWrapper");

    public static ExecutorService getExecutorPool() {
        return globalExecutorPool;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static IUnaryPromise<Discord> authenticate(String token) {
        return Deferreds.call(Throwing.supplier(() -> new Discord(token))).promise();
    }

    private final IDiscordClient dcCli;

    private Discord(String token) throws DiscordException {
        logger.info("Attempting to authenticate with Discord...");
        dcCli = new ClientBuilder().withToken(token).login();
        logger.info("Authentication successful! {}#{}", bot().name(), bot().discrim());
        runStaticInitializers();
    }

    private void runStaticInitializers() {
        Reflect.methods().mask(Modifier.STATIC | Modifier.PUBLIC).params(Discord.class).tagged(StaticInit.class).find().forEach(m -> {
            try {
                m.invoke(null, this);
            } catch (Exception e) {
                logger.warn("Static initializer failed!");
                e.printStackTrace();
            }
        });
    }

    public User bot() {
        return Wrapper.wrap(dcCli.getOurUser());
    }

}
