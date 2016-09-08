package io.github.phantamanta44.discord4j.util.concurrent.deferred;

public interface IPromise {

    IPromise progress(Runnable callback);

    PromiseState state();

}
