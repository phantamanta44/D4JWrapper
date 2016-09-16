package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.data.Permission;
import io.github.phantamanta44.discord4j.data.icon.IIcon;
import io.github.phantamanta44.discord4j.data.icon.UrlIcon;
import io.github.phantamanta44.discord4j.util.ImageUtils;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.Image;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Stream;

public class Guild extends Wrapper<IGuild> { // TODO Voice channel stuff

    Guild(IGuild backing) {
        super(backing);
    }

    public Stream<Channel> channels() {
        return getBacking().getChannels().stream().map(Wrapper::wrap);
    }

    public boolean has(User user) {
        return users().anyMatch(u -> u.id().equalsIgnoreCase(user.id()));
    }

    public UrlIcon icon() {
        return new UrlIcon(getBacking().getIcon());
    }

    public String name() {
        return getBacking().getName();
    }

    public User owner() {
        return Wrapper.wrap(getBacking().getOwner());
    }

    public Stream<Role> roles() {
        return getBacking().getRoles().stream().map(Wrapper::wrap);
    }

    public GuildUserStream users() {
        return new UserStream<>(getBacking().getUsers()).of(this);
    }

    public Channel channel(String id) {
        return Wrapper.wrap(getBacking().getChannelByID(id));
    }
    
    public Role role(String id) {
		return Wrapper.wrap(getBacking().getRoleByID(id));
	}

    public GuildUser user(String id) {
        return ((User)Wrapper.wrap(getBacking().getUserByID(id))).of(this);
    }

    public GuildUserStream usersOfName(String name) {
        return users().filter(u -> u.name().equalsIgnoreCase(name));
    }

    public GuildUserStream usersOfNick(String nick) {
        return users().filter(GuildUser::hasNickname).filter(u -> u.displayName().equalsIgnoreCase(nick));
    }

    public INullaryPromise addBot(String clientId, Permission... perms) {
        return RequestQueue.request(() -> {
            Permissions[] unwrapped = Arrays.stream(perms).map(Permission::getBacking).toArray(Permissions[]::new);
            getBacking().addBot(clientId, EnumSet.of(unwrapped[0], Arrays.copyOfRange(unwrapped, 1, unwrapped.length)));
        });
    }

    public INullaryPromise ban(User user) {
        return RequestQueue.request(() -> getBacking().banUser(user.getBacking()));
    }

    public INullaryPromise destroy() {
        return RequestQueue.request(() -> getBacking().deleteGuild());
    }

    public IUnaryPromise<Channel> makeChannel(String name) {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().createChannel(name)));
    }

    public IUnaryPromise<Role> makeRole() {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().createRole()));
    }

    public IUnaryPromise<Integer> pruneUsers(int inactiveDays) {
        return RequestQueue.request(() -> getBacking().pruneUsers(inactiveDays));
    }

    public INullaryPromise setIcon(IIcon icon) {
        return RequestQueue.request(() -> getBacking().changeIcon(Image.forStream("png", ImageUtils.inputStream(icon.getImage()))));
    }

    public INullaryPromise setName(String name) {
        return RequestQueue.request(() -> getBacking().changeName(name));
    }

    public INullaryPromise unban(User user) {
        return RequestQueue.request(() -> getBacking().pardonUser(user.id()));
    }

}
