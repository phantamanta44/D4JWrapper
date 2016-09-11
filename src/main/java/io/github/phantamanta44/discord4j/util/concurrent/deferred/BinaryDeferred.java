package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BinaryDeferred<A, B> extends AbstractDeferred<IBinaryPromise<A, B>> {

    private BiConsumer<A, B> onResolve;
    private A a;
    private B b;

    public BinaryDeferred() {
        onResolve = Lambdas.noopBinary();
    }

    public void resolve(A a, B b) {
        this.a = a;
        this.b = b;
        state = PromiseState.RESOLVED;
        onResolve.accept(a, b);
    }

    @Override
    public IBinaryPromise<A, B> promise() {
        return new IBinaryPromise<A, B>() {
            @Override
            public IBinaryPromise<A, B> done(BiConsumer<A, B> callback) {
                if (state() == PromiseState.PENDING)
                    onResolve = onResolve.andThen(callback);
                else if (state() == PromiseState.RESOLVED)
                    callback.accept(a, b);
                return this;
            }

            @Override
            public IBinaryPromise<A, B> fail(Consumer<Throwable> callback) {
                if (state() == PromiseState.PENDING)
                    onReject = onReject.andThen(callback);
                else if (state() == PromiseState.REJECTED)
                    callback.accept(exception);
                return this;
            }

            @Override
            public IBinaryPromise<A, B> always(BiConsumer<A, B> callback) {
                if (state() == PromiseState.PENDING) {
                    onResolve = onResolve.andThen(callback);
                    onReject = onReject.andThen(e -> callback.accept(null, null));
                }
                else
                    callback.accept(a, b);
                return this;
            }

            @Override
            public IBinaryPromise<A, B> progress(Runnable callback) {
                onProgress = Lambdas.compose(onProgress, callback);
                return this;
            }

            @Override
            public PromiseState state() {
                return state;
            }
        };
    }

}
