package io.github.phantamanta44.discord4j.core;

import com.github.fge.lambdas.runnable.ThrowingRunnable;
import com.github.fge.lambdas.supplier.ThrowingSupplier;

import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.NullaryDeferred;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.UnaryDeferred;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

public class RequestQueue {

    public static INullaryPromise request(ThrowingRunnable req) {
        NullaryDeferred def = new NullaryDeferred();
        RequestBuffer.request(() -> {
            try {
                req.doRun();
                def.resolve();
            } catch (RateLimitException e) {
                throw e;
            } catch (Throwable e) {
                def.reject(e);
            }
        });
        return def.promise();
    }

    public static <T> IUnaryPromise<T> request(ThrowingSupplier<T> req) {
        UnaryDeferred<T> def = new UnaryDeferred<>();
        RequestBuffer.request(() -> {
            try {
                def.resolve(req.doGet());
            } catch (RateLimitException e) {
                throw e;
            } catch (Throwable e) {
                def.reject(e);
            }
        });
        return def.promise();
    }
    
}
