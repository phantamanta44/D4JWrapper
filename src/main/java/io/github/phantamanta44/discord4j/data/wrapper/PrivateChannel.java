package io.github.phantamanta44.discord4j.data.wrapper;

import sx.blah.discord.handle.obj.IPrivateChannel;

public class PrivateChannel extends Channel {

    PrivateChannel(IPrivateChannel backing) {
        super(backing);
    }

    public User user() {
        return Wrapper.wrap(((IPrivateChannel)getBacking()).getRecipient());
    }

}
