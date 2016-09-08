package io.github.phantamanta44.discord4j.data.wrapper;

import com.github.fge.lambdas.Throwing;
import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.data.Permission;
import io.github.phantamanta44.discord4j.util.CollUtils;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import sx.blah.discord.handle.obj.IRole;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuildUser extends User {

    private final Map<String, ChannelUser> channels;

    final User parent;
    final Guild guild;

    GuildUser(User parent, Guild guild) {
        super(parent.getBacking());
        this.parent = parent;
        this.guild = guild;
        this.channels = new ConcurrentHashMap<>();
    }

    public String displayName() {
        return getBacking().getDisplayName(guild.getBacking());
    }

    public boolean hasNickname() {
        return getBacking().getNicknameForGuild(guild.getBacking()).isPresent();
    }

    public Stream<Role> roles() {
        return getBacking().getRolesForGuild(guild.getBacking()).stream().map(Wrapper::wrap);
    }

    public boolean has(Permission... perms) {
        return CollUtils.containsAll(roles().flatMap(Role::perms).collect(Collectors.toSet()), perms);
    }

    public ChannelUser of(Channel channel) {
        ChannelUser cu = channels.get(channel.id());
        if (cu == null) {
            cu = new ChannelUser(this, channel);
            channels.put(channel.id(), cu);
        }
        return cu;
    }

    public INullaryPromise kick() {
        return RequestQueue.request(Throwing.runnable(() -> guild.getBacking().kickUser(getBacking())));
    }

    public INullaryPromise setNickname(String nick) {
        return RequestQueue.request(Throwing.runnable(() -> guild.getBacking().setUserNickname(getBacking(), nick)));
    }

    public INullaryPromise setRoles(Role... roles) {
        IRole[] unwrapped = Arrays.stream(roles).map(Wrapper::getBacking).toArray(IRole[]::new);
        return RequestQueue.request(Throwing.runnable(() -> guild.getBacking().editUserRoles(getBacking(), unwrapped)));
    }

}
