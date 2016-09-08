package io.github.phantamanta44.discord4j.core;

import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.runnable.ThrowingRunnable;
import com.github.fge.lambdas.supplier.ThrowingSupplier;
import io.github.phantamanta44.discord4j.data.wrapper.Bot;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.NullaryDeferred;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.UnaryDeferred;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

import java.util.LinkedList;
import java.util.List;
import java.util.OptionalLong;

public class RequestQueue {

    private static final List<AbstractRequest> requests = new LinkedList<>();
    private static final Object sync = new Object();

    @StaticInit
    public static void init(Bot bot) {
        new Thread(() -> {
            while (true) {
                if (!requests.isEmpty()) {
                    OptionalLong sleepTime = requests.stream().mapToLong(r -> r.time).min();
                    if (sleepTime.isPresent()) {
                        try {
                            Thread.sleep(sleepTime.getAsLong() - System.currentTimeMillis() + 1L);
                            requests.stream().filter(r -> r.time <= System.currentTimeMillis()).forEach(AbstractRequest::iteration);
                        } catch (InterruptedException e) {
                            Discord.getLogger().error("RequestQueue thread interrupted!");
                            break;
                        }
                    }
                } else {
                    synchronized (sync) {
                        try {
                            sync.wait();
                        } catch (InterruptedException e) {
                            Discord.getLogger().error("RequestQueue thread interrupted!");
                            break;
                        }
                    }
                }
            }
        }, "RequestQueue").run();
    }

    public static INullaryPromise request(ThrowingRunnable req) {
        NullaryDeferred def = new NullaryDeferred();
        request(new NullaryRequest(req, def));
        return def.promise();
    }

    public static <T> IUnaryPromise<T> request(ThrowingSupplier<T> req) {
        UnaryDeferred<T> def = new UnaryDeferred<>();
        request(new UnaryRequest<>(req, def));
        return def.promise();
    }

    private static void request(AbstractRequest req) {
        requests.add(req);
        synchronized (sync) {
            sync.notify();
        }
    }

    private static abstract class AbstractRequest {

        long time;

        abstract void iteration();

        void cancel() {
            requests.remove(this);
        }

    }

    private static class UnaryRequest<T> extends AbstractRequest {

        private final ThrowingSupplier<T> getter;
        private final UnaryDeferred<T> deferred;

        private UnaryRequest(ThrowingSupplier<T> getter, UnaryDeferred<T> deferred) {
            this.getter = getter;
            this.deferred = deferred;
        }

        @Override
        public void iteration() {
            try {
                deferred.resolve(getter.doGet());
                cancel();
            } catch (RateLimitException e) {
                time = System.currentTimeMillis() + e.getRetryDelay();
                deferred.notifyProgress();
            } catch (DiscordException e) {
                if (e.getErrorMessage().endsWith(". This is due to CloudFlare.")) {
                    time = System.currentTimeMillis() + 50L;
                    deferred.notifyProgress();
                } else {
                    deferred.reject(e);
                    cancel();
                }
            } catch (Throwable e) {
                deferred.reject(e);
                cancel();
            }
        }

    }

    private static class NullaryRequest extends AbstractRequest {

        private final ThrowingRunnable func;
        private final NullaryDeferred deferred;

        private NullaryRequest(ThrowingRunnable func, NullaryDeferred deferred) {
            this.func = func;
            this.deferred = deferred;
        }

        @Override
        public void iteration() {
            try {
                func.doRun();
                deferred.resolve();
                cancel();
            } catch (RateLimitException e) {
                time = System.currentTimeMillis() + e.getRetryDelay();
                deferred.notifyProgress();
            } catch (DiscordException e) {
                if (e.getErrorMessage().endsWith(". This is due to CloudFlare.")) {
                    time = System.currentTimeMillis() + 50L;
                    deferred.notifyProgress();
                } else {
                    deferred.reject(e);
                    cancel();
                }
            } catch (Throwable e) {
                deferred.reject(e);
                cancel();
            }
        }

    }

}
