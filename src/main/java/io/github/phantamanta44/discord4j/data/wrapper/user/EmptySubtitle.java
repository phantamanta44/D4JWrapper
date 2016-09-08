package io.github.phantamanta44.discord4j.data.wrapper.user;

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

}
