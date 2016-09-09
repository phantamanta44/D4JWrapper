package io.github.phantamanta44.discord4j.data.wrapper;

import sx.blah.discord.handle.obj.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public abstract class Wrapper<T extends IDiscordObject<T>> {

    private static final Map<Class, Map<IDiscordObject, Wrapper>> cachedWrappers = new HashMap<>();

    static {
        Stream.of(IGuild.class, IPrivateChannel.class, IChannel.class, IUser.class, IMessage.class, IRole.class).forEach(c -> cachedWrappers.put(c, new ConcurrentHashMap<>()));
    }

    @SuppressWarnings("unchecked")
    public static <T extends IDiscordObject<T>, W extends Wrapper<T>> W wrap(T raw) {
        Wrapper wrapped = cachedWrappers.get(raw.getClass()).get(raw);
        if (wrapped != null)
            return (W)wrapped;
        if (raw instanceof IGuild)
            return (W)cache(new Guild((IGuild)raw));
        else if (raw instanceof IPrivateChannel)
            return (W)cache(new PrivateChannel((IPrivateChannel)raw));
        else if (raw instanceof IChannel)
            return (W)cache(new Channel((IChannel)raw));
        else if (raw instanceof IUser)
            return (W)cache(new User((IUser)raw));
        else if (raw instanceof IMessage)
            return (W)cache(new Message((IMessage)raw));
        else if (raw instanceof IRole)
            return (W)cache(new Role((IRole)raw));
        throw new IllegalStateException();
    }

    private static Wrapper cache(Wrapper wrapper) {
        cachedWrappers.get(wrapper.getBacking().getClass()).put(wrapper.getBacking(), wrapper);
        return wrapper;
    }

    private final T backing;

    Wrapper(T backing) {
        this.backing = backing;
    }

    T getBacking() {
        return backing;
    }

    public String id() {
        return getBacking().getID();
    }

    public long timestamp() {
        return getBacking().getCreationDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
