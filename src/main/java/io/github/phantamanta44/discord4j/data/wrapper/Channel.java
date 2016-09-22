package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.handle.obj.IChannel;

import java.io.File;
import java.io.InputStream;
import java.util.stream.Stream;

public class Channel extends Wrapper<IChannel> {

    Channel(IChannel backing) {
        super(backing);
    }

    public Guild guild() {
        return Wrapper.wrap(getBacking().getGuild());
    }

    public MessageStream messages() {
        return new MessageStream(getBacking().getMessages());
    }

    public String name() {
        return getBacking().getName();
    }

    public IUnaryPromise<Stream<Message>> pinned() {
        return RequestQueue.request(() -> getBacking().getPinnedMessages().stream().map(Wrapper::wrap));
    }

    public int position() {
        return getBacking().getPosition();
    }

    public ChannelUserStream users() {
        return new UserStream<>(getBacking().getUsersHere()).of(guild()).of(this);
    }

    public String tag() {
        return getBacking().mention();
    }

    public String topic() {
        return getBacking().getTopic();
    }

    public ChannelUser user(String id) {
        return users().filter(u -> u.id().equalsIgnoreCase(id)).findAny().orElse(null);
    }

    public ChannelUserStream usersOfName(String name) {
        return users().filter(u -> u.name().equalsIgnoreCase(name));
    }

    public ChannelUserStream usersOfNick(String name) {
        return users().filter(GuildUser::hasNickname).filter(u -> u.displayName().equalsIgnoreCase(name));
    }

    public INullaryPromise destroy() {
        return RequestQueue.request(() -> getBacking().delete());
    }

    public IUnaryPromise<String> generateInvite(int age, int uses, boolean tempUsers) {
        return RequestQueue.request(() -> getBacking().createInvite(age, uses, tempUsers).getInviteCode());
    }

    public INullaryPromise pin(Message msg) {
        return RequestQueue.request(() -> getBacking().pin(msg.getBacking()));
    }

    public IUnaryPromise<Message> send(File file) {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().sendFile(file)));
    }

    public IUnaryPromise<Message> send(File file, String caption) {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().sendFile(file, caption)));
    }

    public IUnaryPromise<Message> send(String fileName, InputStream dataSrc) {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().sendFile(dataSrc, fileName)));
    }

    public IUnaryPromise<Message> send(String fileName, InputStream dataSrc, String caption) {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().sendFile(dataSrc, fileName, caption)));
    }

    public IUnaryPromise<Message> send(String msg) {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().sendMessage(msg)));
    }

    public IUnaryPromise<Message> send(String msg, Object... args) {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().sendMessage(String.format(msg, args))));
    }

    public INullaryPromise setName(String name) {
        return RequestQueue.request(() -> getBacking().changeName(name));
    }

    public INullaryPromise setPosition(int position) {
        return RequestQueue.request(() -> getBacking().changePosition(position));
    }

    public INullaryPromise setTopic(String topic) {
        return RequestQueue.request(() -> getBacking().changeTopic(topic));
    }

    public INullaryPromise unpin(Message msg) {
        return RequestQueue.request(() -> getBacking().unpin(msg.getBacking()));
    }

}
