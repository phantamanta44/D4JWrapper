package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.function.Consumer;

public abstract class AbstractDeferred<P extends IPromise> implements IDeferred<P> {

    protected Consumer<Throwable> onReject;
    protected Runnable onProgress;

    protected Throwable exception;
    protected PromiseState state;

    public AbstractDeferred() {
        onProgress = Lambdas.noopNullary();
        onReject = Lambdas.noopUnary();
        state = PromiseState.PENDING;
    }

    @Override
    public void reject(Throwable e) {
        this.exception = e;
        state = PromiseState.REJECTED;
        onReject.accept(e);
    }

    @Override
    public void notifyProgress() {
        onProgress.run();
    }

}
