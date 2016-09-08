package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.function.Consumer;

public abstract class AbstractDeferred<P extends IPromise> implements IDeferred<P> {

    protected Consumer<Exception> onReject;
    protected Runnable onProgress;

    protected Exception exception;
    protected PromiseState state;

    public AbstractDeferred() {
        onProgress = Lambdas.noopNullary();
        onReject = Lambdas.noopUnary();
        state = PromiseState.PENDING;
    }

    @Override
    public void reject(Exception e) {
        this.exception = e;
        state = PromiseState.REJECTED;
        onReject.accept(e);
    }

    @Override
    public void notifyProgress() {
        onProgress.run();
    }

}
