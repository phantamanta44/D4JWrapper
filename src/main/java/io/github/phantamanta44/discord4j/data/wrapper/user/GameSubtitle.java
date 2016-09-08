package io.github.phantamanta44.discord4j.data.wrapper.user;

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

}
