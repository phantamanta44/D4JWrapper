package io.github.phantamanta44.discord4j.core.event.context;

import io.github.phantamanta44.discord4j.core.event.Events;
import io.github.phantamanta44.discord4j.data.wrapper.Channel;
import io.github.phantamanta44.discord4j.data.wrapper.Guild;
import io.github.phantamanta44.discord4j.data.wrapper.Message;
import io.github.phantamanta44.discord4j.data.wrapper.User;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;

public interface IEventContext {

    long timestamp();

    Events eventType();

    Guild guild();

    Channel channel();

    User user();

    Message message();

    IUnaryPromise<Message> send(String msg);

    default IUnaryPromise<Message> send(String msg, Object... args) {
        return send(String.format(msg, args));
    }

}
