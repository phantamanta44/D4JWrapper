package io.github.phantamanta44.discord4j.data.wrapper;

import com.github.fge.lambdas.Throwing;
import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.data.Permission;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

import java.awt.*;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Role extends Wrapper<IRole> {

    private final Map<String, ChannelRole> channels;

    Role(IRole backing) {
        super(backing);
        this.channels = new ConcurrentHashMap<>();
    }

    public Color color() {
        return getBacking().getColor();
    }

    public Guild guild() {
        return Wrapper.wrap(getBacking().getGuild());
    }

    public boolean hoisted() {
        return getBacking().isHoisted();
    }

    public boolean managed() {
        return getBacking().isManaged();
    }

    public String name() {
        return getBacking().getName();
    }

    public Stream<Permission> perms() {
        return getBacking().getPermissions().stream().map(Permission::wrap);
    }

    public boolean taggable() {
        return getBacking().isMentionable();
    }

    public int weight() {
        return getBacking().getPosition();
    }

    public ChannelRole of(Channel channel) {
        ChannelRole cu = channels.get(channel.id());
        if (cu == null) {
            cu = new ChannelRole(this, channel);
            channels.put(channel.id(), cu);
        }
        return cu;
    }

    public String tag() {
        return getBacking().mention();
    }

    public INullaryPromise destroy() {
        return RequestQueue.request(Throwing.runnable(getBacking()::delete));
    }

    public INullaryPromise setColor(Color color) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().changeColor(color)));
    }

    public INullaryPromise setHoisted(boolean hoisted) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().changeHoist(hoisted)));
    }

    public INullaryPromise setName(String name) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().changeName(name)));
    }

    public INullaryPromise setPerms(Permission... perms) {
        EnumSet<Permissions> unwrapped = EnumSet.copyOf(Arrays.stream(perms).map(Permission::getBacking).collect(Collectors.toSet()));
        return RequestQueue.request(Throwing.runnable(() -> getBacking().changePermissions(unwrapped)));
    }

    public INullaryPromise setTaggable(boolean taggable) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().changeMentionable(taggable)));
    }

}
