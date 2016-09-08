package io.github.phantamanta44.discord4j.util.concurrent.deferred;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IBinaryPromise<A, B> extends IPromise {

    IBinaryPromise<A, B> done(BiConsumer<A, B> callback);

    IBinaryPromise<A, B> fail(Consumer<Exception> callback);

    IBinaryPromise<A, B> always(BiConsumer<A, B> callback);

    IBinaryPromise<A, B> progress(Runnable callback);

}
