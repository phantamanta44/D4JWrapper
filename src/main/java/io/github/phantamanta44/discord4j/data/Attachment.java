package io.github.phantamanta44.discord4j.data;

import sx.blah.discord.handle.obj.IMessage;

public class Attachment {

    private final String name, id, url;
    private final int bytes;

    public Attachment(String name, String id, String url, int bytes) {
        this.name = name;
        this.id = id;
        this.url = url;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getSize() {
        return bytes;
    }

    public static Attachment wrap(IMessage.Attachment attachment) {
        return new Attachment(attachment.getFilename(), attachment.getId(), attachment.getUrl(), attachment.getFilesize());
    }

}
