package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import java.util.function.Consumer;

public interface IUnaryPromise<A> extends IPromise {

    IUnaryPromise<A> done(Consumer<A> callback);

    IUnaryPromise<A> fail(Consumer<Exception> callback);

    IUnaryPromise<A> always(Consumer<A> callback);

    IUnaryPromise<A> progress(Runnable callback);

}
