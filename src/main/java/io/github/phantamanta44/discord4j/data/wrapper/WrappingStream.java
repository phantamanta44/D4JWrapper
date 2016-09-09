package io.github.phantamanta44.discord4j.data.wrapper;

import sx.blah.discord.handle.obj.IDiscordObject;

import java.util.Collection;
import java.util.stream.Stream;

public abstract class WrappingStream<T extends Wrapper<W>, W extends IDiscordObject<W>, C extends Collection<W>> implements Stream<T> {

    final C source;
    Stream<T> backing;

    WrappingStream(Stream<T> backing, C source) {
        this.source = source;
    }

    public WrappingStream<T, W, C> withId(String id) {
        filter(w -> w.id().equalsIgnoreCase(id));
        return this;
    }

    public WrappingStream<T, W, C> after(long time) {
        filter(w -> w.timestamp() > time);
        return this;
    }

    public T any() {
        return backing.findAny().orElse(null);
    }

    public T first() {
        return backing.findFirst().orElse(null);
    }

}
