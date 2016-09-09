package io.github.phantamanta44.discord4j.core.event.context;

import io.github.phantamanta44.discord4j.core.event.Events;
import io.github.phantamanta44.discord4j.data.wrapper.*;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.lang.reflect.Method;

public class GenericEventContext implements IEventContext {

    private final Events type;
    private final long timestamp;
    private Guild guild;
    private Channel channel;
    private User user;
    private Message msg;

    public GenericEventContext(Events type, Event event, long timestamp) {
        this.type = type;
        this.timestamp = timestamp;
        Class<? extends Event> clazz = event.getClass();
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            try {
                if (m.getName().equalsIgnoreCase("getUser"))
                    user = Wrapper.wrap((IUser)m.invoke(event));
                else if (m.getName().equalsIgnoreCase("getChannel")) {
                    channel = Wrapper.wrap((IChannel)m.invoke(event));
                    guild = channel.guild();
                } else if (m.getName().equalsIgnoreCase("getMessage")) {
                    msg = Wrapper.wrap((IMessage)m.invoke(event));
                    user = msg.author();
                    channel = msg.channel();
                    guild = msg.guild();
                } else if (m.getName().equalsIgnoreCase("getGuild")) {
                    guild = Wrapper.wrap((IGuild)m.invoke(event));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public long timestamp() {
        return timestamp;
    }

    @Override
    public Events eventType() {
        return type;
    }

    @Override
    public Guild guild() {
        return guild;
    }

    @Override
    public Channel channel() {
        return channel;
    }

    @Override
    public User user() {
        return user;
    }

    @Override
    public Message message() {
        return msg;
    }

    @Override
    public IUnaryPromise<Message> send(String msg) {
        return channel.send(msg);
    }

}
