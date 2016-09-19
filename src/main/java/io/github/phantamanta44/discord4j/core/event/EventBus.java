package io.github.phantamanta44.discord4j.core.event;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.github.fge.lambdas.Throwing;

import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.core.event.context.GenericEventContext;
import io.github.phantamanta44.discord4j.core.event.context.IEventContext;
import io.github.phantamanta44.discord4j.data.wrapper.Bot;
import io.github.phantamanta44.discord4j.util.reflection.Reflect;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;

public class EventBus implements IListener<Event> {

    private final List<HandlerMeta> handlers;
    private Bot bot;

    public EventBus(IDiscordClient dcCli) {
        dcCli.getDispatcher().registerListener(this);
        handlers = new CopyOnWriteArrayList<>();
    }

    public void initHandlers(Bot bot) {
        this.bot = bot;
        Reflect.types().tagged(Handler.class).find().forEach(h -> {
            System.out.println(h.getClass().getName());
            for (Method m : h.getDeclaredMethods()) {
                Handler.On annot = m.getAnnotation(Handler.On.class);
                if (annot != null)
                    handlers.add(new HandlerMeta(annot.value(), Throwing.consumer(ctx -> m.invoke(null, ctx)), m.getAnnotation(Handler.class).value(), annot.scope()));
            }
        });
    }

    public void on(Events event, Consumer<IEventContext> handler) {
        handlers.add(new HandlerMeta(event, handler, null, HandlerScope.GLOBAL));
    }

    @Override
    public void handle(Event event) {
        for (HandlerMeta handler : handlers) {
            if (handler.event.eventType.isAssignableFrom(event.getClass())) {
                if (handler.scope == HandlerScope.GLOBAL)
                    post(handler, event);
                else if (handler.scope == HandlerScope.MODULE)
                    tryPostModule(handler, event);
            }
        }
    }

    private void post(HandlerMeta handler, Event event) {
        Future<?> handlerTask = Discord.executorPool().submit(() -> {
            try {
                handler.handler.accept(generateContext(handler.event, event));
            } catch (Exception e) {
                Discord.logger().warn("Failed to pass {} to {}!", handler.event.toString(), handler.handler.getClass().getName());
                e.printStackTrace();
            }
        });
        Discord.executorPool().schedule(() -> handlerTask.cancel(true), 15000L, TimeUnit.MILLISECONDS);
    }

    private void tryPostModule(HandlerMeta handler, Event event) {
        IEventContext ctx = generateContext(handler.event, event);
        if (ctx.guild() != null && bot.moduleMan().configFor(handler.modId).enabled(ctx.guild())) {
            Future<?> handlerTask = Discord.executorPool().submit(() -> {
                try {
                    handler.handler.accept(ctx);
                } catch (Exception e) {
                    Discord.logger().warn("Failed to pass {} to {}!", handler.event.toString(), handler.handler.getClass().getName());
                    e.printStackTrace();
                }
            });
            Discord.executorPool().schedule(() -> handlerTask.cancel(true), 15000L, TimeUnit.MILLISECONDS);
        }
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

    public enum HandlerScope {

        GLOBAL, MODULE

    }


}
