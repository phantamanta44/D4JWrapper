package io.github.phantamanta44.discord4j.data;

import sx.blah.discord.handle.obj.Permissions;

import java.util.Arrays;

public enum Permission {

    INVITE_CREATE(Permissions.CREATE_INVITE),
    USER_KICK(Permissions.KICK),
    USER_BAN(Permissions.BAN),
    MANAGE_ROLES_ALL(Permissions.ADMINISTRATOR),
    MANAGE_ROLES(Permissions.MANAGE_ROLES),
    MANAGE_PERMS(Permissions.MANAGE_PERMISSIONS),
    MANAGE_CHANS(Permissions.MANAGE_CHANNELS),
    MANAGE_CHAN(Permissions.MANAGE_CHANNEL),
    MANAGE_SERV(Permissions.MANAGE_SERVER),
    MANAGE_MSG(Permissions.MANAGE_MESSAGES),
    MANAGE_NICK(Permissions.MANAGE_NICKNAMES),
    MSG_READ(Permissions.READ_MESSAGES),
    MSG_SEND(Permissions.SEND_MESSAGES),
    MSG_SEND_TTS(Permissions.SEND_TTS_MESSAGES),
    MSG_LINKS(Permissions.EMBED_LINKS),
    MSG_ATTACH(Permissions.ATTACH_FILES),
    MSG_HISTORY(Permissions.READ_MESSAGE_HISTORY),
    MSG_TAG_EVERY(Permissions.MENTION_EVERYONE),
    VOICE_CXN(Permissions.VOICE_CONNECT),
    VOICE_SPEAK(Permissions.VOICE_SPEAK),
    VOICE_MUTE(Permissions.VOICE_MUTE_MEMBERS),
    VOICE_DEAFEN(Permissions.VOICE_DEAFEN_MEMBERS),
    VOICE_MOVE(Permissions.VOICE_MOVE_MEMBERS),
    VOICE_VAD(Permissions.VOICE_USE_VAD),
    NICK_CHANGE(Permissions.CHANGE_NICKNAME);

    public static Permission wrap(Permissions perm) {
        return Arrays.stream(values()).filter(p -> p.backing == perm).findAny().orElseThrow(UnsupportedOperationException::new);
    }

    private final Permissions backing;

    Permission(Permissions backing) {
        this.backing = backing;
    }

    public Permissions getBacking() {
        return backing;
    }

}
