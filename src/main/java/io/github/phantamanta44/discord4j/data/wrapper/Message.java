package io.github.phantamanta44.discord4j.data.wrapper;

import com.github.fge.lambdas.Throwing;
import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.data.Attachment;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import sx.blah.discord.handle.obj.IMessage;

import java.time.LocalDateTime;
import java.util.stream.Stream;

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

    public LocalDateTime lastEdit() {
        return getBacking().getTimestamp();
    }

    public boolean pinned() {
        return getBacking().isPinned();
    }

    public INullaryPromise destroy() {
        return RequestQueue.request(Throwing.runnable(getBacking()::delete));
    }

    public INullaryPromise edit(String content) {
        return RequestQueue.request(Throwing.runnable(() -> getBacking().edit(content)));
    }

}
