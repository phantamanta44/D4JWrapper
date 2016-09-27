package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.core.event.EventBus;
import io.github.phantamanta44.discord4j.core.module.ModuleManager;
import io.github.phantamanta44.discord4j.data.icon.IIcon;
import io.github.phantamanta44.discord4j.data.wrapper.user.Application;
import io.github.phantamanta44.discord4j.data.wrapper.user.ISubtitle;
import io.github.phantamanta44.discord4j.util.ImageUtils;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.UnaryDeferred;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;

import java.util.stream.Stream;

public class Bot { // TODO Events

    static Bot instance;

    IDiscordClient dcCli;
    EventBus eventBus;
    ModuleManager modManager;

    public Bot(String token, UnaryDeferred<Bot> def) {
        Discord.logger().info("Attempting to authenticate with Discord...");
        try {
            Discord4J.disableChannelWarnings();
            dcCli = new ClientBuilder().withToken(token).build();
            eventBus = new EventBus(dcCli);
            modManager = new ModuleManager();
            dcCli.getDispatcher().registerTemporaryListener((ReadyEvent e) -> {
                if (!user().isBot())
                    def.reject(new IllegalArgumentException("User is not a bot account!"));
                Discord.logger().info("Authentication successful! {}#{}", user().name(), user().discrim());
                eventBus.initHandlers(this);
                Discord.runStaticInitializers(instance = this);
                Runtime.getRuntime().addShutdownHook(new Thread(moduleMan()::saveAll));
                def.resolve(this);
            });
            dcCli.login();
        } catch (DiscordException e) {
            def.reject(e);
        }
    }

    public User user() {
        return Wrapper.wrap(dcCli.getOurUser());
    }

    public Application application() {
        try {
            return new Application(dcCli.getApplicationName(), dcCli.getApplicationClientID(), dcCli.getApplicationIconURL());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Stream<Channel> channels() {
        return dcCli.getChannels(false).stream().map(Wrapper::wrap);
    }

    public EventBus eventBus() {
        return eventBus;
    }

    public Stream<Guild> guilds() {
        return dcCli.getGuilds().stream().map(Wrapper::wrap);
    }

    public ModuleManager moduleMan() {
        return modManager;
    }

    public String token() {
        return dcCli.getToken();
    }

    public INullaryPromise logout() {
        return RequestQueue.request(dcCli::logout);
    }

    public Channel channel(String id) {
        return Wrapper.wrap(dcCli.getChannelByID(id));
    }

    public Guild guild(String id) {
        return Wrapper.wrap(dcCli.getGuildByID(id));
    }
    
    public User user(String id) {
    	return Wrapper.wrap(dcCli.getUserByID(id));
    }

    public INullaryPromise leave(Guild guild) {
        return RequestQueue.request(() -> guild.getBacking().leaveGuild());
    }

    public IUnaryPromise<Long> ping() {
        return RequestQueue.request(() -> dcCli.getResponseTime());
    }

    public INullaryPromise setAvatar(IIcon icon) {
        return RequestQueue.request(() -> dcCli.changeAvatar(Image.forStream("png", ImageUtils.inputStream(icon.getImage()))));
    }

    public INullaryPromise setIdle(boolean idle) {
        return RequestQueue.request(() -> dcCli.changePresence(idle));
    }

    public INullaryPromise setSubtitle(ISubtitle subtitle) {
        return RequestQueue.request(() -> dcCli.changeStatus(subtitle.unwrap()));
    }

}
