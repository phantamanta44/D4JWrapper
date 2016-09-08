package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.data.Permission;
import io.github.phantamanta44.discord4j.data.wrapper.user.OverrideSet;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;

import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChannelRole extends Role {

    final Role parent;
    final Channel channel;

    ChannelRole(Role parent, Channel channel) {
        super(parent.getBacking());
        this.parent = parent;
        this.channel = channel;
    }

    public Stream<Permission> augmentedPerms() {
        return channel.getBacking().getModifiedPermissions(getBacking()).stream().map(Permission::wrap);
    }

    public OverrideSet overrides() {
        IChannel.PermissionOverride backing = channel.getBacking().getRoleOverrides().get(id());
        return new OverrideSet(
                backing.allow().stream().map(Permission::wrap).collect(Collectors.toSet()),
                backing.deny().stream().map(Permission::wrap).collect(Collectors.toSet())
        );
    }

    public INullaryPromise setOverridePerms(Collection<Permission> allow, Collection<Permission> deny) {
        EnumSet<Permissions> allowUnwrap = EnumSet.copyOf(allow.stream().map(Permission::getBacking).collect(Collectors.toSet()));
        EnumSet<Permissions> denyUnwrap = EnumSet.copyOf(deny.stream().map(Permission::getBacking).collect(Collectors.toSet()));
        return RequestQueue.request(() -> channel.getBacking().overrideRolePermissions(getBacking(), allowUnwrap, denyUnwrap));
    }

    public INullaryPromise unsetOverridePerms() {
        return RequestQueue.request(() -> channel.getBacking().removePermissionsOverride(getBacking()));
    }

}
