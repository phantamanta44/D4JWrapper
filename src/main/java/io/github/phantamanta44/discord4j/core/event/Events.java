package io.github.phantamanta44.discord4j.core.event;

import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.*;

public class Events { // TODO Voice channel stuff

    public static final Events ALL = new Events(Event.class);

    public static final Events DC_DISCON = new Events(DiscordDisconnectedEvent.class);
    public static final Events DC_RECON = new Events(DiscordReconnectedEvent.class);

    public static final Events BOT_JOIN = new Events(GuildCreateEvent.class);
    public static final Events BOT_LEAVE = new Events(GuildLeaveEvent.class);

    public static final Events GUILD_OWNER_CHANGE = new Events(GuildTransferOwnershipEvent.class);
    public static final Events GUILD_MODIFY = new Events(GuildUpdateEvent.class);

    public static final Events CHAN_CREATE = new Events(ChannelCreateEvent.class);
    public static final Events CHAN_DEL = new Events(ChannelDeleteEvent.class);
    public static final Events CHAN_MODIFY = new Events(ChannelUpdateEvent.class);

    public static final Events USER_BAN = new Events(UserBanEvent.class);
    public static final Events USER_JOIN = new Events(UserJoinEvent.class);
    public static final Events USER_LEAVE = new Events(UserLeaveEvent.class);
    public static final Events USER_ROLE = new Events(UserRoleUpdateEvent.class);
    public static final Events USER_STATUS = new Events(PresenceUpdateEvent.class);
    public static final Events USER_SUBTITLE = new Events(StatusChangeEvent.class);
    public static final Events USER_TYPING = new Events(TypingEvent.class);
    public static final Events USER_UNBAN = new Events(UserPardonEvent.class);
    public static final Events USER_MODIFY = new Events(UserUpdateEvent.class);

    public static final Events MSG_DEL = new Events(MessageDeleteEvent.class);
    public static final Events MSG_EDIT = new Events(MessageUpdateEvent.class);
    public static final Events MSG_GET = new Events(MessageReceivedEvent.class);
    public static final Events MSG_PIN = new Events(MessagePinEvent.class);
    public static final Events MSG_SEND = new Events(MessageSendEvent.class);
    public static final Events MSG_UNPIN = new Events(MessageUnpinEvent.class);

    public static final Events ROLE_CREATE = new Events(RoleCreateEvent.class);
    public static final Events ROLE_DEL = new Events(RoleDeleteEvent.class);
    public static final Events ROLE_MODIFY = new Events(RoleUpdateEvent.class);


    final Class<? extends Event> eventType;

    private Events(Class<? extends Event> eventType) {
        this.eventType = eventType;
    }

}
