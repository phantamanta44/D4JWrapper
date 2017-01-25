package io.github.phantamanta44.discord4j.core.event;

import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.*;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelUpdateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.TypingEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.*;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionRemoveEvent;
import sx.blah.discord.handle.impl.events.guild.channel.webhook.WebhookCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.webhook.WebhookDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.member.*;
import sx.blah.discord.handle.impl.events.guild.role.RoleCreateEvent;
import sx.blah.discord.handle.impl.events.guild.role.RoleDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.role.RoleUpdateEvent;
import sx.blah.discord.handle.impl.events.guild.voice.VoiceChannelCreateEvent;
import sx.blah.discord.handle.impl.events.guild.voice.VoiceChannelDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.voice.VoiceChannelUpdateEvent;
import sx.blah.discord.handle.impl.events.guild.voice.VoicePingEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserSpeakingEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelMoveEvent;
import sx.blah.discord.handle.impl.events.shard.DisconnectedEvent;
import sx.blah.discord.handle.impl.events.shard.ReconnectFailureEvent;
import sx.blah.discord.handle.impl.events.shard.ReconnectSuccessEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;
import sx.blah.discord.handle.impl.events.user.StatusChangeEvent;
import sx.blah.discord.handle.impl.events.user.UserUpdateEvent;

public enum Events { // TODO Voice channel stuff

    ALL(Event.class),

    DC_DISCON(DisconnectedEvent.class),
    DC_RECON(ReconnectSuccessEvent.class),
    DC_RECON_FAIL(ReconnectFailureEvent.class),

    BOT_JOIN(GuildCreateEvent.class),
    BOT_LEAVE(GuildLeaveEvent.class),

    GUILD_EMOJI_UPDATE(GuildEmojisUpdateEvent.class),
    GUILD_MODIFY(GuildUpdateEvent.class),
    GUILD_OWNER_CHANGE(GuildTransferOwnershipEvent.class),

    CHAN_CREATE(ChannelCreateEvent.class),
    CHAN_DEL(ChannelDeleteEvent.class),
    CHAN_MODIFY(ChannelUpdateEvent.class),

    VCHAN_CREATE(VoiceChannelCreateEvent.class),
    VCHAN_DEL(VoiceChannelDeleteEvent.class),
    VCHAN_MODIFY(VoiceChannelUpdateEvent.class),
    VCHAN_PING(VoicePingEvent.class),

    USER_BAN(UserBanEvent.class),
    USER_JOIN(UserJoinEvent.class),
    USER_LEAVE(UserLeaveEvent.class),
    USER_ROLE(UserRoleUpdateEvent.class),
    USER_STATUS(PresenceUpdateEvent.class),
    USER_SUBTITLE(StatusChangeEvent.class),
    USER_TYPING(TypingEvent.class),
    USER_UNBAN(UserPardonEvent.class),
    USER_MODIFY(UserUpdateEvent.class),

    GUSER_NICK(NickNameChangeEvent.class),

    VUSER_JOIN(UserVoiceChannelJoinEvent.class),
    VUSER_LEAVE(UserVoiceChannelLeaveEvent.class),
    VUSER_MOVE(UserVoiceChannelMoveEvent.class),
    VUSER_SPEAKS(UserSpeakingEvent.class),

    MSG_DEL(MessageDeleteEvent.class),
    MSG_EDIT(MessageUpdateEvent.class),
    MSG_EMBED(MessageEmbedEvent.class),
    MSG_GET(MessageReceivedEvent.class),
    MSG_PIN(MessagePinEvent.class),
    MSG_SEND(MessageSendEvent.class),
    MSG_UNPIN(MessageUnpinEvent.class),

    REACT_ADD(ReactionAddEvent.class),
    REACT_REM(ReactionRemoveEvent.class),

    ROLE_CREATE(RoleCreateEvent.class),
    ROLE_DEL(RoleDeleteEvent.class),
    ROLE_MODIFY(RoleUpdateEvent.class),

    WH_CREATE(WebhookCreateEvent.class),
    WH_DEL(WebhookDeleteEvent.class);


    final Class<? extends Event> eventType;

    Events(Class<? extends Event> eventType) {
        this.eventType = eventType;
    }

}
