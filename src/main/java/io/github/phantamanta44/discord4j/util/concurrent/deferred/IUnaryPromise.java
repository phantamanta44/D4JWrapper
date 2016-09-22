package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import java.util.function.Consumer;
import java.util.function.Function;

public interface IUnaryPromise<A> extends IPromise {

    IUnaryPromise<A> done(Consumer<A> callback);

    IUnaryPromise<A> fail(Consumer<Throwable> callback);

    IUnaryPromise<A> always(Consumer<A> callback);

    IUnaryPromise<A> progress(Runnable callback);

    <B> IUnaryPromise<B> map(Function<A, B> mapper);

}
