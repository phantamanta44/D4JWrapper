package io.github.phantamanta44.discord4j.data.wrapper.user;

import sx.blah.discord.handle.obj.Status;

public class GameSubtitle implements ISubtitle {

    private final String message;

    public GameSubtitle(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Status unwrap() {
        return Status.game(message);
    }

}
