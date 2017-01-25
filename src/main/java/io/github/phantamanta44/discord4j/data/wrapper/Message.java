package io.github.phantamanta44.discord4j.data.wrapper;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.data.Attachment;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;

public class Message extends Wrapper<IMessage> { // TODO Mention getter

    Message(IMessage backing) {
        super(backing);
    }

    public User author() {
        return Wrapper.wrap(getBacking().getAuthor());
    }

    public Stream<Attachment> attachments() {
        return getBacking().getAttachments().stream().map(Attachment::wrap);
    }

    public String body() {
        return getBacking().getContent();
    }

    public Channel channel() {
        return Wrapper.wrap(getBacking().getChannel());
    }

    public boolean edited() {
        return getBacking().getEditedTimestamp().isPresent();
    }

    public Guild guild() {
        return Wrapper.wrap(getBacking().getGuild());
    }

    public long lastEdit() {
        return getBacking().getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public boolean pinned() {
        return getBacking().isPinned();
    }
    
    public List<IReaction> reacts() {
        return getBacking().getReactions();
    }
    
    public IReaction reacts(String reaction) {
        return getBacking().getReactionByName(reaction);
    }
    
    public IReaction reacts(Emoji reaction) {
        return getBacking().getReactionByIEmoji(reaction.getBacking());
    }

    public INullaryPromise destroy() {
        return RequestQueue.request(getBacking()::delete);
    }

    public IUnaryPromise<Message> edit(String content) {
        return RequestQueue.request(() -> Wrapper.wrap(getBacking().edit(content)));
    }

    public IUnaryPromise<Message> edit(String format, Object... args) {
        return edit(String.format(format, args));
    }
    
    public INullaryPromise react(String reaction) {
        return RequestQueue.request(() -> getBacking().addReaction(reaction));
    }

    public INullaryPromise react(Emoji reaction) {
        return RequestQueue.request(() -> getBacking().addReaction(reaction.getBacking()));
    }

    public INullaryPromise react(IReaction reaction) {
        return RequestQueue.request(() -> getBacking().addReaction(reaction));
    }
    
    public INullaryPromise unreact(IReaction reaction) {
        return RequestQueue.request(() -> getBacking().removeReaction(reaction));
    }

    public INullaryPromise unreact(User user, IReaction reaction) {
        return RequestQueue.request(() -> getBacking().removeReaction(user.getBacking(), reaction));
    }
    
    public INullaryPromise unreactAll() {
        return RequestQueue.request(getBacking()::removeAllReactions);
    }

}
