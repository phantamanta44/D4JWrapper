package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import java.util.function.Consumer;

public interface INullaryPromise extends IPromise {

    INullaryPromise done(Runnable callback);

    INullaryPromise fail(Consumer<Exception> callback);

    INullaryPromise always(Runnable callback);

    INullaryPromise progress(Runnable callback);

}
