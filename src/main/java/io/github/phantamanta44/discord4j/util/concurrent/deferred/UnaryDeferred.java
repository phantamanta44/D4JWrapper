package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.function.Consumer;

public class UnaryDeferred<A> extends AbstractDeferred<IUnaryPromise<A>> {

    private Consumer<A> onResolve;
    private A result;

    public UnaryDeferred() {
        onResolve = Lambdas.noopUnary();
    }

    public void resolve(A result) {
        this.result = result;
        state = PromiseState.RESOLVED;
        onResolve.accept(result);
    }

    @Override
    public IUnaryPromise<A> promise() {
        return new IUnaryPromise<A>() {
            @Override
            public IUnaryPromise<A> done(Consumer<A> callback) {
                if (state() == PromiseState.PENDING)
                    onResolve = onResolve.andThen(callback);
                else
                    callback.accept(result);
                return this;
            }

            @Override
            public IUnaryPromise<A> fail(Consumer<Throwable> callback) {
                if (state() == PromiseState.PENDING)
                    onReject = onReject.andThen(callback);
                else
                    callback.accept(exception);
                return this;
            }

            @Override
            public IUnaryPromise<A> always(Consumer<A> callback) {
                if (state() == PromiseState.PENDING) {
                    onResolve = onResolve.andThen(callback);
                    onReject = onReject.andThen(e -> callback.accept(null));
                }
                else
                    callback.accept(result);
                return this;
            }

            @Override
            public IUnaryPromise<A> progress(Runnable callback) {
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
