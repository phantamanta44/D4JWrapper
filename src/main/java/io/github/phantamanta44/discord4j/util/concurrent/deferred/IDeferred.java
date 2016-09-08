package io.github.phantamanta44.discord4j.util.concurrent.deferred;

public interface IDeferred<P extends IPromise> {

    void reject(Throwable e);

    void notifyProgress();

    P promise();

}
