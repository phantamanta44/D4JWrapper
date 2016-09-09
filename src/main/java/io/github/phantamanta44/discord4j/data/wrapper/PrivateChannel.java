package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IPrivateChannel;

import java.io.File;
import java.io.InputStream;

public class PrivateChannel extends Wrapper<IChannel> {

    PrivateChannel(IPrivateChannel backing) {
        super(backing);
    }

    public MessageStream messages() {
        return new MessageStream(getBacking().getMessages());
    }

    public String name() {
        return getBacking().getName();
    }

    public User user() {
        return Wrapper.wrap(((IPrivateChannel)getBacking()).getRecipient());
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

}
