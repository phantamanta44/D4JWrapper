package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.data.icon.UrlIcon;
import io.github.phantamanta44.discord4j.data.wrapper.user.*;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Status;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User extends Wrapper<IUser> {

    private final Map<String, GuildUser> guilds;

    User(IUser backing) {
        super(backing);
        this.guilds = new ConcurrentHashMap<>();
    }

    public UrlIcon avatar() {
        return new UrlIcon(getBacking().getAvatarURL());
    }

    public boolean isBot() {
        return getBacking().isBot();
    }

    public String discrim() {
        return getBacking().getDiscriminator();
    }

    public String name() {
        return getBacking().getName();
    }

    public IUnaryPromise<PrivateChannel> privateChannel() {
        return RequestQueue.request(() -> Wrapper.wrap(Bot.instance.dcCli.getOrCreatePMChannel(getBacking())));
    }

    public UserStatus status() {
        return UserStatus.wrap(getBacking().getPresence());
    }

    public ISubtitle subtitle() {
        Status sub = getBacking().getStatus();
        switch (sub.getType()) {
            case GAME:
                return new GameSubtitle(sub.getStatusMessage());
            case STREAM:
                return new StreamSubtitle(sub.getStatusMessage(), sub.getUrl().orElse(null));
            case NONE:
                return EmptySubtitle.INSTANCE;
        }
        return null;
    }

    public boolean in(Guild guild) {
        return guild.has(this);
    }

    public String tag() {
        return getBacking().mention();
    }

    public GuildUser of(Guild guild) {
        GuildUser gu = guilds.get(guild.id());
        if (gu == null) {
            gu = new GuildUser(this, guild);
            guilds.put(guild.id(), gu);
        }
        return gu;
    }

}
