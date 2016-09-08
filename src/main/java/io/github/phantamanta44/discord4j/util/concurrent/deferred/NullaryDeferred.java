package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.function.Consumer;

public class NullaryDeferred extends AbstractDeferred<INullaryPromise> {

    private Runnable onResolve;

    public NullaryDeferred() {
        onResolve = Lambdas.noopNullary();
    }

    public void resolve() {
        state = PromiseState.RESOLVED;
        onResolve.run();
    }

    @Override
    public INullaryPromise promise() {
        return new INullaryPromise() {
            @Override
            public INullaryPromise done(Runnable callback) {
                if (state() == PromiseState.PENDING)
                    onResolve = Lambdas.compose(onResolve, callback);
                else
                    callback.run();
                return this;
            }

            @Override
            public INullaryPromise fail(Consumer<Throwable> callback) {
                if (state() == PromiseState.PENDING)
                    onReject = onReject.andThen(callback);
                else
                    callback.accept(exception);
                return this;
            }

            @Override
            public INullaryPromise always(Runnable callback) {
                if (state() == PromiseState.PENDING) {
                    onResolve = Lambdas.compose(onResolve, callback);
                    onReject = onReject.andThen(e -> callback.run());
                }
                else
                    callback.run();
                return this;
            }

            @Override
            public INullaryPromise progress(Runnable callback) {
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
