package io.github.phantamanta44.discord4j.data.wrapper;

import com.github.fge.lambdas.Throwing;
import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.handle.obj.IChannel;

import java.util.stream.Stream;

public class Channel extends Wrapper<IChannel> { // TODO Message related stuff
    // TODO File sending
    Channel(IChannel backing) {
        super(backing);
    }

    public Guild guild() {
        return Wrapper.wrap(getBacking().getGuild());
    }

    public String name() {
        return getBacking().getName();
    }

    public String topic() {
        return getBacking().getTopic();
    }

    public IUnaryPromise<Stream<Message>> pinned() {
        return RequestQueue.request(Throwing.supplier(() -> getBacking().getPinnedMessages().stream().map(Wrapper::wrap)));
    }

    public int position() {
        return getBacking().getPosition();
    }

    public boolean isPrivate() {
        return getBacking().isPrivate();
    }

    public Stream<ChannelUser> users() {
        return getBacking().getUsersHere().stream().map(Wrapper::wrap).map(u -> ((User)u).of(guild()).of(this));
    }

    public String tag() {
        return getBacking().mention();
    }

    public ChannelUser user(String id) {
        return users().filter(u -> u.id().equalsIgnoreCase(id)).findAny().orElse(null);
    }

    public Stream<ChannelUser> usersOfName(String name) {
        return users().filter(u -> u.name().equalsIgnoreCase(name));
    }

    public Stream<ChannelUser> usersOfNick(String name) {
        return users().filter(GuildUser::hasNickname).filter(u -> u.displayName().equalsIgnoreCase(name));
    }

    public INullaryPromise destroy() {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().delete()));
    }

    public IUnaryPromise<String> generateInvite(int age, int uses, boolean tempUsers) {
        return RequestQueue.request(Throwing.supplier(() -> getBacking().createInvite(age, uses, tempUsers).getInviteCode()));
    }

    public INullaryPromise pin(Message msg) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().pin(msg.getBacking())));
    }

    public IUnaryPromise<Message> send(String msg) {
        return RequestQueue.request(Throwing.supplier(() -> Wrapper.wrap(getBacking().sendMessage(msg))));
    }

    public INullaryPromise setName(String name) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().changeName(name)));
    }

    public INullaryPromise setPosition(int position) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().changePosition(position)));
    }

    public INullaryPromise setTopic(String topic) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().changeTopic(topic)));
    }

    public INullaryPromise unpin(Message msg) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().unpin(msg.getBacking())));
    }

}
