package io.github.phantamanta44.discord4j.data.wrapper.user;

import sx.blah.discord.handle.obj.Status;

public class EmptySubtitle implements ISubtitle {

    public static final EmptySubtitle INSTANCE = new EmptySubtitle();

    private EmptySubtitle() {
        // NO-OP
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Status unwrap() {
        return Status.empty();
    }

}
