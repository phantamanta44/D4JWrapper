package io.github.phantamanta44.discord4j.core;

import io.github.phantamanta44.discord4j.util.concurrent.deferred.Deferreds;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.util.RequestBuffer;

import java.util.function.Supplier;

public class RequestQueue {

    public static INullaryPromise request(Runnable req) {
        return Deferreds.call(() -> {
            RequestBuffer.RequestFuture future = RequestBuffer.request(() -> {
                req.run();
                return null;
            });
            while (!(future.isDone() || future.isCancelled())) {
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException ignored) { }
            }
        }).promise();
    }

    public static <T> IUnaryPromise<T> request(Supplier<T> req) {
        return Deferreds.call(() -> {
            RequestBuffer.RequestFuture<T> future = RequestBuffer.request((RequestBuffer.IRequest<T>)req::get);
            while (!(future.isDone() || future.isCancelled())) {
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException ignored) { }
            }
            return future.get();
        }).promise();
    }

}
