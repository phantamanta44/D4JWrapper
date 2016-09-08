package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.core.StaticInit;
import io.github.phantamanta44.discord4j.data.Permission;
import io.github.phantamanta44.discord4j.data.wrapper.user.OverrideSet;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class ChannelUser extends GuildUser {

    @StaticInit
    private static void init(Bot bot) {
        // TODO Typing listener
    }

    final GuildUser parent;
    final Channel channel;

    private boolean typingStatus = false;

    ChannelUser(GuildUser parent, Channel channel) {
        super(parent.parent, parent.guild);
        this.parent = parent;
        this.channel = channel;
    }

    public OverrideSet channelOverrides() {
        IChannel.PermissionOverride backing = channel.getBacking().getUserOverrides().get(id());
        return new OverrideSet(
                backing.allow().stream().map(Permission::wrap).collect(Collectors.toSet()),
                backing.deny().stream().map(Permission::wrap).collect(Collectors.toSet())
        );
    }

    public boolean typing() {
        return typingStatus;
    }

    @Override
    public boolean has(Permission... perms) {
        return channel.getBacking().getModifiedPermissions(getBacking()).containsAll(Arrays.stream(perms).map(Permission::getBacking).collect(Collectors.toSet()));
    }

    public INullaryPromise setOverridePerms(Collection<Permission> allow, Collection<Permission> deny) {
        EnumSet<Permissions> allowUnwrap = EnumSet.copyOf(allow.stream().map(Permission::getBacking).collect(Collectors.toSet()));
        EnumSet<Permissions> denyUnwrap = EnumSet.copyOf(deny.stream().map(Permission::getBacking).collect(Collectors.toSet()));
        return RequestQueue.request(() -> channel.getBacking().overrideUserPermissions(getBacking(), allowUnwrap, denyUnwrap));
    }

    public INullaryPromise unsetOverridePerms() {
        return RequestQueue.request(() -> channel.getBacking().removePermissionsOverride(getBacking()));
    }

}
