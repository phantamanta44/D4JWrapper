package io.github.phantamanta44.discord4j.data.wrapper;

import java.io.File;
import java.io.InputStream;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import sx.blah.discord.handle.obj.IPrivateChannel;

public class PrivateChannel extends Channel {

    PrivateChannel(IPrivateChannel backing) {
        super(backing);
    }

    public User user() {
        return Wrapper.wrap(((IPrivateChannel)getBacking()).getRecipient());
    }

}
