package io.github.phantamanta44.discord4j.data.wrapper.user;

import sx.blah.discord.handle.obj.Presences;

public enum UserStatus {

    ONLINE, AFK, OFFLINE, STREAMING;

    public static UserStatus wrap(Presences status) {
        switch (status) {
            case ONLINE:
                return ONLINE;
            case IDLE:
                return AFK;
            case OFFLINE:
                return OFFLINE;
            case STREAMING:
                return STREAMING;
        }
        throw new IllegalStateException();
    }

}
