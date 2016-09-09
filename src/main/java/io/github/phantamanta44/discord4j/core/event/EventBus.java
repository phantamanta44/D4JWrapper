package io.github.phantamanta44.discord4j.core.event;

import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.core.event.context.GenericEventContext;
import io.github.phantamanta44.discord4j.core.event.context.IEventContext;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class EventBus implements IListener<Event> {

    private final List<HandlerMeta> handlers;

    public EventBus(IDiscordClient dcCli) {
        dcCli.getDispatcher().registerListener(this);
        handlers = new CopyOnWriteArrayList<>();
    }

    public void onGlobal(Events event, Consumer<IEventContext> handler) {
        handlers.add(new HandlerMeta(event, handler));
    }

    @Override
    public void handle(Event event) {
        for (HandlerMeta handler : handlers) {
            if (handler.event.eventType.isAssignableFrom(event.getClass())) {
                if (handler.scope == HandlerScope.GLOBAL)
                    post(handler, event);
                else if (handler.scope == HandlerScope.MODULE) {
                    // TODO Module checking stuff
                }
            }
        }
    }

    private void post(HandlerMeta handler, Event event) {
        Future<?> handlerTask = Discord.getExecutorPool().submit(() -> {
            try {
                handler.handler.accept(generateContext(handler.event, event));
            } catch (Exception e) {
                Discord.getLogger().warn("Failed to pass {} to {}!", handler.event.toString(), handler.handler.getClass().getName());
                e.printStackTrace();
            }
        });
        Discord.getExecutorPool().schedule(() -> handlerTask.cancel(true), 15000L, TimeUnit.MILLISECONDS);
    }

    private IEventContext generateContext(Events type, Event event) { // TODO More specific event contexts that don't require reflection
        return new GenericEventContext(type, event, System.currentTimeMillis());
    }

    private static class HandlerMeta {

        final Events event;
        final HandlerScope scope;
        final Consumer<IEventContext> handler;
        final String modId;

        HandlerMeta(Events event, Consumer<IEventContext> handler) {
            this(event, handler, null, HandlerScope.GLOBAL);
        }

        HandlerMeta(Events event, Consumer<IEventContext> handler, String modId) {
            this(event, handler, modId, HandlerScope.MODULE);
        }

        HandlerMeta(Events event, Consumer<IEventContext> handler, String modId, HandlerScope scope) {
            this.event = event;
            this.scope = scope;
            this.handler = handler;
            this.modId = modId;
        }

    }

    private enum HandlerScope {

        GLOBAL, MODULE

    }


}
