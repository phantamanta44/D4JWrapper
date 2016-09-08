package io.github.phantamanta44.discord4j.data.wrapper;

import com.github.fge.lambdas.Throwing;
import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.data.wrapper.Guild;
import io.github.phantamanta44.discord4j.data.wrapper.User;
import io.github.phantamanta44.discord4j.data.wrapper.Wrapper;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.Deferreds;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Bot {

    private final IDiscordClient dcCli;

    public Bot(String token) throws DiscordException {
        Discord.getLogger().info("Attempting to authenticate with Discord...");
        dcCli = new ClientBuilder().withToken(token).login();
        Discord.getLogger().info("Authentication successful! {}#{}", user().name(), user().discrim());
        Discord.runStaticInitializers(this);
    }

    public User user() {
        return Wrapper.wrap(dcCli.getOurUser());
    }

    public INullaryPromise leave(Guild guild) {
        return RequestQueue.request(() -> guild.getBacking().leaveGuild());
    }

}
