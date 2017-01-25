package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.data.icon.IIcon;
import io.github.phantamanta44.discord4j.data.icon.UrlIcon;
import sx.blah.discord.handle.obj.IEmoji;

import java.util.stream.Stream;

public class Emoji extends Wrapper<IEmoji> {

    Emoji(IEmoji backing) {
        super(backing);
    }

    public Guild guild() {
        return Wrapper.wrap(getBacking().getGuild());
    }

    public IIcon icon() {
        return new UrlIcon(getBacking().getImageUrl());
    }

    public boolean managed() {
        return getBacking().isManaged();
    }

    public String name() {
        return getBacking().getName();
    }

    public boolean needsColons() {
        return getBacking().requiresColons();
    }

    public Stream<Role> roles() {
        return getBacking().getRoles().stream().map(Wrapper::wrap);
    }

    @Override
    public String toString() {
        return getBacking().toString();
    }

}
