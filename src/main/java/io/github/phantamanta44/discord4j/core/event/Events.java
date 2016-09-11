package io.github.phantamanta44.discord4j.core.event;

import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.*;

public enum Events { // TODO Voice channel stuff

    ALL(Event.class),

    DC_DISCON(DiscordDisconnectedEvent.class),
    DC_RECON(DiscordReconnectedEvent.class),

    BOT_JOIN(GuildCreateEvent.class),
    BOT_LEAVE(GuildLeaveEvent.class),

    GUILD_OWNER_CHANGE(GuildTransferOwnershipEvent.class),
    GUILD_MODIFY(GuildUpdateEvent.class),

    CHAN_CREATE(ChannelCreateEvent.class),
    CHAN_DEL(ChannelDeleteEvent.class),
    CHAN_MODIFY(ChannelUpdateEvent.class),

    USER_BAN(UserBanEvent.class),
    USER_JOIN(UserJoinEvent.class),
    USER_LEAVE(UserLeaveEvent.class),
    USER_ROLE(UserRoleUpdateEvent.class),
    USER_STATUS(PresenceUpdateEvent.class),
    USER_SUBTITLE(StatusChangeEvent.class),
    USER_TYPING(TypingEvent.class),
    USER_UNBAN(UserPardonEvent.class),
    USER_MODIFY(UserUpdateEvent.class),

    MSG_DEL(MessageDeleteEvent.class),
    MSG_EDIT(MessageUpdateEvent.class),
    MSG_GET(MessageReceivedEvent.class),
    MSG_PIN(MessagePinEvent.class),
    MSG_SEND(MessageSendEvent.class),
    MSG_UNPIN(MessageUnpinEvent.class),

    ROLE_CREATE(RoleCreateEvent.class),
    ROLE_DEL(RoleDeleteEvent.class),
    ROLE_MODIFY(RoleUpdateEvent.class);


    final Class<? extends Event> eventType;

    Events(Class<? extends Event> eventType) {
        this.eventType = eventType;
    }

}
