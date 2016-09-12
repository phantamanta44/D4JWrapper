package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.util.MathUtils;
import sx.blah.discord.handle.obj.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public abstract class Wrapper<T extends IDiscordObject<T>> implements Comparable<Wrapper> {

    private static final Map<Class, Map<IDiscordObject, Wrapper>> cachedWrappers = new HashMap<>();

    static {
        Stream.of(IGuild.class, IPrivateChannel.class, IChannel.class, IUser.class, IMessage.class, IRole.class).forEach(c -> cachedWrappers.put(c, new ConcurrentHashMap<>()));
    }


    @SuppressWarnings("unchecked")
    public static <T extends IDiscordObject<T>, W extends Wrapper<T>> W wrap(T raw) {
        if (raw == null)
            return null;
        Class<?>[] ints = raw.getClass().getInterfaces();
        Class<?> iface = ints[ints.length - 1];
        Wrapper wrapped = cachedWrappers.get(iface).get(raw);
        if (wrapped != null)
            return (W)wrapped;
        if (raw instanceof IGuild)
            return (W)cache(new Guild((IGuild)raw), iface);
        else if (raw instanceof IPrivateChannel)
            return (W)cache(new PrivateChannel((IPrivateChannel)raw), iface);
        else if (raw instanceof IChannel)
            return (W)cache(new Channel((IChannel)raw), iface);
        else if (raw instanceof IUser)
            return (W)cache(new User((IUser)raw), iface);
        else if (raw instanceof IMessage)
            return (W)cache(new Message((IMessage)raw), iface);
        else if (raw instanceof IRole)
            return (W)cache(new Role((IRole)raw), iface);
        throw new IllegalStateException();
    }

    private static Wrapper cache(Wrapper wrapper, Class<?> iface) {
        cachedWrappers.get(iface).put(wrapper.getBacking(), wrapper);
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

    @Override
    public boolean equals(Object o) {
        return o instanceof Wrapper && ((Wrapper) o).id().equalsIgnoreCase(id());
    }

    @Override
    public int compareTo(Wrapper o) {
        return (int)MathUtils.clamp(timestamp() - o.timestamp(), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

}
